package com.rzm.testapplication.singleTon;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 不使用synchronized实现单例方法1
 */
public class CAS_Singleton {
    private static final AtomicReference<CAS_Singleton> INSTANCE = new AtomicReference<CAS_Singleton>();

    private CAS_Singleton() {
    }

    /**
     * CAS的一个重要缺点在于如果忙等待一直执行不成功(一直在死循环中)，会对CPU造成较大的执行开销。
     * 另外，代码中，如果N个线程同时执行到 singleton = new Singleton();的时候，会有大量对象被创建，可能导致内存溢出。
     * @return
     */
    public static CAS_Singleton getInstance() {
        for (; ; ) {
            CAS_Singleton singleton = INSTANCE.get();
            if (null != singleton) {
                return singleton;
            }

            singleton = new CAS_Singleton();
            if (INSTANCE.compareAndSet(null, singleton)) {
                return singleton;
            }
        }
    }
}