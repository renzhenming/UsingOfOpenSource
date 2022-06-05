package com.rzm.testapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.rzm.testapplication.glide.GlideActivity;
import com.rzm.testapplication.okhttp.OkHttpActivity;
import com.rzm.testapplication.retrofit.RetrofitActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void testOkhttp(View view) {
        startActivity(new Intent(getApplicationContext(), OkHttpActivity.class));
    }

    public void testGlide(View view) {
        startActivity(new Intent(getApplicationContext(), GlideActivity.class));
    }

    public void testRetrofit(View view) {
        startActivity(new Intent(getApplicationContext(), RetrofitActivity.class));
    }
}