package com.rzm.testapplication.dokit

import android.content.Context
import android.os.Looper
import com.rzm.testapplication.LogUtils
import com.rzm.testapplication.startup.my_startup.startup.AndroidStartup

class DokitTask : AndroidStartup<Void>() {
    override fun callCreateOnMainThread(): Boolean {
        return true
    }

    override fun waitOnMainThread(): Boolean {
        return true
    }

    override fun create(context: Context): Void? {
        val t = if (Looper.myLooper() == Looper.getMainLooper()) "主线程: " else "子线程: "
        LogUtils.log("$t DokitTask：start")

        //编译没有通过
        //TODO Execution failed for task ':app:processDebugManifest'.
        //TODO org.xml.sax.SAXParseException; systemId: file:/Users/renzhenming/AndroidStudioProjects/TestApplication/app/build/intermediates/merged_manifests/debug/; lineNumber: 1; columnNumber: 1; 前言中不允许有内容。
//        DoKit.Builder(context as Application)
//            .productId("DokitTask___")
//            .build()

        Thread.sleep(300)

        LogUtils.log("$t DokitTask：start")
        return null
    }
}