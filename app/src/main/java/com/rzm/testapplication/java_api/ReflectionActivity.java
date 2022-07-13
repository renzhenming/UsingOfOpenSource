package com.rzm.testapplication.java_api;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.rzm.testapplication.R;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reflection);

        try {
//            Class<?> aClass = Class.forName("com.rzm.testapplication.java_api.ReflectionActivity.MyClass");
            Class<?> aClass = MyClass.class;
            Constructor<?>[] constructors = aClass.getConstructors();
            Field[] declaredFields = aClass.getDeclaredFields();
            Field name = aClass.getDeclaredField("name");
            Method print = aClass.getDeclaredMethod("print",String.class);
            Constructor<?> constructor = aClass.getConstructor(String.class);
            MyClass myClass = (MyClass) constructor.newInstance("rzm");
            myClass.print("ReflectionActivity 我是打印的内容，constructors = " + constructors + " declaredFields = " + declaredFields
                    + " name = " + name + " print = " + print + " constructor = " + constructor);

            print.setAccessible(true);
            print.invoke(myClass,"ReflectionActivity 我是通过invoke方法执行的print");
        } catch (NoSuchFieldException | NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    static class MyClass {

        private String name;

        public MyClass(String name) {
            this.name = name;
        }

        private void print(String arg) {
            System.out.println(arg);
        }
    }
}