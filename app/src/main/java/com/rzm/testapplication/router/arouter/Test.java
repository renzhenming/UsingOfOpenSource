package com.rzm.testapplication.router.arouter;

import java.io.Serializable;

public class Test implements Serializable {
    private final String jack;
    private final String rose;

    public Test(String jack, String rose) {
        this.jack = jack;
        this.rose = rose;
    }

    public String getJack() {
        return jack;
    }

    public String getRose() {
        return rose;
    }

    @Override
    public String toString() {
        return "Test{" +
                "jack='" + jack + '\'' +
                ", rose='" + rose + '\'' +
                '}';
    }
}
