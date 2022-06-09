package com.plugin.patch;

import com.android.build.api.transform.Transform;
import com.android.build.gradle.AppExtension;
import com.android.build.gradle.AppPlugin;
import com.android.build.gradle.api.ApplicationVariant;
import com.android.build.gradle.internal.pipeline.TransformTask;
import com.android.build.gradle.internal.transforms.ProGuardTransform;
import com.android.build.gradle.internal.transforms.R8Transform;
import com.android.utils.FileUtils;

import org.apache.commons.compress.utils.IOUtils;
import org.gradle.api.Action;
import org.gradle.api.GradleException;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.file.FileCollection;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.regex.Matcher;

public class Patch implements Plugin<Project> {

    private static final String TAG = "-----Patch-----";

    @Override
    public void apply(Project project) {
        System.out.println(TAG + " start patch");

        //AppPlugin指的是build.gradle中的android {},要拿到这个类，需要在buildSrc的build.gradle中引入
        //"com.android.tools.build:gradle:3.5.4"
        if (!project.getPlugins().hasPlugin(AppPlugin.class)) {
            throw new GradleException("只能在applicationId的模块使用");
        }

        //创建一个patch{}配置
        //就和引入了 apply plugin: 'com.android.application' 一样，可以配置android{}
        project.getExtensions().create("patch", PatchExtension.class);

        project.afterEvaluate(new Action<Project>() {
            @Override
            public void execute(final Project project) {
                //gradle执行会解析build.gradle文件，afterEvaluate表示在解析完成之后再执行我们的代码
                //只能在这个回调中才能获取到
                final PatchExtension patchExtension = project.getExtensions().findByType(PatchExtension.class);
                if (patchExtension == null) {
                    System.out.println(TAG + " patchExtension is null");
                    return;
                }
                final boolean debugOn = patchExtension.debugOn;
                final String applicationName = patchExtension.applicationName;
                final String output = patchExtension.output;

                System.out.println(TAG + " debugOn = " + debugOn + " applicationName = " + applicationName + " output = " + output);

                //得到android的配置
                AppExtension appExtension = project.getExtensions().getByType(AppExtension.class);
                // android项目默认会有 debug和release，
                // 那么getApplicationVariants就是包含了debug和release的集合，all表示对集合进行遍历
                appExtension.getApplicationVariants().all(new Action<ApplicationVariant>() {
                    @Override
                    public void execute(ApplicationVariant applicationVariant) {
                        //当前用户是debug模式，并且没有配置debug运行执行热修复
                        if (applicationVariant.getName().contains("debug") && !debugOn) {
                            System.out.println(TAG + "debug 模式下不进行差分包的生成");
                            return;
                        }
                        beginTasks(project, applicationVariant,
                                patchExtension);
                    }
                });
            }
        });
    }

    private void beginTasks(final Project project, final ApplicationVariant applicationVariant, final PatchExtension patchExtension) {
        //获得: debug/release
        String variantName = applicationVariant.getName();
        //首字母大写
        String capitalizeName = Utils.capitalize(variantName);
        System.out.println(TAG + "variantName = " + variantName + " capitalizeName = " + capitalizeName);

        File outPutFile = null;
        String output = patchExtension.output;
        if (Utils.isEmpty(output)) {
            //如果用户没有配置生成差分包的目录，则默认生成到build目录下
            outPutFile = new File(project.getBuildDir() + File.separator + "patch" + File.separator + variantName);
        } else {
            outPutFile = new File(output, variantName);
        }
        boolean mkdirs = outPutFile.mkdirs();

        final File finalOutputFile = outPutFile;
        System.out.println(TAG + "mkdirs = " + mkdirs + " out put dir = " + outPutFile.getAbsolutePath());

        //处理混淆问题，如果不做处理，同一个类每次混淆结果可能不同，导致差分包生成时无法匹配，所以要应用mapping文件，
        //让同一个类混淆结果和上次保持相同

        //获得android的混淆任务
        Task proguardTask = project.getTasks().findByName("transformClassesAndResourcesWithProguardFor" + capitalizeName);

        System.out.println(TAG + "transformClassesAndResourcesWithProguardFor" + capitalizeName + " is " + (proguardTask == null ? "null" : "exist"));

        if (proguardTask == null) {
            proguardTask = project.getTasks().findByName("transformClassesAndResourcesWithR8For" + capitalizeName);
            System.out.println(TAG + "transformClassesAndResourcesWithR8For" + capitalizeName + " is " + (proguardTask == null ? "null" : "exist"));
        }


        /**
         * 在混淆后 记录类的hash值，并生成补丁包
         */
        final File hexFile = new File(outPutFile, "hex.txt");
        // 需要打包补丁的类的jar包
        final File patchClassFile = new File(outPutFile, "patchClass.jar");
        // 用dx打包后的jar包:补丁包
        final File patchFile = new File(outPutFile, "patch.jar");

        //如果没开启混淆，则为null，不需要备份mapping
        if (proguardTask != null) {
            proguardTask.doLast(new Action<Task>() {
                @Override
                public void execute(Task task) {
                    processClassWithMapping(task, finalOutputFile);
                    //如果是R8-transformClassesAndResourcesWithR8For,那么会找不到transformClassesWithDexBuilderFor
                    //所以class文件可以从这里获取，我们在gradle.properties中禁用R8,这样不妨碍我们测试
                    processClassWithAsmAndDoPatch(project, applicationVariant, patchExtension, task, hexFile, patchClassFile, patchFile);
                }
            });
        } else {
            System.out.println(TAG + "proguardTask  is null");
        }
    }

    private void processClassWithMapping(Task proguardTask, File outPutFile) {
        if (proguardTask != null) {
            //备份本次的mapping文件
            final File mappingBak = new File(outPutFile, "mapping.txt");
            FileCollection files = proguardTask.getOutputs().getFiles();
            for (File file : files) {
                System.out.println(TAG + "proguard output file = " + file.getPath());
                if (file.getName().endsWith("mapping.txt")) {
                    try {
                        FileUtils.copyFile(file, mappingBak);
                        System.out.println(TAG + " mapping: " + mappingBak.getCanonicalPath());
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(TAG + " copy mapping: " + e);
                    }
                    break;
                }
            }
            //将上次混淆的mapping应用到本次,如果没有上次的混淆文件就没操作
            if (mappingBak.exists()) {
                TransformTask transformTask = (TransformTask) proguardTask;
                Transform transform = transformTask.getTransform();
                System.out.println(TAG + "applying mapping.txt, transformTask = " + transformTask + " transform = " + transform);
                if (transform instanceof ProGuardTransform) {
                    System.out.println(TAG + "applying mapping using ProGuardTransform");
                    ProGuardTransform proGuardTransform = (ProGuardTransform) transformTask.getTransform();
                    proGuardTransform.applyTestedMapping(mappingBak);
                } else if (transform instanceof R8Transform) {
                    System.out.println(TAG + "applying mapping using R8Transform");
                    R8Transform r8Transform = (R8Transform) transformTask.getTransform();
                    r8Transform.setMainDexListOutput(mappingBak);
                }
            }
        }
    }

    private void processClassWithAsmAndDoPatch(final Project project, final ApplicationVariant applicationVariant,
                                               final PatchExtension patchExtension, Task task, File hexFile, File patchClassFile, File patchFile) {
        //插桩 记录md5并对比
        PatchGenerator patchGenerator = new PatchGenerator(project, patchFile,
                patchClassFile, hexFile);
        //用户配置的application，实际上可以解析manifest自动获取，但是java实现太麻烦了，干脆让用户自己配置
        String applicationName = patchExtension.applicationName;
        //windows下 目录输出是  xx\xx\  ,linux下是  /xx/xx ,把 . 替换成平台相关的斜杠
        applicationName = applicationName.replaceAll("\\.",
                Matcher.quoteReplacement(File.separator));
        System.out.println(TAG + "applicationName = " + applicationName + " applicationName = " + applicationName);

        //记录类的md5
        Map<String, String> newHexs = new HashMap<>();

        FileCollection files = task.getInputs().getFiles();
        for (File file : files) {
            String filePath = file.getAbsolutePath();
            if (filePath.endsWith(".jar")) {
                //processJar(applicationName, file, newHexs, patchGenerator);
            }
            if (filePath.endsWith(".class")) {
                processClass(applicationName, applicationVariant.getDirName(), file, newHexs,
                        patchGenerator);
            }
        }

        Iterator<Map.Entry<String, String>> iterator = newHexs.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            if (next == null) {
                continue;
            }
            System.out.println(TAG + "md5 = " + next.getKey() + " : " + next.getValue());
        }

        //类的md5集合 写入到文件
        Utils.writeHex(newHexs, hexFile);
        try {
            //生成补丁
            patchGenerator.generate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processClass(String applicationName, String dirName, File file, Map<String, String> newHexs, PatchGenerator patchGenerator) {
        //注意这里的filePath包含了目录+包名+类名，所以去掉目录
        ///Users/renzhenming/AndroidStudioProjects/TestApplication/app/build/intermediates/javac/debug/classes/com/rzm/testapplication/okhttp/OkHttpActivity.class
        String filePath = file.getAbsolutePath();
        //className = classes/com/rzm/testapplication/retrofit/User.class
        dirName = dirName + "/classes";
        //注意这里的filePath包含了目录+包名+类名，所以去掉目录\
        String className = filePath.split(dirName)[1].substring(1);
        //注意这里的filePath包含了目录+包名+类名，所以去掉目录
        //application或者android support我们不管
        if (className.startsWith("com/rzm/testapplication/Application") || Utils.isAndroidClass(className)) {
            return;
        }
        System.out.println(TAG + "processClass className = " + className + " dirName = " + dirName);
        try {
            FileInputStream is = new FileInputStream(filePath);
            //执行插桩
            byte[] byteCode = AsmUtils.doInsertWithAsm(is, className);
            String hex = Utils.hex(byteCode);
            is.close();

            FileOutputStream os = new FileOutputStream(filePath);
            os.write(byteCode);
            os.close();

            newHexs.put(className, hex);
            //对比缓存的md5，不一致则放入补丁
            patchGenerator.checkClass(className, hex, byteCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processJar(String applicationName, File file, Map<String, String> newHexs, PatchGenerator patchGenerator) {
        try {
            //  无论是windows还是linux jar包都是 /
            applicationName = applicationName.replaceAll(Matcher.quoteReplacement(File.separator),
                    "/");
            File bakJar = new File(file.getParent(), file.getName() + ".bak");
            JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream(bakJar));

            JarFile jarFile = new JarFile(file);
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry jarEntry = entries.nextElement();

                jarOutputStream.putNextEntry(new JarEntry(jarEntry.getName()));
                InputStream is = jarFile.getInputStream(jarEntry);

                String className = jarEntry.getName();
                if (className.endsWith(".class") && !className.startsWith(applicationName)
                        && !Utils.isAndroidClass(className) && !className.startsWith("com/enjoy" +
                        "/patch")) {

                    byte[] byteCode = AsmUtils.doInsertWithAsm(is, className);
                    String hex = Utils.hex(byteCode);
                    newHexs.put(className, hex);
                    //对比缓存的md5，不一致则放入补丁
                    patchGenerator.checkClass(className, hex, byteCode);
                    jarOutputStream.write(byteCode);
                } else {
                    //输出到临时文件
                    jarOutputStream.write(IOUtils.toByteArray(is));
                }
                is.close();
                jarOutputStream.closeEntry();
            }
            jarOutputStream.close();
            jarFile.close();
            file.delete();
            bakJar.renameTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}














