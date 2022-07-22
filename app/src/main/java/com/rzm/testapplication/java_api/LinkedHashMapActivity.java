package com.rzm.testapplication.java_api;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.rzm.testapplication.R;

import java.util.LinkedHashMap;

public class LinkedHashMapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linked_hash_map);

        LinkedHashMap<String,String> map = new LinkedHashMap<>();
        map.put("1","ssfe");
        map.put("1","ssfe");
        map.put("1","ssfe");
        map.put("1","ssfe");
        String s = map.get("1");
    }
}