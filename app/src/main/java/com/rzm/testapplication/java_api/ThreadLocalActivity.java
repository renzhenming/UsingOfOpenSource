package com.rzm.testapplication.java_api;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.rzm.testapplication.R;

public class ThreadLocalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_local);


    }

    public void start(View view) {
        InheritableThreadLocal<Integer> threadLocal2 = new InheritableThreadLocal<>();
        threadLocal2.set(6);
        System.out.println("父线程获取数据：" + threadLocal2.get());

        new Thread(() -> {
            System.out.println("子线程获取数据：" + threadLocal2.get());
        }).start();



        ThreadLocal<Byte[]> threadLocal = new ThreadLocal<>();
        for (int i = 0; i < 50; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    threadLocal.set(new Byte[20480000]);
                }
            });
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}