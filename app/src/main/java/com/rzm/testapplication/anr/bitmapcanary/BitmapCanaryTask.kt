package com.rzm.testapplication.anr.bitmapcanary

import android.app.Application
import android.content.Context
import android.os.Looper
import com.rzm.testapplication.LogUtils
import com.rzm.testapplication.startup.my_startup.startup.AndroidStartup

class BitmapCanaryTask : AndroidStartup<Void>() {

    override fun callCreateOnMainThread(): Boolean {
        return true
    }

    override fun waitOnMainThread(): Boolean {
        return true
    }

    override fun create(context: Context): Void? {
        val t = if (Looper.myLooper() == Looper.getMainLooper()) "主线程: " else "子线程: "
        LogUtils.log("$t BitmapCanaryTask：start")
        ActivityDrawableWatcher.watchDrawable(context as Application)
        LogUtils.log("$t BitmapCanaryTask：end")
        return null
    }
}