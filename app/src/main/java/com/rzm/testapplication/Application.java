package com.rzm.testapplication;

import android.content.res.Resources;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rzm.exceptionhandler.UncaughtCrashHandler;

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ProcessLifecycleOwner.get().getLifecycle().addObserver(new ApplicationObserver());
        UncaughtCrashHandler.getInstance().init(this);

        if (BuildConfig.DEBUG) {           // These two lines must be written before init, otherwise these configurations will be invalid in the init process
            ARouter.openLog();     // Print log
            ARouter.openDebug();   // Turn on debugging mode (If you are running in InstantRun mode, you must turn on debug mode! Online version needs to be closed, otherwise there is a security risk)
        }
        ARouter.init(this);

    }

    @Override
    public Resources getResources() {
        return super.getResources();
    }

    public class ApplicationObserver implements LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        private void onCreate() {
            Log.i("Application", "OnCreate");
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        private void onStart() {
            Log.i("Application", "OnStart");
        }


        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        private void onResume() {
            Log.i("Application", "OnResume");
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        private void onPause() {
            Log.i("Application", "OnPause");
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        private void onStop() {
            Log.i("Application", "OnStop");
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        private void onDestroy() {
            Log.i("Application", "OnPause");
        }
    }

}
