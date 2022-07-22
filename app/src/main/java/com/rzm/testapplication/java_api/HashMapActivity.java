package com.rzm.testapplication.java_api;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

import com.rzm.testapplication.MainActivity;
import com.rzm.testapplication.R;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;

public class HashMapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hash_map);

        HashMap<String, String> map = new HashMap<>();
        map.put("dsfs","fdsfsd");
        map.put("dsfs","fdsfsd");
        map.put("dsfs","fdsfsd");
        String dsfs = map.get("dsfs");

        Collections.synchronizedMap(map);

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setClassName(getPackageName(),"com.rzm.MainActivity");
        intent.setComponent(new ComponentName(getPackageName(),"com.rzm.MainActivity"));

        new Hashtable();
    }
}