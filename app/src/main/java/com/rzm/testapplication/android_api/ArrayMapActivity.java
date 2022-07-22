package com.rzm.testapplication.android_api;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.ArrayMap;

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

        String s = map.get("1");
    }
}