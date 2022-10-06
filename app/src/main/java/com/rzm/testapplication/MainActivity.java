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
import com.rzm.testapplication.anr.AnrActivity;
import com.rzm.testapplication.argusapm.ArgusApmActivity;
import com.rzm.testapplication.kotllincoroutine.CoroutineKotlinActivity;
import com.rzm.testapplication.router.arouter.ARouterActivity;
import com.rzm.testapplication.dokit.DokitActivity;
import com.rzm.testapplication.fragment.TestFragmentActivity;
import com.rzm.testapplication.glide.GlideActivity;
import com.rzm.testapplication.java_api.TestJavaApiActivity;
import com.rzm.testapplication.oom.OOMActivity;
import com.rzm.testapplication.net.okhttp.OkHttpActivity;
import com.rzm.testapplication.net.retrofit.RetrofitActivity;
import com.rzm.testapplication.startup.StartupActivity;
import com.rzm.testapplication.web_socket.WebSocketActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogUtils.log("MainActivity onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtils.log("MainActivity onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtils.log("MainActivity onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.log("MainActivity onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtils.log("MainActivity onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtils.log("MainActivity onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.log("MainActivity onDestroy");
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

    public void dokit(View view) {
        startActivity(new Intent(getApplicationContext(), DokitActivity.class));
    }

    public void argus(View view) {
        startActivity(new Intent(getApplicationContext(), ArgusApmActivity.class));
    }

    public void anr(View view) {
        startActivity(new Intent(getApplicationContext(), AnrActivity.class));
    }

    public void startup(View view) {
        startActivity(new Intent(getApplicationContext(), StartupActivity.class));
    }

    public void oom(View view) {
        startActivity(new Intent(getApplicationContext(), OOMActivity.class));
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

    public void kcoroutine(View view) {
        startActivity(new Intent(getApplicationContext(), CoroutineKotlinActivity.class));
    }

    public void WebSocket(View view) {
        startActivity(new Intent(getApplicationContext(), WebSocketActivity.class));
    }
}