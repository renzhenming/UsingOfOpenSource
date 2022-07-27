package com.rzm.testapplication.startup.tasks;

import android.content.Context;
import android.os.Looper;
import android.os.SystemClock;

import com.rzm.testapplication.startup.startup.AndroidStartup;
import com.rzm.testapplication.LogUtils;
import com.rzm.testapplication.startup.startup.Startup;

import java.util.ArrayList;
import java.util.List;

public class Task4 extends AndroidStartup<Void> {

    static List<Class<? extends Startup<?>>> depends;

    static {
        depends = new ArrayList<>();
        depends.add(Task2.class);
    }

    @Override
    public Void create(Context context) {
        String t = Looper.myLooper() == Looper.getMainLooper()
                ? "主线程: " : "子线程: ";
        LogUtils.log(t + " Task4：学习Http");
        SystemClock.sleep(1_00);
        LogUtils.log(t + " Task4：掌握Http");
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
