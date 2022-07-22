package com.rzm.testapplication.android_api;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.SparseArray;

import com.rzm.testapplication.R;

public class SparseArrayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sparse_array);
        SparseArray<Integer> array = new SparseArray<>();
        array.put(1,1);
        Integer integer = array.get(1);
    }
}