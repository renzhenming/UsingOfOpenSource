package com.rzm.testapplication.startup.luge;

import android.util.Log;

public interface LogInfo {

    public void e(String tag, String msg, Throwable e);

    public void d(String tag, String msg);


    public static class DefaultLogger implements LogInfo {

        @Override
        public void e(String tag, String msg, Throwable e) {
            Log.e(tag, msg);
        }

        @Override
        public void d(String tag, String msg) {
            Log.d(tag, msg);
        }
    }
}
