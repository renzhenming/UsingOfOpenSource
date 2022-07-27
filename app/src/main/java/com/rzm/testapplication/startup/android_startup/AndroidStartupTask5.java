package com.rzm.testapplication.startup.android_startup;

import android.content.Context;
import android.os.Looper;
import android.os.SystemClock;

import com.rzm.testapplication.LogUtils;
import com.rousetime.android_startup.AndroidStartup;
import com.rousetime.android_startup.Startup;

import java.util.ArrayList;
import java.util.List;

public class AndroidStartupTask5 extends AndroidStartup<Void> {

    static List<Class<? extends Startup<?>>> depends;

    static {
        depends = new ArrayList<>();
        depends.add(AndroidStartupTask3.class);
        depends.add(AndroidStartupTask4.class);
    }

    @Override
    public Void create(Context context) {
        String t = Looper.myLooper() == Looper.getMainLooper()
                ? "主线程: " : "子线程: ";
        LogUtils.log(t + " AndroidStartupTask5：start");
        SystemClock.sleep(200);
        LogUtils.log(t + " AndroidStartupTask5：end");
        return null;
    }

    @Override
    public boolean callCreateOnMainThread() {
        return false;
    }

    @Override
    public boolean waitOnMainThread() {
        return true;
    }

    //执行此任务需要依赖哪些任务
    @Override
    public List<Class<? extends Startup<?>>> dependencies() {
        return depends;
    }

}
