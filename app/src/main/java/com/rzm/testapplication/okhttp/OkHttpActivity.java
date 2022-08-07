package com.rzm.testapplication.okhttp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.rzm.testapplication.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Proxy;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.Dispatcher;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.cache.CacheInterceptor;
import okhttp3.internal.io.FileSystem;
import okio.Sink;
import okio.Source;

public class OkHttpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http);

        OkHttpClient client = new OkHttpClient.Builder()
                .dispatcher(new Dispatcher())
                .followRedirects(true) //允许重定向
                .dns(new HttpDns()) // 网络优化之 dns优化
                .addNetworkInterceptor(new NetCacheInterceptor()) //自定义支持Cache-Control
                .proxy(Proxy.NO_PROXY)
                .cache(new Cache(new File(""), 100))
                .cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(@NonNull HttpUrl httpUrl, @NonNull List<Cookie> list) {

                    }

                    @NonNull
                    @Override
                    public List<Cookie> loadForRequest(@NonNull HttpUrl httpUrl) {
                        return null;
                    }
                })
                .followSslRedirects(true) //如果重定向在http到https之间切换，也允许，false则是不允许
                .build();


        RequestBody body = new FormBody.Builder()
                .add("weaid", "1")
                .add("date", "2018-08-13")
                .add("appkey", "10003")
                .add("sign", "b59bc3ef6191eb9f747dd4e83c99f2a4")
                .add("format", "json")
                .build();


        //通过CacheControl.Builder()定制自己的缓存策略，可选的设置方法如下：
        CacheControl cacheControl = new CacheControl.Builder()
                .maxStale(10, TimeUnit.SECONDS)
                .maxAge(10, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .url("https://www.baidu.com")
                .cacheControl(cacheControl)
                .post(body)
                .build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = client.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

            }
        });
    }
}