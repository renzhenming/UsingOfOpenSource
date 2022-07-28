package com.rzm.testapplication.anr.anrmonitor

import com.rzm.testapplication.LogUtils
import kotlin.concurrent.thread

/**
 * @author lanxiaobin
 * @date 5/30/21
 */
object DeadLockUtil {


    const val TAG = "DeadLockUtil"

    fun createDeadLock() {
        val lock1 = Object()
        val lock2 = Object()

        val thread1 = Thread(Runnable {
            synchronized(lock1) {
                try {
                    Thread.sleep(100)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                LogUtils.log(
                    "DeadLockUtil thread1 wait to get deadLock2,currentThread id = " + Thread.currentThread().id
                )
                synchronized(lock2) { LogUtils.log( "DeadLockUtil thread1") }
            }
        }, "testThread1")
        val thread2 = Thread(Runnable {
            synchronized(lock2) {
                try {
                    Thread.sleep(100)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                LogUtils.log(
                    "DeadLockUtil thread2 wait to get deadLock1,currentThread id = " + Thread.currentThread().id
                )
                synchronized(lock1) { LogUtils.log(  "DeadLockUtil thread2") }
            }
        }, "testThread2")
        thread1.start()
        thread2.start()
    }

    fun createDeadLockAnr() {
        val lock1 = Object()
        val lock2 = Object()
        //子线程持有锁1，想要竞争锁2
        thread {
            synchronized(lock1) {
                Thread.sleep(100)

                synchronized(lock2) {
                    LogUtils.log("DeadLockUtil testAnr: getLock2")
                }
            }
        }

        //主线程持有锁2，想要竞争锁1
        synchronized(lock2) {
            Thread.sleep(100)

            synchronized(lock1) {
                LogUtils.log("DeadLockUtil testAnr: getLock1")
            }
        }
    }
}