package com.rzm.testapplication.java_api;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.rzm.testapplication.R;
import com.rzm.testapplication.fragment.TestFragment;

import java.util.ArrayList;

public class ArrayListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_array_list);
        ArrayList<String> list = new ArrayList<>();


        TestFragmentB testFragment = new TestFragmentB();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.fragmentRoot, testFragment);
        transaction.commitAllowingStateLoss();
    }
}