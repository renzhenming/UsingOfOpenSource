package com.rzm.testapplication.java_api;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.rzm.testapplication.R;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicProxyActivity extends AppCompatActivity {

    public interface Husband {
        void careFamily();
    }

    public interface Father {
        void teachChild();
    }

    public class Man implements Husband, Father {

        @Override
        public void careFamily() {

        }

        @Override
        public void teachChild() {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_proxy);
        Man man = new Man();
        Object o = Proxy.newProxyInstance(Man.class.getClassLoader(), new Class[]{Husband.class, Father.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                return method.invoke(man, objects);
            }
        });

        ((Husband) o).careFamily();
        ((Father) o).teachChild();
    }
}

/**
 * 生成的代理类
 *
 */
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//
//
//public final class Husband$Proxy0 extends Proxy implements Husband {
//    private static Method m1;
//    private static Method m2;
//    private static Method m3;
//    private static Method m0;
//
//    //可以看到静态代码块中生成了四个方法，其中有三个equals toString hashCode是每个类自带的（Object）
//    //careFamily是我们接口中提供的方法
//    //类一生成就通过反射获取到了几个关键方法的method
//    static {
//        try {
//            m1 = Class.forName("java.lang.Object").getMethod("equals", Class.forName("java.lang.Object"));
//            m2 = Class.forName("java.lang.Object").getMethod("toString");
//            m3 = Class.forName("com.enjoy.lib.Husband").getMethod("careFamily");
//            m0 = Class.forName("java.lang.Object").getMethod("hashCode");
//        } catch (NoSuchMethodException var2) {
//            throw new NoSuchMethodError(var2.getMessage());
//        } catch (ClassNotFoundException var3) {
//            throw new NoClassDefFoundError(var3.getMessage());
//        }
//    }
//
//    public Husband$Proxy0(InvocationHandler var1) throws  {
//        super(var1);
//    }
//
//    public final boolean equals(Object var1) throws  {
//        try {
//            return (Boolean)super.h.invoke(this, m1, new Object[]{var1});
//        } catch (RuntimeException | Error var3) {
//            throw var3;
//        } catch (Throwable var4) {
//            throw new UndeclaredThrowableException(var4);
//        }
//    }
//
//    public final String toString() throws  {
//        try {
//            return (String)super.h.invoke(this, m2, (Object[])null);
//        } catch (RuntimeException | Error var2) {
//            throw var2;
//        } catch (Throwable var3) {
//            throw new UndeclaredThrowableException(var3);
//        }
//    }
//
//    public final void careFamily() throws  {
//        try {
//            //当外界调用careFamilly方法的时候，会执行到这里，h就是传入的InvocationHandler
//            //m3就是静态代码块中获取到的careFamily方法（来自接口），因为我们方法没有传参数，所以为null
//            super.h.invoke(this, m3, (Object[])null);
//        } catch (RuntimeException | Error var2) {
//            throw var2;
//        } catch (Throwable var3) {
//            throw new UndeclaredThrowableException(var3);
//        }
//    }
//
//    public final int hashCode() throws  {
//        try {
//            return (Integer)super.h.invoke(this, m0, (Object[])null);
//        } catch (RuntimeException | Error var2) {
//            throw var2;
//        } catch (Throwable var3) {
//            throw new UndeclaredThrowableException(var3);
//        }
//    }
//}
