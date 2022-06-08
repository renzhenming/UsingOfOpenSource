//package com.rzm.testapplication.asm;
//
//import org.objectweb.asm.ClassReader;
//import org.objectweb.asm.ClassVisitor;
//import org.objectweb.asm.ClassWriter;
//import org.objectweb.asm.MethodVisitor;
//import org.objectweb.asm.Opcodes;
//import org.objectweb.asm.Type;
//import org.objectweb.asm.commons.AdviceAdapter;
//import org.objectweb.asm.commons.Method;
//
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//
//public class AsmTest {
//
//    /**
//     * 1.希望插入的代码是统计时长的几行，先用java代码写出来
//     *
//     *      public class TestAsm {
//     *
//     *     public void test() {
//     *         Date date = new Date();
//     *         long time = date.getTime();
//     *     }
//     *
//     *     public void test2() {
//     *         long start = System.currentTimeMillis();
//     *         try {
//     *             Thread.sleep(2000);
//     *         } catch (InterruptedException e) {
//     *             e.printStackTrace();
//     *         }
//     *         long end = System.currentTimeMillis();
//     *         System.out.println("consume = " + (end - start));
//     *     }
//     * }
//     *
//     * 2.javac TestAsm.java生成class
//     *
//     *      public class TestAsm {
//     *     public TestAsm() {
//     *         long var1 = System.currentTimeMillis();
//     *         long var3 = System.currentTimeMillis();
//     *         com.lang.System.out.println("consume = " + (var3 - var1));
//     *     }
//     *
//     *     public void test() {
//     *         long var1 = System.currentTimeMillis();
//     *         Date var3 = new Date();
//     *         long var4 = var3.getTime();
//     *         long var6 = System.currentTimeMillis();
//     *         com.lang.System.out.println("consume = " + (var6 - var1));
//     *     }
//     *
//     *     public void test2() {
//     *         long var1 = System.currentTimeMillis();
//     *
//     *         try {
//     *             Thread.sleep(2000L);
//     *         } catch (InterruptedException var6) {
//     *             var6.printStackTrace();
//     *         }
//     *
//     *         long var4 = System.currentTimeMillis();
//     *         com.lang.System.out.println("consume = " + (var4 - var1));
//     *     }
//     * }
//     *
//     *
//     * 3.javap -c TestAsm.class生成字节码
//     *
//     *      javac TestAsm.java
//     *
//     * public class com.rzm.testapplication.asm.TestAsm {
//     *   public com.rzm.testapplication.asm.TestAsm();
//     *     Code:
//     *        0: aload_0
//     *        1: invokespecial #1                  // Method java/lang/Object."<init>":()V
//     *        4: return
//     *
//     *   public void test();
//     *     Code:
//     *        0: new           #2                  // class java/util/Date
//     *        3: dup
//     *        4: invokespecial #3                  // Method java/util/Date."<init>":()V
//     *        7: astore_1
//     *        8: aload_1
//     *        9: invokevirtual #4                  // Method java/util/Date.getTime:()J
//     *       12: lstore_2
//     *       13: return
//     *
//     *   public void test2();
//     *     Code:
//     *        0: invokestatic  #5                  // Method java/lang/System.currentTimeMillis:()J
//     *        3: lstore_1
//     *        4: ldc2_w        #6                  // long 2000l
//     *        7: invokestatic  #8                  // Method java/lang/Thread.sleep:(J)V
//     *       10: goto          18
//     *       13: astore_3
//     *       14: aload_3
//     *       15: invokevirtual #10                 // Method java/lang/InterruptedException.printStackTrace:()V
//     *       18: invokestatic  #5                  // Method java/lang/System.currentTimeMillis:()J
//     *       21: lstore_3
//     *       22: getstatic     #11                 // Field java/lang/System.out:Ljava/io/PrintStream;
//     *       25: new           #12                 // class java/lang/StringBuilder
//     *       28: dup
//     *       29: invokespecial #13                 // Method java/lang/StringBuilder."<init>":()V
//     *       32: ldc           #14                 // String consume =
//     *       34: invokevirtual #15                 // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
//     *       37: lload_3
//     *       38: lload_1
//     *       39: lsub
//     *       40: invokevirtual #16                 // Method java/lang/StringBuilder.append:(J)Ljava/lang/StringBuilder;
//     *       43: invokevirtual #17                 // Method java/lang/StringBuilder.toString:()Ljava/lang/String;
//     *       46: invokevirtual #18                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
//     *       49: return
//     *     Exception table:
//     *        from    to  target type
//     *            4    10    13   Class java/lang/InterruptedException
//     * }
//     *
//     * 4.根据生成的字节码,通过Asm框架写字节码插桩的代码
//     *
//     *
//     * @throws IOException
//     */
//    public void test() throws IOException {
//        FileInputStream fis = new FileInputStream("/Users/renzhenming/AndroidStudioProjects/TestApplication/app/src/main/java/com/rzm/testapplication/asm/TestAsm.class");
//
//
//        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
//        ClassReader cr = new ClassReader(fis);
//
//        ClassVisitor cv = new ClassVisitor(Opcodes.ASM7, cw) {
//            @Override
//            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
//                System.out.println("access = " + access + " name = " + name + " descriptor = " + descriptor + " signature = " + signature + " exceptions = " + exceptions);
//                MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
//                MyMethodVisitor myMethodVisitor = new MyMethodVisitor(Opcodes.ASM7, methodVisitor, access, name, descriptor);
//                return myMethodVisitor;
//            }
//        };
//
//        cr.accept(cv, ClassReader.EXPAND_FRAMES);
//
//        byte[] bytes = cw.toByteArray();
//
//        FileOutputStream fos = new FileOutputStream("/Users/renzhenming/AndroidStudioProjects/TestApplication/app/src/main/java/com/rzm/testapplication/asm/TestAsm.class");
//        fos.write(bytes);
//        fos.close();
//    }
//
//    static class MyMethodVisitor extends AdviceAdapter {
//
//        private int start;
//
//        protected MyMethodVisitor(int api, MethodVisitor methodVisitor, int access, String name, String descriptor) {
//            super(api, methodVisitor, access, name, descriptor);
//        }
//
//        @Override
//        protected void onMethodEnter() {
//            super.onMethodEnter();
//            //long start = System.currentTimeMillis();
//            invokeStatic(Type.getType("Ljava/lang/System;"), new Method("currentTimeMillis", "()J"));
//            start = newLocal(Type.LONG_TYPE);
//            storeLocal(start);
//        }
//
//        /**
//         * 生成
//         * long end = System.currentTimeMillis();
//         * System.out.println("consume = " + (end - start));
//         *
//         * @param opcode
//         */
//        @Override
//        protected void onMethodExit(int opcode) {
//            super.onMethodExit(opcode);
//            //long end = System.currentTimeMillis();
//            //18: invokestatic  #5                  // Method java/lang/System.currentTimeMillis:()J
//            invokeStatic(Type.getType("Ljava/lang/System;"), new Method("currentTimeMillis", "()J"));
//            //21: lstore_3
//            int end = newLocal(Type.LONG_TYPE);
//            storeLocal(end);
//
//            //获取System的静态方法out,out的类型是PrintStream
//            //22: getstatic     #11                 // Field java/lang/System.out:Ljava/io/PrintStream;
//            getStatic(Type.getType("Lcom/lang/System;"), "out", Type.getType("Ljava/io/PrintStream;"));
//
//            //拼接字符串，底层用的StringBuilder
//            //25: new           #12                 // class java/lang/StringBuilder
//            newInstance(Type.getType("Ljava/lang/StringBuilder;"));
//
//            //28: dup
//            dup();
//
//            //29: invokespecial #13                 // Method java/lang/StringBuilder."<init>":()V
//            invokeConstructor(Type.getType("Ljava/lang/StringBuilder;"), new Method("<init>", "()V"));
//
//            //32: ldc           #14                 // String consume =
//            visitLdcInsn("consume = ");
//
//            //34: invokevirtual #15                 // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
//            invokeVirtual(Type.getType("Ljava/lang/StringBuilder;"), new Method("append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;"));
//
//            //37: lload_3
//            loadLocal(end);
//
//            //38: lload_1
//            loadLocal(start);
//
//            //39: lsub
//            math(SUB, Type.LONG_TYPE);
//
//            //40: invokevirtual #16                 // Method java/lang/StringBuilder.append:(J)Ljava/lang/StringBuilder;
//            invokeVirtual(Type.getType("Ljava/lang/StringBuilder;"), new Method("append", "(J)Ljava/lang/StringBuilder;"));
//
//            //43: invokevirtual #17                 // Method java/lang/StringBuilder.toString:()Ljava/lang/String;
//            invokeVirtual(Type.getType("Ljava/lang/StringBuilder;"), new Method("toString", "()Ljava/lang/String;"));
//
//            //46: invokevirtual #18                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
//            invokeVirtual(Type.getType("Ljava/io/PrintStream;"), new Method("println", "(Ljava/lang/String;)V"));
//        }
//    }
//}
