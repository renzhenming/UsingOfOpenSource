package com.rzm.testapplication.android_api;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;

import com.rzm.testapplication.R;
import com.rzm.testapplication.android_api.ams.ActivityManagerServiceActivity;
import com.rzm.testapplication.android_api.databinding.DatabindingActivity;
import com.rzm.testapplication.android_api.livedata.LiveDataActivity;
import com.rzm.testapplication.android_api.viewmodel.ViewModelActivity;
import com.rzm.testapplication.android_api.windowInsets.WindowInsetsActivity;
import com.rzm.testapplication.android_api.wms.WindowManagerServiceActivity;

public class TestAndroidApiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_android_api);
    }

    public void windowInsets(View view) {
        startActivity(new Intent(getApplicationContext(), WindowInsetsActivity.class));
    }

    public void dialog(View view) {
        startActivity(new Intent(getApplicationContext(), DialogActivity.class));
    }

    public void arrayMap(View view) {
        startActivity(new Intent(getApplicationContext(), ArrayMapActivity.class));
    }

    public void wms(View view) {
        startActivity(new Intent(getApplicationContext(), WindowManagerServiceActivity.class));
    }

    public void ams(View view) {
        startActivity(new Intent(getApplicationContext(), ActivityManagerServiceActivity.class));
    }

    public void dataBinding(View view) {
        startActivity(new Intent(getApplicationContext(), DatabindingActivity.class));
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