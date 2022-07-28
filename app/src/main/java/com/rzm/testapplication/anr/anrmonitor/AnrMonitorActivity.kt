package com.rzm.testapplication.anr.anrmonitor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.rzm.testapplication.R

/**
 * https://github.com/lanshifu/ANRMonitorDemo
 * https://blog.csdn.net/xiaxia20220117/article/details/122560820
 */
class AnrMonitorActivity : AppCompatActivity() {

    val anrMonitor = AnrMonitor(this.lifecycle)
    private val deadLockMonitor = DeadLockMonitor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anr_monitor)

    }

    fun makeAnr(view: View) {
        DeadLockUtil.createDeadLockAnr()
    }

    fun startDeadLockMonitor(view: View) {
        deadLockMonitor.startMonitor()
    }

    fun startAnrMonitor(view: View) {
        anrMonitor.start()
    }
}