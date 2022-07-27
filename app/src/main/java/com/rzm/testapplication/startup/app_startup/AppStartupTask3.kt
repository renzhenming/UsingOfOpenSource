package com.rzm.testapplication.startup.app_startup

import android.content.Context
import android.os.Looper
import android.os.SystemClock
import androidx.startup.Initializer
import com.rzm.testapplication.LogUtils

class AppStartupTask3 : Initializer<Object> {
    override fun create(context: Context): Object {
        val t = if (Looper.myLooper() == Looper.getMainLooper()) "主线程: " else "子线程: "
        LogUtils.log("$t AppStartupTask3：start")
        SystemClock.sleep(400)
        LogUtils.log("$t AppStartupTask3：end")
        return Object()
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        val mutableListOf = mutableListOf<Class<out Initializer<*>>>()
        mutableListOf.add(AppStartupTask1::class.java)
        return mutableListOf
    }
}