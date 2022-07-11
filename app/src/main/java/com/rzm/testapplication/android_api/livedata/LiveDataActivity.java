package com.rzm.testapplication.android_api.livedata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.view.View;

import com.rzm.testapplication.R;

public class LiveDataActivity extends AppCompatActivity {

    private MutableLiveData<UserBean> liveData = new MutableLiveData(new UserBean("rzm", 30));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_data);

        //忽略生命周期
        liveData.observeForever(new Observer<UserBean>() {
            @Override
            public void onChanged(UserBean userBean) {

            }
        });

        liveData.observe(this, new Observer<UserBean>() {
            @Override
            public void onChanged(UserBean userBean) {
                System.out.println("LiveDataActivity: " + userBean);
            }
        });
    }

    public void change(View view) {
        liveData.setValue(new UserBean("rzm", 18));

        new Thread(new Runnable() {
            @Override
            public void run() {
                liveData.postValue(new UserBean("rzm", 18));
            }
        }).start();
    }
}