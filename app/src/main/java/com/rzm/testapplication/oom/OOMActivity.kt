package com.rzm.testapplication.oom

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.rzm.testapplication.R
import com.rzm.testapplication.oom.koom.KoomActivity
import com.rzm.testapplication.oom.leakcanary.LeakCanaryActivity

class OOMActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oomactivity)
    }

    fun koom(view: View) {
        startActivity(Intent(applicationContext, KoomActivity::class.java))
    }

    fun leakcanary(view: View) {
        startActivity(Intent(applicationContext, LeakCanaryActivity::class.java))
    }
}