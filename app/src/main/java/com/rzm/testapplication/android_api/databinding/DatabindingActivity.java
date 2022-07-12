package com.rzm.testapplication.android_api.databinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.rzm.testapplication.R;
import com.rzm.testapplication.databinding.ActivityDatabindingBinding;

public class DatabindingActivity extends AppCompatActivity {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_databinding);
        ActivityDatabindingBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_databinding);
        user = new User("Derry", "123");
        binding.setUser(user); // 必须要建立绑定关系，否则没有任何效果

        // Model（修改Model的数据）  ---> View（UI的控件就DataBinding自动刷新）  一向 更新
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    try {
                        Thread.sleep(1000);
                        user.setName(user.getName() + "口"); // view.setText(text);
                        user.setPwd(user.getPwd() + "口");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        user.setName("888");
    }
}