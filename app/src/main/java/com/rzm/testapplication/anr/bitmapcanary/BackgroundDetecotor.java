package com.rzm.testapplication.anr.bitmapcanary;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.view.View;


public class BackgroundDetecotor extends Detector<View> {
    public BackgroundDetecotor() {
        super();
    }

    @Override
    public void detect(View view) {
        Drawable backGroupDrawable = view.getBackground();
        if (backGroupDrawable instanceof StateListDrawable) {
            backGroupDrawable = backGroupDrawable.getCurrent();
        }
        if (backGroupDrawable != null && backGroupDrawable instanceof BitmapDrawable) {
            Bitmap bitmap = ((BitmapDrawable) backGroupDrawable).getBitmap();
            if (bitmap.getHeight() > view.getHeight() * DrawableDetectUtil.MAX_SCALE
                    || bitmap.getWidth() > view.getWidth() * DrawableDetectUtil.MAX_SCALE) {
                markScaleView(bitmap, view);
            }
        }
    }
}
