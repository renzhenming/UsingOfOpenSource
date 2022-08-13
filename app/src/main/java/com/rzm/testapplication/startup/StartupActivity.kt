package com.rzm.testapplication.startup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.rzm.testapplication.R
import com.rzm.testapplication.startup.alpha.AlphaActivity

class StartupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_startup)
    }

    fun alpha(view: View) {
        startActivity(Intent(applicationContext, AlphaActivity::class.java))
    }
}