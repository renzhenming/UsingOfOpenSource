package com.rzm.testapplication.android_api;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.rzm.testapplication.R;
import com.rzm.testapplication.android_api.livedata.LiveDataActivity;
import com.rzm.testapplication.android_api.viewmodel.ViewModelActivity;

public class TestAndroidApiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_android_api);
    }

    public void lifeCycle(View view) {
        startActivity(new Intent(getApplicationContext(), LifeCycleActivity.class));
    }

    public void viewModel(View view) {
        startActivity(new Intent(getApplicationContext(), ViewModelActivity.class));
    }

    public void liveData(View view) {
        startActivity(new Intent(getApplicationContext(), LiveDataActivity.class));
    }
}