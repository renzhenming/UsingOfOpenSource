package com.rzm.testapplication.anr.anrwatchdog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rzm.testapplication.R

/**
 * https://github.com/lanshifu/ANRMonitorDemo
 * https://github.com/SalomonBrys/ANR-WatchDog
 */
class AnrWatchDogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anr_watch_dog)
    }
}