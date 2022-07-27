package com.rzm.testapplication.android_api;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Looper;
import android.util.ArrayMap;
import android.util.Printer;

import com.rzm.testapplication.R;

public class ArrayMapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_array_map);
        ArrayMap<String, String> map = new ArrayMap<>();
        map.put("1", "fsdfds");
        map.put("2", "gfgrf");
        map.put("3", "erwerw");

        map.remove("1");
        Looper.getMainLooper().setMessageLogging(new Printer() {
            @Override
            public void println(String x) {

            }
        });

        String s = map.get("1");
    }
}