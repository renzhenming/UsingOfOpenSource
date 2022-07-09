package com.rzm.testapplication.java_api;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.rzm.testapplication.R;

import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linked_blocking_queue);

        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        boolean add = queue.add(1);
        System.out.println("add添加1" + add);
        try {
            queue.put(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        boolean offer = queue.offer(3);
        System.out.println("offer添加3" + offer);

        Integer remove = queue.remove();
        System.out.println("remove取出" + remove);
        Integer poll = queue.poll();
        System.out.println("poll取出" + poll);
        try {
            Integer take = queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("take取出" + poll);
    }
}