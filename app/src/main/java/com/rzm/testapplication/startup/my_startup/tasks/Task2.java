package com.rzm.testapplication.startup.my_startup.tasks;

import android.content.Context;
import android.os.Looper;
import android.os.SystemClock;

import androidx.annotation.Nullable;

import com.rzm.testapplication.startup.my_startup.startup.AndroidStartup;
import com.rzm.testapplication.LogUtils;
import com.rzm.testapplication.startup.my_startup.startup.Startup;

import java.util.ArrayList;
import java.util.List;

public class Task2 extends AndroidStartup<Void> {
    static List<Class<? extends Startup<?>>> depends;

    static {
        depends = new ArrayList<>();
        depends.add(Task1.class);
    }

    @Nullable
    @Override
    public Void create(Context context) {
        String t = Looper.myLooper() == Looper.getMainLooper()
                ? "主线程: " : "子线程: ";
        LogUtils.log(t + " Task2：学习Socket");
        SystemClock.sleep(50);
        LogUtils.log(t + " Task2：掌握Socket");
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
