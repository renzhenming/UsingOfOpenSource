package com.rzm.testapplication.anr

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.rzm.testapplication.LogUtils
import com.rzm.testapplication.R
import com.rzm.testapplication.anr.anrmonitor.AnrMonitorActivity
import com.rzm.testapplication.anr.anrwatchdog.AnrWatchDogActivity
import com.rzm.testapplication.anr.bitmapcanary.BitmapCanaryActivity
import com.rzm.testapplication.anr.blockcanary.BlockCanaryActivity
import kotlin.concurrent.thread

class AnrActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anr)
    }

    fun testAnr(view: View) {
        val lock1 = Object()
        val lock2 = Object()
        //子线程持有锁1，想要竞争锁2
        thread {
            synchronized(lock1) {
                Thread.sleep(100)
                synchronized(lock2) {
                    LogUtils.log("testAnr: getLock2")
                }
            }
        }
        //主线程持有锁2，想要竞争锁1
        synchronized(lock2) {
            Thread.sleep(100)
            synchronized(lock1) {
                LogUtils.log("testAnr: getLock1")
            }
        }
    }

    fun blockCanary(view: View) {
        startActivity(Intent(applicationContext, BlockCanaryActivity::class.java))
    }

    fun anrWatchDog(view: View) {
        startActivity(Intent(applicationContext, AnrWatchDogActivity::class.java))
    }

    fun anrMonitor(view: View) {
        startActivity(Intent(applicationContext, AnrMonitorActivity::class.java))
    }

    fun bitmapCanary(view: View) {
        startActivity(Intent(applicationContext, BitmapCanaryActivity::class.java))
    }
}