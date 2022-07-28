package com.rzm.testapplication.anr.anrwatchdog

import android.R.attr.duration
import android.content.Context
import android.os.Looper
import com.github.anrwatchdog.ANRWatchDog
import com.rzm.testapplication.LogUtils
import com.rzm.testapplication.startup.my_startup.startup.AndroidStartup

/**
 * https://github.com/SalomonBrys/ANR-WatchDog
 */
class AnrWatchDogTask : AndroidStartup<Void>() {

    override fun callCreateOnMainThread(): Boolean {
        return false
    }

    override fun waitOnMainThread(): Boolean {
        return false
    }

    override fun create(context: Context?): Void? {
        val t = if (Looper.myLooper() == Looper.getMainLooper()) "主线程: " else "子线程: "
        LogUtils.log("$t AnrWatchDogTask：start")
        ANRWatchDog()
            .setIgnoreDebugger(true)//忽略调试状态
            .setANRListener { anrError ->
                LogUtils.log("AnrWatchDogTask anrError = ${anrError.message}")
            }
//            .setReportThreadNamePrefix("thread_sdk：") //报告自己的线程,需要将自己的线程名都设置为thread_sdk：
//            .setReportMainThreadOnly() //设置只跟踪主线程
            .setANRInterceptor {
                //拦截器将在报告错误之前调用。拦截器的作用是定义在给定的冻结持续时间下，是否应该引发或推迟ANR错误
                val ret = (5000 - duration).toLong()
                if (ret > 0) {
                    LogUtils.log("Intercepted ANR that is too short ($duration ms), postponing for $ret ms.")
                }
                ret
            }
            .start()
        LogUtils.log("$t AnrWatchDogTask：end")
        return null
    }
}