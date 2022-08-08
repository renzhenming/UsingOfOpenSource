package com.rzm.testapplication.anr.bitmapcanary;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.widget.ImageView;

public class ImagesrcDetector extends Detector<ImageView> {
    public ImagesrcDetector() {
    }

    @Override
    public void detect(ImageView imageView) {
        Drawable srcDrawable = imageView.getDrawable();
        if (srcDrawable instanceof StateListDrawable) {
            srcDrawable = srcDrawable.getCurrent();
        }
        if (srcDrawable instanceof BitmapDrawable) {
            Bitmap bitmap = ((BitmapDrawable) srcDrawable).getBitmap();
            if (bitmap.getHeight() > imageView.getHeight() * DrawableDetectUtil.MAX_SCALE
                    || bitmap.getWidth() > imageView.getWidth() * DrawableDetectUtil.MAX_SCALE) {
                markScaleView(bitmap, imageView);
            }
        }
    }
}
