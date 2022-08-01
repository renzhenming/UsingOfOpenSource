package com.rzm.testapplication.argusapm

import android.content.Context
import android.os.Looper
import com.argusapm.android.api.Client
import com.argusapm.android.core.Config
import com.argusapm.android.network.cloudrule.RuleSyncRequest
import com.argusapm.android.network.upload.CollectDataSyncUpload
import com.rzm.testapplication.LogUtils
import com.rzm.testapplication.startup.my_startup.startup.AndroidStartup


/**
 * https://github.com/Qihoo360/ArgusAPM/wiki/移动性能监控-SDK-详细集成文档
 */
class ApmTask : AndroidStartup<Void>() {
    override fun callCreateOnMainThread(): Boolean {
        return true
    }

    override fun waitOnMainThread(): Boolean {
        return true
    }

    override fun create(context: Context?): Void? {
        val t = if (Looper.myLooper() == Looper.getMainLooper()) "主线程: " else "子线程: "
        LogUtils.log("$t ApmTask：start")
        val builder: Config.ConfigBuilder = Config.ConfigBuilder()
            .setAppContext(context)
            .setAppName("TestApplication")
            .setRuleRequest(RuleSyncRequest())
            .setUpload(CollectDataSyncUpload())
            .setAppVersion("0.0.1")
            .setApmid("TestApplication_argus_apm_id")


        Client.attach(builder.build())
        Client.isDebugOpen(true,"APM_360")
        Client.startWork()
        LogUtils.log("$t ApmTask：end")
        return null
    }
}