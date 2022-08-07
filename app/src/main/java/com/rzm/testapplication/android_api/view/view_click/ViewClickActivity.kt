package com.rzm.testapplication.android_api.view.view_click

import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.TouchDelegate
import android.view.View
import android.view.ViewGroup
import com.rzm.testapplication.R

/**
 * 扩大view的点击范围
 */
class ViewClickActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_click)

        var button = findViewById<View>(R.id.button);

        val rect = Rect()
        var touchDelegate = TouchDelegate(rect,button)
       (button.parent as ViewGroup).touchDelegate = touchDelegate
    }
}