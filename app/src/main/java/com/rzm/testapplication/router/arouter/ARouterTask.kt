package com.rzm.testapplication.router.arouter

import android.app.Application
import android.content.Context
import android.os.Looper
import android.os.SystemClock
import com.alibaba.android.arouter.launcher.ARouter
import com.rzm.testapplication.BuildConfig
import com.rzm.testapplication.LogUtils
import com.rzm.testapplication.startup.my_startup.startup.AndroidStartup
import com.rzm.testapplication.startup.my_startup.startup.manage.ExecutorManager
import java.util.concurrent.Executor

class ARouterTask : AndroidStartup<Void>() {
    override fun callCreateOnMainThread(): Boolean {
        return true
    }

    override fun waitOnMainThread(): Boolean {
        return true
    }

    override fun executor(): Executor {
        return ExecutorManager.mainExecutor
    }

    override fun create(context: Context): Void? {
        val t = if (Looper.myLooper() == Looper.getMainLooper()) "主线程: " else "子线程: "
        LogUtils.log("$t ARouterTask：start")
        if (BuildConfig.DEBUG) {           // These two lines must be written before init, otherwise these configurations will be invalid in the init process
            ARouter.openLog() // Print log
            ARouter.openDebug() // Turn on debugging mode (If you are running in InstantRun mode, you must turn on debug mode! Online version needs to be closed, otherwise there is a security risk)
        }
        if (context is Application) {
            ARouter.init(context)
        }
        SystemClock.sleep(0)
        LogUtils.log("$t ARouterTask：end")
        return null
    }
}