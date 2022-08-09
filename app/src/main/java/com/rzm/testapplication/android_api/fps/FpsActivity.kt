package com.rzm.testapplication.android_api.fps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Choreographer
import android.view.View
import com.rzm.testapplication.LogUtils
import com.rzm.testapplication.R

class FpsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fps)
    }

    fun listen(view: View) {

    }
}