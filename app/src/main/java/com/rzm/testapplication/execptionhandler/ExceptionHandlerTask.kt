package com.rzm.testapplication.execptionhandler

import android.content.Context
import android.os.Looper
import android.os.SystemClock
import com.rzm.exceptionhandler.UncaughtCrashHandler
import com.rzm.testapplication.LogUtils
import com.rzm.testapplication.startup.my_startup.startup.AndroidStartup

class ExceptionHandlerTask : AndroidStartup<Void>() {
    override fun callCreateOnMainThread(): Boolean {
        return false
    }

    override fun waitOnMainThread(): Boolean {
        return true
    }

    override fun create(context: Context?): Void? {
        val t = if (Looper.myLooper() == Looper.getMainLooper()) "主线程: " else "子线程: "
        LogUtils.log("$t ExceptionHandlerTask：start")
        UncaughtCrashHandler.getInstance().init(context)
        SystemClock.sleep(0)
        LogUtils.log("$t ExceptionHandlerTask：end")
        return null
    }
}