package com.rzm.testapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.util.AttributeSet;
import android.view.View;

import com.rzm.testapplication.android_api.TestAndroidApiActivity;
import com.rzm.testapplication.arouter.ARouterActivity;
import com.rzm.testapplication.blockcanary.BlockCanaryActivity;
import com.rzm.testapplication.fragment.TestFragmentActivity;
import com.rzm.testapplication.glide.GlideActivity;
import com.rzm.testapplication.java_api.TestJavaApiActivity;
import com.rzm.testapplication.leakcanary.LeakCanaryActivity;
import com.rzm.testapplication.okhttp.OkHttpActivity;
import com.rzm.testapplication.retrofit.RetrofitActivity;
import com.rzm.testapplication.startup.StartupActivity;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogUtils.log("MainActivity onCreate");
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Debug.stopMethodTracing();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }


    public void blockCanary(View view) {
        startActivity(new Intent(getApplicationContext(), BlockCanaryActivity.class));
    }

    public void startup(View view) {
        startActivity(new Intent(getApplicationContext(), StartupActivity.class));
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

    public void testAndroidApi(View view) {
        startActivity(new Intent(getApplicationContext(), TestAndroidApiActivity.class));
    }

    public void testFragment(View view) {
        startActivity(new Intent(getApplicationContext(), TestFragmentActivity.class));
    }
}