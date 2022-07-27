package com.rzm.testapplication.android_api;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AndroidView extends FrameLayout {
    public AndroidView(@NonNull Context context) {
        super(context);
    }

    public AndroidView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AndroidView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(){

    }

}
