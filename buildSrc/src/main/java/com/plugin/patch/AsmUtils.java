package com.plugin.patch;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;
import org.objectweb.asm.commons.Method;

import java.io.IOException;
import java.io.InputStream;


public class AsmUtils {

    public static byte[] doInsertWithAsm(InputStream inputStream, final String className) throws IOException {
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        ClassReader reader = new ClassReader(inputStream);
        reader.accept(new ClassVisitor(Opcodes.ASM5, writer) {
            @Override
            public MethodVisitor visitMethod(int access, final String name, String desc, String signature, String[] exceptions) {
                MethodVisitor methodVisitor = super.visitMethod(access, name, desc, signature, exceptions);

                AdviceAdapter adviceAdapter = new AdviceAdapter(Opcodes.ASM5, methodVisitor, access, name, desc) {
                    private int start;

                    @Override
                    public void visitInsn(int opcode) {
                        super.visitInsn(opcode);
                        //在构造方法中插入AntilazyLoad引用
                        if ("<init>".equals(name) && opcode == Opcodes.RETURN) {
                            super.visitLdcInsn(Type.getType("Lcom/enjoy/patch/hack/AntilazyLoad;"));
                        }
                        super.visitInsn(opcode);
                    }

                    @Override
                    protected void onMethodEnter() {
                        super.onMethodEnter();
                        //long start = System.currentTimeMillis()
                        invokeStatic(Type.getType("Ljava/lang/System;"), new Method("currentTimeMillis", "()J"));
                        start = newLocal(Type.LONG_TYPE);
                        storeLocal(start);
                    }

                    @Override
                    protected void onMethodExit(int opcode) {
                        super.onMethodExit(opcode);
                        //long end = System.currentTimeMillis();
                        //18: invokestatic  #5                  // Method java/lang/System.currentTimeMillis:()J
                        invokeStatic(Type.getType("Ljava/lang/System;"), new Method("currentTimeMillis", "()J"));
                        //21: lstore_3
                        int end = newLocal(Type.LONG_TYPE);
                        storeLocal(end);

                        //获取System的静态方法out,out的类型是PrintStream
                        //22: getstatic     #11                 // Field java/lang/System.out:Ljava/io/PrintStream;
                        getStatic(Type.getType("Ljava/lang/System;"), "out", Type.getType("Ljava/io/PrintStream;"));

                        //拼接字符串，底层用的StringBuilder
                        //25: new           #12                 // class java/lang/StringBuilder
                        newInstance(Type.getType("Ljava/lang/StringBuilder;"));

                        //28: dup
                        dup();

                        //29: invokespecial #13                 // Method java/lang/StringBuilder."<init>":()V
                        invokeConstructor(Type.getType("Ljava/lang/StringBuilder;"), new Method("<init>", "()V"));

                        //32: ldc           #14                 // String consume =
                        visitLdcInsn(className + " method: " + name + ", consume = ");

                        //34: invokevirtual #15                 // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                        invokeVirtual(Type.getType("Ljava/lang/StringBuilder;"), new Method("append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;"));

                        //37: lload_3
                        loadLocal(end);

                        //38: lload_1
                        loadLocal(start);

                        //39: lsub
                        math(SUB, Type.LONG_TYPE);

                        //40: invokevirtual #16                 // Method java/lang/StringBuilder.append:(J)Ljava/lang/StringBuilder;
                        invokeVirtual(Type.getType("Ljava/lang/StringBuilder;"), new Method("append", "(J)Ljava/lang/StringBuilder;"));

                        //43: invokevirtual #17                 // Method java/lang/StringBuilder.toString:()Ljava/lang/String;
                        invokeVirtual(Type.getType("Ljava/lang/StringBuilder;"), new Method("toString", "()Ljava/lang/String;"));

                        //46: invokevirtual #18                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
                        invokeVirtual(Type.getType("Ljava/io/PrintStream;"), new Method("println", "(Ljava/lang/String;)V"));
                    }
                };


                return adviceAdapter;
            }
        }, ClassReader.EXPAND_FRAMES);
        byte[] bytes = writer.toByteArray();
        return bytes;
    }
}
