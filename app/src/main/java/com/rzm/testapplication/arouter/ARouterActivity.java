package com.rzm.testapplication.arouter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.rzm.testapplication.R;

public class ARouterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arouter);
    }

    public void goTest(View view) {
        ARouter.getInstance().build("/test/ARouterActivity2")
                .withString("name", "zhangsan")
                .withInt("age", 11)
                .withBoolean("boy", false)
                .withObject("obj", new Test("Jack", "Rose"))
                .navigation();
    }
}