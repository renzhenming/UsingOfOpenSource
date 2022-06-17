package com.rzm.testapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.rzm.testapplication.arouter.ARouterActivity;
import com.rzm.testapplication.glide.GlideActivity;
import com.rzm.testapplication.java_api.TestJavaApiActivity;
import com.rzm.testapplication.leakcanary.LeakCanaryActivity;
import com.rzm.testapplication.okhttp.OkHttpActivity;
import com.rzm.testapplication.retrofit.RetrofitActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void testLeakCanary(View view) {
        startActivity(new Intent(getApplicationContext(), LeakCanaryActivity.class));
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

    public void testARouter(View view) {
        startActivity(new Intent(getApplicationContext(), ARouterActivity.class));
    }

    public void testJavaApi(View view) {
        startActivity(new Intent(getApplicationContext(), TestJavaApiActivity.class));
    }
}