package com.rzm.testapplication.android_api.viewmodel;

import androidx.lifecycle.ViewModel;

public class MyViewModel extends ViewModel {

    private int number = 0;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        //清楚引用关系，防止内存泄漏
    }
}
