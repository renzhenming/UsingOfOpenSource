package com.rzm.testapplication.startup.my_startup.startup;


import android.os.Process;

import com.rzm.testapplication.startup.my_startup.startup.manage.ExecutorManager;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

/**
 * https://mp.weixin.qq.com/s?__biz=MzIzNTc5NDY4Nw==&mid=2247484560&idx=1&sn=39e02098d66d6228970bdc8df09fba42&chksm=e8e0fd00df977416066c25f6560e27ba0ad75abb3ab0ade0afd85df866850671a997dc609c5d&token=846567760&lang=zh_CN#rd
 *
 * @param <T>
 */
public abstract class AndroidStartup<T> implements Startup<T> {
    private CountDownLatch mWaitCountDown = new CountDownLatch(getDependenciesCount());

    @Override
    public List<Class<? extends Startup<?>>> dependencies() {
        return null;
    }

    @Override
    public int getDependenciesCount() {
        List<Class<? extends Startup<?>>> dependencies = dependencies();
        return dependencies == null ? 0 : dependencies.size();
    }


    @Override
    public Executor executor() {
        return ExecutorManager.ioExecutor;
    }

    @Override
    public void toWait() {
        try {
            mWaitCountDown.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void toNotify() {
        mWaitCountDown.countDown();
    }

    @Override
    public int getThreadPriority() {
        return Process.THREAD_PRIORITY_DEFAULT;
    }
}
