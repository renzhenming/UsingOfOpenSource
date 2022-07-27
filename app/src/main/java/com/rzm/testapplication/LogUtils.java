package com.rzm.testapplication;

import android.util.Log;

public class LogUtils {
    private static final String TAG = "LogUtils";

    public static void log(String msg) {
        Log.i(TAG, msg);
    }
}
