package com.rzm.testapplication.anr.anrmonitor;

import android.os.Build;
import android.util.Log;

import com.rzm.testapplication.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author lanxiaobin
 * @date 2021/5/11.
 */
public class DeadLockMonitor {

    private String TAG = "DeadLockMonitor";

    static {
        System.loadLibrary("native-lib");
    }

    Thread[] getAllThreads() {
        ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
        while (currentGroup.getParent() != null) {
            currentGroup = currentGroup.getParent();
        }
        int noThreads = currentGroup.activeCount();
        Thread[] lstThreads = new Thread[noThreads];
        currentGroup.enumerate(lstThreads);
        return lstThreads;
    }

    void startMonitor() {
        int nativeInitResult = nativeInit(Build.VERSION.SDK_INT);
        LogUtils.log("nativeInit: " + nativeInitResult);
        HashMap<Integer, DeadLockThread> deadLock = new HashMap<>();

        Thread[] threads = getAllThreads();
        for (Thread thread : threads) {
            if (thread.getState() == Thread.State.BLOCKED) {
                long threadAddress = (long) ReflectUtil.getField(thread, "nativePeer");
                // 这里记一下，找不到地址，或者线程已经挂了，此时获取到的可能是0和-1
                if (threadAddress <= 0) {
                    continue;
                }

                LogUtils.log("thread block ，getId = " + thread.getId() + ",threadAddress=" + threadAddress);

                int blockThreadId = getContentThreadIdArt(threadAddress);

                int curThreadId = getThreadIdFromThreadPtr(threadAddress);

                Log.w(TAG, "blockThread = " + blockThreadId + ",curThreadId=" + curThreadId);
                if (blockThreadId != 0 && curThreadId != 0) {
                    //最后一步是判断哪个线程造成死锁 1->2->3->1  1->2->1
                    deadLock.put(curThreadId, new DeadLockThread(curThreadId, blockThreadId, thread));
                }
            }
        }

        // 将所有情况进行分组
        ArrayList<HashMap<Integer, Thread>> deadLockThreadGroup = deadLockThreadGroup(deadLock);
        for (HashMap<Integer, Thread> group : deadLockThreadGroup) {

            for (Integer curId : group.keySet()) {

                DeadLockThread deadLockThread = deadLock.get(curId);
                if (deadLockThread == null) {
                    continue;
                }

                Thread waitThread = group.get(deadLockThread.blockThreadId);
                Thread deadThread = group.get(deadLockThread.curThreadId);
                if (waitThread == null) {
                    continue;
                }

                //输出
                Log.e(TAG, "startMonitor: waitThread.Name = " + waitThread.getName());
                Log.e(TAG, "startMonitor: deadThread.Name = " + deadThread.getName());

                StringBuffer sb = new StringBuffer("\r\n");
                StackTraceElement[] stackTrace = deadThread.getStackTrace();
                for (StackTraceElement stackTraceElement : stackTrace) {
                    sb.append(stackTraceElement.toString());
                    sb.append("\r\n");
                }
                Log.e(TAG, sb.toString());

            }

        }
    }

    private ArrayList<HashMap<Integer, Thread>> deadLockThreadGroup(HashMap<Integer, DeadLockThread> deadLock) {

        //排重而已？
        HashSet<Integer> hashSet = new HashSet<>();

        ArrayList<HashMap<Integer, Thread>> lockThreadGroup = new ArrayList<>();
        for (Integer currentThreadId : deadLock.keySet()) {
            if (hashSet.contains(currentThreadId)) {
                continue;
            }
            hashSet.add(currentThreadId);
            HashMap<Integer, Thread> deadLockGroup = findDeadLockGroup(currentThreadId, deadLock, new HashMap<Integer, Thread>());

            hashSet.addAll(deadLockGroup.keySet());
            lockThreadGroup.add(deadLockGroup);
        }


        return lockThreadGroup;
    }

    private HashMap<Integer, Thread> findDeadLockGroup(Integer currentThreadId, HashMap<Integer, DeadLockThread> deadLock, HashMap<Integer, Thread> threadHashMap) {

        DeadLockThread deadLockThread = deadLock.get(currentThreadId);
        if (deadLockThread == null) {
            return new HashMap<>();
        }

        //已经走了一轮，例如 1->2 然后 2->1
        if (threadHashMap.containsKey(currentThreadId)) {
            return threadHashMap;
        }

        threadHashMap.put(currentThreadId, deadLockThread.thread);

        // 1->2->3->1   123为一组 3和1为这一组

        return findDeadLockGroup(deadLockThread.blockThreadId, deadLock, threadHashMap);
    }

    class DeadLockThread {
        public DeadLockThread(int curThreadId, int blockThreadId, Thread thread) {
            this.curThreadId = curThreadId;
            this.blockThreadId = blockThreadId;
            this.thread = thread;
        }

        int curThreadId;
        int blockThreadId;
        Thread thread;
    }

    public native int nativeInit(int sdkVersion);

    public native int getContentThreadIdArt(long threadAddress);

    public native int getThreadIdFromThreadPtr(long threadAddress);
}
