package com.rzm.testapplication.android_api.viewmodel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.rzm.testapplication.R;

public class ViewModelActivity extends AppCompatActivity {

    private MyViewModel myViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_model);

        System.out.println("ViewModelActivity,getCanonicalName:" + MyViewModel.class.getCanonicalName());
        myViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MyViewModel.class);
    }

    public void change(View view) {
        TextView textView = findViewById(R.id.text);
        int number = myViewModel.getNumber();
        myViewModel.setNumber(++number);
        textView.setText(String.valueOf(myViewModel.getNumber()));
    }

    @Nullable
    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        Log.d("DDD", "横竖屏切换了 onRetainCustomNonConfigurationInstance: ");
        return "DDDDDDD";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}