package com.rzm.testapplication;

import android.content.res.Resources;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rzm.exceptionhandler.UncaughtCrashHandler;

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();

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
}
