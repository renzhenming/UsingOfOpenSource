package com.rzm.testapplication.router.wmrouter;

import androidx.annotation.NonNull;

/**
 * 用于配置组件
 * <p>
 * Created by jzj on 2018/4/28.
 */
public class RouterComponents {

    @NonNull
    private static IFactory sDefaultFactory = DefaultFactory.INSTANCE;

    @NonNull
    public static IFactory getDefaultFactory() {
        return sDefaultFactory;
    }
}
