package com.rzm.testapplication.arouter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.rzm.testapplication.R;

@Route(path = "/test/ARouterActivity2")
public class ARouterActivity2 extends AppCompatActivity {

    @Autowired
    public String name;
    @Autowired
    int age;
    @Autowired(name = "girl") // Map different parameters in the URL by name
    boolean boy;
    @Autowired
    Test obj;    // Support for parsing custom objects, using json pass in URL


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arouter2);
        ARouter.getInstance().inject(this);
        Log.d("param", "name = " + name + " " + "age = " + age + "is boy = " + boy + " " + obj.toString());
    }
}