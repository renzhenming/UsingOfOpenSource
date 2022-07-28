package com.rzm.testapplication.oom.koom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.rzm.testapplication.LogUtils
import com.rzm.testapplication.R
import kotlin.concurrent.thread

/**
 * VSS - Virtual Set Size 虚拟耗用内存
 * RSS - Resident Set Size 实际使用物理内存（包含共享库内存）
 * USS - Unique Set Size 进程独自占用的物理内存（不包含共享库内存）
 * PSS - Proportional Set Size 实际使用的物理内存（共享库内存按比例占用）
 */
class KoomActivity : AppCompatActivity() {

    var list = mutableListOf<OOMBean>();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_koom)
    }

    fun start(view: View) {
        thread {
            var i = 0
            do {
                list.add(OOMBean())
                Thread.sleep(1000)
                i++;
                LogUtils.log("KoomActivity 创建第$i 个对象")
            } while (i < 1000)
        }
    }
}