package com.rzm.testapplication.android_api.view.view_exposure

import android.content.Context
import android.graphics.Rect
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import com.rzm.testapplication.LogUtils

class ExposureViewGroup(
    context: Context?, attrs: AttributeSet?

) : LinearLayout(context, attrs),
    ViewTreeObserver.OnScrollChangedListener {

    private var mFirstValidExposure: Boolean = true
    private var VALIDE_TIME: Long = 2000
    private var mValidTime: Long = VALIDE_TIME

    private val percent = 0.8

    private var data: String? = null
    private var mHandler = Handler(Looper.getMainLooper())

    fun setData(data: String) {
        this.data = data
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        super.onWindowFocusChanged(hasWindowFocus)
        if (data.isNullOrEmpty()){
            return
        }
        LogUtils.log("ExposureViewGroup onWindowFocusChanged hasWindowFocus = $hasWindowFocus data = $data")
    }

    override fun onWindowVisibilityChanged(visibility: Int) {
        super.onWindowVisibilityChanged(visibility)
        if (data.isNullOrEmpty()){
            return
        }
        LogUtils.log("ExposureViewGroup onWindowVisibilityChanged visibility = $visibility data = $data")
//        if (visibility == 0) {
//            //可见
//            if (mValidTime != VALIDE_TIME) {
//                startExposure()
//            }
//        } else {
//            //不可见
//            exitExposure()
//        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (data.isNullOrEmpty()){
            return
        }
        LogUtils.log("ExposureViewGroup onAttachedToWindow data = $data")
        viewTreeObserver.addOnScrollChangedListener(this);

    }

    private var runnable = Runnable {
        LogUtils.log("ExposureViewGroup onScrollChanged 有效曝光产生")
        mFirstValidExposure = true
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (data.isNullOrEmpty()){
            return
        }
        LogUtils.log("ExposureViewGroup onDetachedFromWindow data = $data")
//        mEndTime = System.currentTimeMillis();
//        if (mEndTime - mStartTime > mValidTime) {
//            LogUtils.log("ExposureViewGroup onDetachedFromWindow 有效曝光产生")
//        }
        viewTreeObserver.removeOnScrollChangedListener(this);
    }

    override fun onScrollChanged() {
        if (data.isNullOrEmpty()){
            return
        }
        if (measuredHeight <= 0) {
            return
        }
        val rect = Rect()
        getLocalVisibleRect(rect)
        LogUtils.log("ExposureViewGroup onScrollChanged exposure height = ${rect.bottom - rect.top}, real height = ${measuredHeight}")

        if ((rect.bottom - rect.top) * 1.0f / measuredHeight >= percent) {
            if (mFirstValidExposure) {
                mFirstValidExposure = false
                startExposure()
                LogUtils.log("ExposureViewGroup onScrollChanged 曝光范围超过$percent")
            }
        } else {
            exitExposure()
            mFirstValidExposure = true
        }
//        getLocalVisibleRect()
//        getGlobalVisibleRect();
//        LogUtils.log("ExposureViewGroup onScrollChanged ${location[0]} ${location[1]}")
    }

    private fun startExposure() {
        mHandler.removeCallbacks(runnable)
        mHandler.postDelayed(runnable, mValidTime)
    }

    private fun exitExposure() {
        mHandler.removeCallbacks(runnable)
    }
}
