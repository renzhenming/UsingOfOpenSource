package com.rzm.testapplication.asm;

import java.util.Date;

public class TestAsm {

    public void test() {
        Date date = new Date();
        long time = date.getTime();
    }

    public void test2() {
        long start = System.currentTimeMillis();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("consume = " + (end - start));
    }
}
