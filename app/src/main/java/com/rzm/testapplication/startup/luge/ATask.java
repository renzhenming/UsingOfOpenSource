package com.rzm.testapplication.startup.luge;

import java.util.List;

public class ATask extends Task{
    @Override
    public void run() {
        //业务逻辑 推送 图片加载
    }

    @Override
    protected List<Class<? extends Task>> dependencies() {
        return null;
    }

    @Override
    public String getTaskName() {
        return "taskA";
    }

    @Override
    public boolean isWaitOnMainThread() {
        return true;
    }
}
