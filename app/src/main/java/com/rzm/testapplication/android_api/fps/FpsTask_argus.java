package com.rzm.testapplication.android_api.fps;

import android.os.Handler;
import android.os.Looper;
import android.view.Choreographer;

import com.argusapm.android.api.ApmTask;
import com.rzm.testapplication.LogUtils;

import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * fps收集task
 *
 * @author ArgusAPM Team
 */
public class FpsTask_argus implements Choreographer.FrameCallback {
    private long mLastFrameTimeNanos = 0; //最后一次时间
    private long mFrameTimeNanos = 0; //本次的当前时间
    private int mFpsCount = 0;
    private Handler mMainHandler = new Handler(Looper.getMainLooper());
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private JSONObject paramsJson = new JSONObject();
    //定时任务
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            calculateFPS();
            //实现分段采集
            mMainHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    executorService.execute(runnable);
                }
            }, 1000);
        }
    };

    private void calculateFPS() {
        if (mLastFrameTimeNanos == 0) {
            mLastFrameTimeNanos = mFrameTimeNanos;
            return;
        }
        float costTime = (float) (mFrameTimeNanos - mLastFrameTimeNanos) / 1000000.0F;
        if (mFpsCount <= 0 && costTime <= 0.0F) {
            return;
        }
        int fpsResult = (int) (mFpsCount * 1000 / costTime);
        if (fpsResult < 0) {
            return;
        }
        if (fpsResult <= 30) {
            String stack = CommonUtils.getStack();
            String processName = ProcessUtils.getCurrentProcessName();

            LogUtils.log("FpsTask_argus processName = " + processName + "\n" + " stack = " + stack + " \n");
        }
        mLastFrameTimeNanos = mFrameTimeNanos;
        mFpsCount = 0;
    }

    public void start() {
        mMainHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                executorService.execute(runnable);
            }
        }, 1000);
        Choreographer.getInstance().postFrameCallback(this);
    }

    @Override
    public void doFrame(long frameTimeNanos) {
        mFpsCount++;
        mFrameTimeNanos = frameTimeNanos;
        //注册下一帧回调
        Choreographer.getInstance().postFrameCallback(this);
    }
}