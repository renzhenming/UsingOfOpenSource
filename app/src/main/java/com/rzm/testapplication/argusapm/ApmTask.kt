package com.rzm.testapplication.argusapm

import android.content.Context
import android.os.Looper
import com.argusapm.android.api.ApmTask
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

    override fun create(context: Context): Void? {
        val t = if (Looper.myLooper() == Looper.getMainLooper()) "主线程: " else "子线程: "
        LogUtils.log("$t ApmTask：start")
        //TODO 从ArgusApm demo中拿来的代码
//        val isUi = TextUtils.equals(context.packageName, ProcessUtils.getCurrentProcessName())
        val builder = Config.ConfigBuilder()
            .setAppContext(context)
            .setRuleRequest(RuleSyncRequest())
            .setUpload(CollectDataSyncUpload())
            .setAppName("apm_demo")
            .setAppVersion("1.0.0")
            .setApmid("apm_demo") //该ID是在APM的后台进行申请的
        //单进程应用可忽略builder.setDisabled相关配置。
        //单进程应用可忽略builder.setDisabled相关配置。
//        if (!isUi) { //除了“主进程”，其他进程不需要进行数据上报、清理等逻辑。“主进程”通常为常驻进行，如果无常驻进程，即为UI进程。
       if(true) {
            builder.setDisabled(ApmTask.FLAG_DATA_CLEAN)
                .setDisabled(ApmTask.FLAG_CLOUD_UPDATE)
                .setDisabled(ApmTask.FLAG_DATA_UPLOAD)
                .setDisabled(ApmTask.FLAG_COLLECT_ANR)
                .setDisabled(ApmTask.FLAG_COLLECT_FILE_INFO)
        }
        //builder.setEnabled(ApmTask.FLAG_COLLECT_ACTIVITY_AOP); //activity采用aop方案时打开，默认关闭即可。
        //builder.setEnabled(ApmTask.FLAG_COLLECT_ACTIVITY_AOP); //activity采用aop方案时打开，默认关闭即可。
        builder.setEnabled(ApmTask.FLAG_LOCAL_DEBUG) //是否读取本地配置，默认关闭即可。
        Client.attach(builder.build())
        Client.isDebugOpen(true) //设置成true的时候将会打开悬浮窗
        Client.startWork()
        LogUtils.log("$t ApmTask：end")
        return null
    }
}