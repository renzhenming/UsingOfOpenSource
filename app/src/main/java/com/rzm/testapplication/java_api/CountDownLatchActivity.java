package com.rzm.testapplication.java_api;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.rzm.testapplication.R;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down_latch);

        //初始化一个CountDownLatch，初始值为8，表示有八个线程需要被统计
        //执行结果
        CountDownLatch count = new CountDownLatch(8);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //CountDownLatch开始等待其他线程结果，注意这个await()方法调
                    //时机不限，可以在count.countDown()之前也可以在之后
                    count.await();
                    System.out.println("所有线程执行完成");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        //开启8个线程
        for (int i = 0; i < 8; i++) {
            int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(finalI * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        System.out.println(Thread.currentThread().getName() + "执行完成");
                        //在每个线程中任务执行完成的时候进行countDown,这个方法要
                        //确保一定执行，所以放在finally中，哪怕发生异常也要执行
                        count.countDown();
                    }
                }
            }).start();
        }
    }
}