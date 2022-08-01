package com.rzm.testapplication.android_api.windowInsets

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowInsetsAnimation
import com.rzm.testapplication.LogUtils
import com.rzm.testapplication.R

class WindowInsetsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            //非常重要，没有这句话监听无法生效
            window.setDecorFitsSystemWindows(false)
        }
        setContentView(R.layout.activity_window_insets)
        val content = findViewById<View>(R.id.rootView)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val callback =
                object : WindowInsetsAnimation.Callback(DISPATCH_MODE_CONTINUE_ON_SUBTREE) {
                    override fun onProgress(
                        insets: WindowInsets,
                        animations: MutableList<WindowInsetsAnimation>
                    ): WindowInsets {
                        val navigationBars = insets.getInsets(WindowInsets.Type.navigationBars())
                        val ime = insets.getInsets(WindowInsets.Type.ime())
                        LogUtils.log(
                            "WindowInsetsActivity onProgress:" + ime.top +
                                    " " + ime.bottom
                        )
                        val parmas = (content.layoutParams as ViewGroup.MarginLayoutParams)
                        parmas.bottomMargin = ime.bottom - navigationBars.bottom
                        content.layoutParams = parmas
                        return insets
                        return insets
                    }

                    override fun onStart(
                        animation: WindowInsetsAnimation,
                        bounds: WindowInsetsAnimation.Bounds
                    ): WindowInsetsAnimation.Bounds {
                        LogUtils.log(
                            "WindowInsetsActivity onStart:"
                        )
                        return super.onStart(animation, bounds)
                    }

                    override fun onEnd(animation: WindowInsetsAnimation) {
                        super.onEnd(animation)
                        LogUtils.log(
                            "WindowInsetsActivity onEnd:"
                        )
                    }
                }

            content.setWindowInsetsAnimationCallback(callback)

            content.setOnApplyWindowInsetsListener { view, windowInsets ->
                //状态栏
                val statusBars = windowInsets.getInsets(WindowInsets.Type.statusBars())
                //导航栏
                val navigationBars = windowInsets.getInsets(WindowInsets.Type.navigationBars())
                //键盘
                val ime = windowInsets.getInsets(WindowInsets.Type.ime())

                LogUtils.log(
                    "WindowInsetsActivity statusBars: $statusBars navigationBars = $navigationBars ime = $ime"
                )
                windowInsets
            }
        } else {
        }
    }
}