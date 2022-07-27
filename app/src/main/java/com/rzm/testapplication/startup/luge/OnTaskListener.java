package com.rzm.testapplication.startup.luge;

/***
 * Task interface
 */
public interface OnTaskListener {

    void onTaskStart();

    void onTaskFinish();

    void onStageFinish();

    //... task error
}
