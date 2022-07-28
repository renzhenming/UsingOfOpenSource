package com.rzm.testapplication.oom.koom

import android.app.Application
import android.content.Context
import android.os.Looper
import com.kwai.koom.base.DefaultInitTask
import com.kwai.koom.base.MonitorManager
import com.kwai.koom.javaoom.monitor.OOMHprofUploader
import com.kwai.koom.javaoom.monitor.OOMMonitor
import com.kwai.koom.javaoom.monitor.OOMMonitorConfig
import com.kwai.koom.javaoom.monitor.OOMReportUploader
import com.rzm.testapplication.LogUtils
import com.rzm.testapplication.startup.my_startup.startup.AndroidStartup
import java.io.File

class KoomTask : AndroidStartup<Void>() {
    override fun callCreateOnMainThread(): Boolean {
        return true
    }

    override fun waitOnMainThread(): Boolean {
        return true
    }

    override fun create(context: Context?): Void? {
        val t = if (Looper.myLooper() == Looper.getMainLooper()) "主线程: " else "子线程: "
        LogUtils.log("$t KoomTask：start")
        if (context !is Application) {
            LogUtils.log("$t KoomTask：context is not an Application")
            return null
        }

        DefaultInitTask.init(context as Application)
        val config = OOMMonitorConfig.Builder()
            .setThreadThreshold(50) //50 only for test! Please use default value!
            .setFdThreshold(300) // 300 only for test! Please use default value!
            .setHeapThreshold(0.9f) // 0.9f for test! Please use default value!
            .setVssSizeThreshold(1_000_000) // 1_000_000 for test! Please use default value!
            .setMaxOverThresholdCount(1) // 1 for test! Please use default value!
            .setAnalysisMaxTimesPerVersion(3) // Consider use default value！
            .setAnalysisPeriodPerVersion(15 * 24 * 60 * 60 * 1000) // Consider use default value！
            .setLoopInterval(5_000) // 5_000 for test! Please use default value!
            .setEnableHprofDumpAnalysis(true)
            .setHprofUploader(object : OOMHprofUploader {
                override fun upload(file: File, type: OOMHprofUploader.HprofType) {
                    LogUtils.log("OOMMonitor ============== todo, upload hprof ${file.name} if necessary")
                }
            })
            .setReportUploader(object : OOMReportUploader {
                override fun upload(file: File, content: String) {
                    LogUtils.log("OOMMonitor ==============   $content")
                    LogUtils.log("OOMMonitor ============== todo, upload report ${file.name} if necessary")
                }
            })
            .build()

        MonitorManager.addMonitorConfig(config)
//        OOMMonitor.startLoop(true, postAtFront = false, delayMillis = 5_000L)
        OOMMonitor.startLoop(clearQueue = true, postAtFront = false, delayMillis = 5_000L)
        LogUtils.log("$t KoomTask：end")

        //OOMMonitor.stopLoop()//停止 OOMMonitor，通常不用主动停止
        return null
    }
}