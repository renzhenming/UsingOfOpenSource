package com.rzm.testapplication.startup.my_startup.startup.run;

import android.content.Context;
import android.os.Process;

import com.rzm.testapplication.startup.my_startup.startup.Result;
import com.rzm.testapplication.startup.my_startup.startup.Startup;
import com.rzm.testapplication.startup.my_startup.startup.manage.StartupCacheManager;
import com.rzm.testapplication.startup.my_startup.startup.manage.StartupManager;

public class StartupRunnable implements Runnable {
    private StartupManager startupManager;
    private Startup<?> startup;
    private Context context;

    public StartupRunnable(Context context, Startup<?> startup,
                           StartupManager startupManager) {
        this.context = context;
        this.startup = startup;
        this.startupManager = startupManager;
    }

    @Override
    public void run() {
        Process.setThreadPriority(startup.getThreadPriority());
        startup.toWait();
        Object result = startup.create(context);
        StartupCacheManager.getInstance().saveInitializedComponent(startup.getClass(),
                new Result(result));

        startupManager.notifyChildren(startup);

    }
}
