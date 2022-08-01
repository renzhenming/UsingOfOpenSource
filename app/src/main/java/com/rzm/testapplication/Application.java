package com.rzm.testapplication;

import android.content.ComponentCallbacks;
import android.content.ComponentCallbacks2;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Debug;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.rzm.testapplication.anr.anrwatchdog.AnrWatchDogTask;
import com.rzm.testapplication.anr.blockcanary.BlockCanaryTask;
import com.rzm.testapplication.argusapm.ApmTask;
import com.rzm.testapplication.arouter.ARouterTask;
import com.rzm.testapplication.execptionhandler.ExceptionHandlerTask;
import com.rzm.testapplication.oom.koom.KoomTask;
import com.rzm.testapplication.startup.my_startup.startup.manage.StartupManager;
import com.rzm.testapplication.startup.my_startup.tasks.Task1;
import com.rzm.testapplication.startup.my_startup.tasks.Task2;
import com.rzm.testapplication.startup.my_startup.tasks.Task3;
import com.rzm.testapplication.startup.my_startup.tasks.Task4;
import com.rzm.testapplication.startup.my_startup.tasks.Task5;

import java.io.File;

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Debug.startMethodTracingSampling(new File(getFilesDir(), "trace2").getAbsolutePath(), 8 * 1024 * 1024, 1000);

        //my-startup
        new StartupManager.Builder()
                .addStartup(new ApmTask())
                .addStartup(new KoomTask())
                .addStartup(new BlockCanaryTask())
                .addStartup(new ExceptionHandlerTask())
                .addStartup(new ARouterTask())
                .addStartup(new AnrWatchDogTask())
                .addStartup(new Task5())
                .addStartup(new Task4())
                .addStartup(new Task3())
                .addStartup(new Task2())
                .addStartup(new Task1())
                .build(this)
                .start().await();
        LogUtils.log("StartupManager tasks all finished");

        //android-startup
//        https://juejin.cn/post/6859500445669752846
//        new com.rousetime.android_startup.StartupManager.Builder()
//                .addStartup(new AndroidStartupTask1())
//                .addStartup(new AndroidStartupTask2())
//                .addStartup(new AndroidStartupTask3())
//                .addStartup(new AndroidStartupTask4())
//                .addStartup(new AndroidStartupTask5())
//                .build(this)
//                .start()
//                .await();

        //app startup
//        AppInitializer.getInstance(this)
//                .initializeComponent(AppStartupTask5.class);

        LogUtils.log("AndroidStartupManager tasks all finished");

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
