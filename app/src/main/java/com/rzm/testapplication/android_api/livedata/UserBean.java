package com.rzm.testapplication.android_api.livedata;

public class UserBean {

    public String name;
    public int age;

    public UserBean(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
