package com.rzm.testapplication.startup.android_startup;

import android.content.Context;
import android.os.Looper;
import android.os.SystemClock;

import androidx.annotation.Nullable;

import com.rzm.testapplication.LogUtils;
import com.rousetime.android_startup.AndroidStartup;
import com.rousetime.android_startup.Startup;

import java.util.ArrayList;
import java.util.List;

public class AndroidStartupTask2 extends AndroidStartup<Void> {
    static List<Class<? extends Startup<?>>> depends;

    static {
        depends = new ArrayList<>();
        depends.add(AndroidStartupTask1.class);
    }

    @Nullable
    @Override
    public Void create(Context context) {
        String t = Looper.myLooper() == Looper.getMainLooper()
                ? "主线程: " : "子线程: ";
        LogUtils.log(t + " AndroidStartupTask2：start");
        SystemClock.sleep(200);
        LogUtils.log(t + " AndroidStartupTask2：end");
        return null;
    }

    @Override
    public boolean callCreateOnMainThread() {
        return true;
    }

    @Override
    public boolean waitOnMainThread() {
        return false;
    }

    @Override
    public List<Class<? extends Startup<?>>> dependencies() {
        return depends;
    }
}
