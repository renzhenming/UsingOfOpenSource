package com.rzm.testapplication;

import android.content.ComponentCallbacks;
import android.content.ComponentCallbacks2;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rzm.exceptionhandler.UncaughtCrashHandler;
import com.rzm.testapplication.arouter.ARouterTask;
import com.rzm.testapplication.execptionhandler.ExceptionHandlerTask;
import com.rzm.testapplication.startup.startup.manage.StartupManager;
import com.rzm.testapplication.startup.tasks.Task1;
import com.rzm.testapplication.startup.tasks.Task2;
import com.rzm.testapplication.startup.tasks.Task3;
import com.rzm.testapplication.startup.tasks.Task4;
import com.rzm.testapplication.startup.tasks.Task5;

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();

        new StartupManager.Builder()
                .addStartup(new ExceptionHandlerTask())
                .addStartup(new ARouterTask())
                .addStartup(new Task5())
                .addStartup(new Task4())
                .addStartup(new Task3())
                .addStartup(new Task2())
                .addStartup(new Task1())
                .build(this)
                .start().await();

        ProcessLifecycleOwner.get().getLifecycle().addObserver(new ApplicationObserver());

        registerComponentCallbacks(new ComponentCallbacks() {
            @Override
            public void onConfigurationChanged(@NonNull Configuration newConfig) {

            }

            @Override
            public void onLowMemory() {

            }
        });

        registerComponentCallbacks(new ComponentCallbacks2() {
            @Override
            public void onConfigurationChanged(@NonNull Configuration newConfig) {

            }

            @Override
            public void onLowMemory() {

            }

            @Override
            public void onTrimMemory(int level) {

            }
        });

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
