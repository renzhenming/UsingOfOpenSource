package com.rzm.testapplication.oom.leakcanary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.rzm.testapplication.R;

import leakcanary.AppWatcher;

public class LeakCanaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leak_canary);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppWatcher.INSTANCE.manualInstall(getApplication());
    }
}