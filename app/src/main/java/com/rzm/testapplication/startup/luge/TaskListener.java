package com.rzm.testapplication.startup.luge;

/***
 * dag 的主要的目的 任务的调度
 */
public interface TaskListener {
    void onWaitRunning(Task task);
    void onStart(Task task);

    /**
     * @param task
     * @param dw Time to wait for execution
     * @param df Time consuming task execution
     */
    void onFinish(Task task, long dw, long df);
}
