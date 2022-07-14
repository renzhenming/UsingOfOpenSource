package com.rzm.testapplication.android_api.ams;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import com.rzm.testapplication.R;

public class ActivityManagerServiceActivity extends AppCompatActivity {
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_service);
    }

    public void openProvider(View view) {
    }

    public void registerReceiver(View view) {
        registerReceiver(receiver,new IntentFilter());
    }

    public void startService(View view) {
        startService(new Intent(getApplicationContext(), TestService.class));
    }

    public void startActivity(View view) {
        startActivity(new Intent(getApplicationContext(), ActivityManagerServiceActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}