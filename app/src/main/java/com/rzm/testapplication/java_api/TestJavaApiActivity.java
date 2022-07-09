package com.rzm.testapplication.java_api;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.rzm.testapplication.R;

public class TestJavaApiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_java_api);
    }

    public void semaphore(View view) {
        startActivity(new Intent(getApplicationContext(), SemaphoreActivity.class));
    }

    public void cyclicBarrier(View view) {
        startActivity(new Intent(getApplicationContext(), CyclicBarrierActivity.class));
    }

    public void countDownLatch(View view) {
        startActivity(new Intent(getApplicationContext(), CountDownLatchActivity.class));
    }

    public void reentrantLock(View view) {
        startActivity(new Intent(getApplicationContext(), ReentrantLockActivity.class));
    }


    public void linkedBlockingQueue(View view) {
        startActivity(new Intent(getApplicationContext(), LinkedBlockingQueueActivity.class));
    }

    public void arraylist(View view) {
        startActivity(new Intent(getApplicationContext(), ArrayListActivity.class));
    }

    public void hashmap(View view) {
        startActivity(new Intent(getApplicationContext(), HashMapActivity.class));
    }

    public void sparsearray(View view) {
        startActivity(new Intent(getApplicationContext(), SparseArrayActivity.class));
    }

    public void linkedlist(View view) {
        startActivity(new Intent(getApplicationContext(), LinkedListActivity.class));
    }

    public void threadlocal(View view) {
        startActivity(new Intent(getApplicationContext(), ThreadLocalActivity.class));
    }

    public void copyOnWrite(View view) {
        startActivity(new Intent(getApplicationContext(), CopyOnWritArrayListActivity.class));
    }
}