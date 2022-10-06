package com.zhangke.websocket.util;

import android.os.Handler;
import android.os.Looper;

public class ThreadUtil {

    private static Handler sMainHandler;

    /**
     * 是否为主线程
     */
    public static boolean checkMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    /**
     * 将 Runnable 运行在主线程
     */
    public static void runOnMainThread(Runnable runnable) {
        checkMainHandlerIsNull();
        sMainHandler.post(runnable);
    }

    private static void checkMainHandlerIsNull() {
        if (sMainHandler == null) {
            sMainHandler = new Handler(Looper.getMainLooper());
        }
    }
}
