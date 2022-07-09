package com.rzm.testapplication.java_api;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.rzm.testapplication.R;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aqsactivity);

        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        lock.unlock();
    }
}