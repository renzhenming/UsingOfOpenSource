package com.rzm.testapplication.singleTon;

/**
 * 不使用synchronized实现单例方法2
 *
 * ThreadLocal这种写法主要是考察面试者对于ThreadLocal的理解，
 * 以及是否可以把知识活学活用，但是实际上，这种所谓的"单例"，其实失去了单例的意义
 */
public class ThreadLocal_Singleton {
    private static final ThreadLocal<ThreadLocal_Singleton> singleton =
            new ThreadLocal<ThreadLocal_Singleton>() {
                @Override
                protected ThreadLocal_Singleton initialValue() {
                    return new ThreadLocal_Singleton();
                }
            };

    public static ThreadLocal_Singleton getInstance() {
        return singleton.get();
    }

    private ThreadLocal_Singleton() {
    }
}
