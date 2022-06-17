package com.rzm.testapplication.java_api;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.rzm.testapplication.R;
import com.rzm.testapplication.glide.GlideActivity;

public class TestJavaApiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_java_api);
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
}