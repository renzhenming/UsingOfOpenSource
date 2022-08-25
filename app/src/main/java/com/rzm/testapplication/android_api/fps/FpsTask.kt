package com.rzm.testapplication.android_api.fps

import android.content.Context
import android.view.Choreographer
import com.rzm.testapplication.LogUtils
import com.rzm.testapplication.startup.my_startup.startup.AndroidStartup

class FpsTask : AndroidStartup<Void>() {
    override fun callCreateOnMainThread(): Boolean {
        return true
    }

    override fun waitOnMainThread(): Boolean {
        return true
    }

    override fun create(context: Context?): Void? {
        Choreographer.getInstance().postFrameCallback(FPSFrameCallback(System.nanoTime()))
        return null
    }

    class FPSFrameCallback(lastFrameTimeNanos: Long) : Choreographer.FrameCallback {

        var mLastFrameTimeNanos: Long = 0
        var mFrameIntervalNanos: Int = 0

        init {
            mLastFrameTimeNanos = lastFrameTimeNanos
            mFrameIntervalNanos = (1000000000 / 60)
        }

        override fun doFrame(frameTimeNanos: Long) {
            if (mLastFrameTimeNanos == 0L) {
                mLastFrameTimeNanos = frameTimeNanos;
            }
//            LogUtils.log(
//                "FpsActivity ===== current frame = $frameTimeNanos, last frame = $mLastFrameTimeNanos"
//            )
            var jitterNanos = frameTimeNanos - mLastFrameTimeNanos;
            if (jitterNanos >= mFrameIntervalNanos) {
                var skippedFrames = jitterNanos / mFrameIntervalNanos;
                if (skippedFrames > 30) {
                    //丢帧30以上打印日志
                    LogUtils.log(
                        "FpsActivity ===== Skipped " + skippedFrames + " frames!  "
                                + "The application may be doing too much work on its main thread."
                    )
                    val stack = CommonUtils.getStack()
                    val processName = ProcessUtils.getCurrentProcessName()
                    LogUtils.log("FpsActivity ===== processName = $processName\n stack = $stack \n")

                }
            }
            mLastFrameTimeNanos = frameTimeNanos
            //注册下一帧回调
            Choreographer.getInstance().postFrameCallback(this)
        }
    }

}