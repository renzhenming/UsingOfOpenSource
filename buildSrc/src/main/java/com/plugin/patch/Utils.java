package com.plugin.patch;

import com.android.build.gradle.internal.pipeline.TransformTask;
import com.android.build.gradle.internal.transforms.ProGuardTransform;

import org.apache.commons.codec.digest.DigestUtils;
import org.gradle.api.Task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Utils {

    public static String capitalize(String self) {
        return isEmpty(self) ? "" :
                "" + Character.toUpperCase(self.charAt(0)) + self.subSequence(1, self.length());
    }

    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean isEmpty(Map map) {
        return map == null || map.isEmpty();
    }


    public static void applyMapping(Task proguardTask, File mappingFile) {
        //上一次混淆的mapping文件存在并且 也开启了混淆任务
        if (mappingFile.exists() && proguardTask != null) {
            //将上次混淆的mapping应用到本次
            TransformTask task = (TransformTask) proguardTask;
            ProGuardTransform transform = (ProGuardTransform) task.getTransform();
            transform.applyTestedMapping(mappingFile);
        }
    }


    public static boolean isAndroidClass(String filePath) {
        return filePath.startsWith("android") ||
                filePath.startsWith("androidx") ||
                filePath.contains("/google/android/") ||
                filePath.contains("R.class") ||
                filePath.contains("R$id.class") ||
                filePath.contains("R$styleable.class") ||
                filePath.contains("R$style.class") ||
                filePath.contains("R$string.class") ||
                filePath.contains("R$color.class") ||
                filePath.contains("R$dimen.class") ||
                filePath.contains("R$integer.class") ||
                filePath.contains("R$layout.class") ||
                filePath.contains("R$attr.class") ||
                filePath.contains("R$drawable.class") ||
                filePath.contains("R$bool.class") ||
                filePath.contains("R$animator.class") ||
                filePath.contains("R$interpolator.class") ||
                filePath.contains("R$anim.class") ||
                filePath.contains("R$mipmap.class");

    }


    public static String hex(byte[] byteCode) {
        try {
            return DigestUtils.md5Hex(byteCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Map<String, String> readHex(File hexFile) {
        Map<String, String> hashMap = new HashMap<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(hexFile)));
            String line;
            while ((line = br.readLine()) != null) {
                String[] list = line.split(":");
                if (list != null && list.length == 2) {
                    hashMap.put(list[0], list[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return hashMap;
    }

    public static void writeHex(Map<String, String> hexs, File hexFile) {
        try {
            FileOutputStream os = new FileOutputStream(hexFile);
            for (String key : hexs.keySet()) {
                String value = hexs.get(key);
                String line = key + ":" + value + "\n";
                os.write(line.getBytes());
            }
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
