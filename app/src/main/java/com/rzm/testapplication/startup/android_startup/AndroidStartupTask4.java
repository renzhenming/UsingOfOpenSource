package com.rzm.testapplication.startup.android_startup;

import android.content.Context;
import android.os.Looper;
import android.os.SystemClock;

import com.rzm.testapplication.LogUtils;
import com.rousetime.android_startup.AndroidStartup;
import com.rousetime.android_startup.Startup;

import java.util.ArrayList;
import java.util.List;

public class AndroidStartupTask4 extends AndroidStartup<Void> {

    static List<Class<? extends Startup<?>>> depends;

    static {
        depends = new ArrayList<>();
        depends.add(AndroidStartupTask2.class);
    }

    @Override
    public Void create(Context context) {
        String t = Looper.myLooper() == Looper.getMainLooper()
                ? "主线程: " : "子线程: ";
        LogUtils.log(t + " AndroidStartupTask4：start");
        SystemClock.sleep(1_00);
        LogUtils.log(t + " AndroidStartupTask4：end");
        return null;
    }


    @Override
    public List<Class<? extends Startup<?>>> dependencies() {
        return depends;
    }

    @Override
    public boolean callCreateOnMainThread() {
        return false;
    }

    @Override
    public boolean waitOnMainThread() {
        return false;
    }
}
