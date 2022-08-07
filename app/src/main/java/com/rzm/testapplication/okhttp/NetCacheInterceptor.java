package com.rzm.testapplication.okhttp;

import android.text.TextUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class NetCacheInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response originResponse = chain.proceed(request);
        //  .cacheControl(cacheControl)中会添加"Cache-Control"
        if (!TextUtils.isEmpty(request.header("Cache-Control"))) {
            originResponse = originResponse.newBuilder()
                    .removeHeader("pragma")
                    .header("Cache-Control", request.header("Cache-Control"))
                    .build();
        }

        return originResponse;
    }
}
