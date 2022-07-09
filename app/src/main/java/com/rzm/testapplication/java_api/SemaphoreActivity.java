package com.rzm.testapplication.java_api;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.rzm.testapplication.R;

import java.util.concurrent.Semaphore;

public class SemaphoreActivity extends AppCompatActivity {

    /**
     * Semaphore是一个计数信号量，可以维护当前访问自身的线程个数，并提供了同步机制。常用于限制可以访问某些资源的线程数量，
     * 使用场景：接口限流
     * <p>
     * 如下示例代码，设置初始值为5，表示同时只能最多5个线程访问，我们在下边开启了8个线程，通过打印结果可以看出，
     * 启动之后立即有5个线程开始执行，4s之后，这5个线程release掉，其他三个线程才开始执行，
     * 可以表明Semaphore可以控制线程访问数量的结论
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semaphore);

        Semaphore semaphore = new Semaphore(5);
        for (int i = 0; i < 8; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        semaphore.acquire();
                        System.out.println(Thread.currentThread().getName() + "开始执行" + " time=" + System.currentTimeMillis() / 1000);
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        semaphore.release();
                    }
                }
            }).start();
        }
    }
}