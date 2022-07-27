package com.rzm.testapplication.startup.app_startup

import android.content.Context
import android.os.Looper
import android.os.SystemClock
import androidx.startup.Initializer
import com.rzm.testapplication.LogUtils

class AppStartupTask1 : Initializer<Object> {
    override fun create(context: Context): Object {
        val t = if (Looper.myLooper() == Looper.getMainLooper()) "主线程: " else "子线程: "
        LogUtils.log("$t AppStartupTask1：start")
        SystemClock.sleep(1000)
        LogUtils.log("$t AppStartupTask1：end")
        return Object()
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}