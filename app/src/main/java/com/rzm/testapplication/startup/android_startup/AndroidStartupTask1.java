package com.rzm.testapplication.startup.android_startup;

import android.content.Context;
import android.os.Looper;
import android.os.SystemClock;

import androidx.annotation.Nullable;

import com.rousetime.android_startup.AndroidStartup;
import com.rousetime.android_startup.Startup;
import com.rzm.testapplication.LogUtils;

import java.util.List;

public class AndroidStartupTask1 extends AndroidStartup<String> {

    @Nullable
    @Override
    public String create(Context context) {
        String t = Looper.myLooper() == Looper.getMainLooper()
                ? "主线程: " : "子线程: ";
        LogUtils.log(t+" AndroidStartupTask1：start");
        SystemClock.sleep(3_00);
        LogUtils.log(t+" AndroidStartupTask1：end");
        return "Task1返回数据";
    }


    //执行此任务需要依赖哪些任务
    @Override
    public List<Class<? extends Startup<?>>> dependencies() {
        return super.dependencies();
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
