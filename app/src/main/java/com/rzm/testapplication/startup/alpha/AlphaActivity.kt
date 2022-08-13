package com.rzm.testapplication.startup.alpha

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.rzm.testapplication.R

class AlphaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alpha)


    }

    fun start(view: View) {
        val test = ConfigTest(applicationContext)
        test.setOnProjectExecuteListener(object : OnProjectExecuteListener() {
            override fun onProjectStart() {}
            override fun onTaskFinish(taskName: String?) {}
            override fun onProjectFinish() {
            }
        })
        test.start()
    }
}