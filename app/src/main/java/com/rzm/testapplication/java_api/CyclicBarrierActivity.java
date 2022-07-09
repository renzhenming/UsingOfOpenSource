package com.rzm.testapplication.java_api;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.rzm.testapplication.R;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cyclic_barrier);


        CyclicBarrier barrier = new CyclicBarrier(8);
        for (int i = 0; i < 8; i++) {
            int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "就位");
                    try {
                        Thread.sleep(finalI * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    try {
                        barrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                    System.out.println("全部就位，" + Thread.currentThread().getName() + "起跑");
                }
            }).start();
        }
    }
}