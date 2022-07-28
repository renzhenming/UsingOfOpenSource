package com.rzm.testapplication.anr.blockcanary

import android.content.Context
import android.os.Looper
import com.github.moduth.blockcanary.BlockCanary
import com.rzm.testapplication.LogUtils
import com.rzm.testapplication.startup.my_startup.startup.AndroidStartup

class BlockCanaryTask : AndroidStartup<Void>() {
    override fun callCreateOnMainThread(): Boolean {
        return true;
    }

    override fun waitOnMainThread(): Boolean {
        return true;
    }

    override fun create(context: Context): Void? {
        val t = if (Looper.myLooper() == Looper.getMainLooper()) "主线程: " else "子线程: "
        LogUtils.log("$t BlockCanaryTask：start")
        BlockCanary.install(context, AppBlockCanaryContext()).start()
        LogUtils.log("$t BlockCanaryTask：end")
        return null
    }
}