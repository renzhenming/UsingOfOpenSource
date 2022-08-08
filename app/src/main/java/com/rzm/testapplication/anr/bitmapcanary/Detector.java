package com.rzm.testapplication.anr.bitmapcanary;

import android.graphics.Bitmap;
import android.os.Build;
import android.view.View;
import android.view.ViewOverlay;

public abstract class Detector<T extends View> {

    public Detector() {

    }

    abstract public void detect(T view);

    protected void markScaleView(Bitmap bitmap, T view) {
        float scale = Math.max(bitmap.getHeight() * 1.0f / view.getHeight(), bitmap.getWidth() * 1.0f / view.getWidth());
        if (Build.VERSION.SDK_INT >= 18) {
            ViewOverlay overlay = view.getOverlay();
            overlay.clear();
            DrawableDetectUtil.TextDetectDrawable detectDrawable = new DrawableDetectUtil.TextDetectDrawable();
            detectDrawable.setText(String.format("%.1f", scale));
            detectDrawable.setBgColor(DrawableDetectUtil.getTipColorByScale(scale));
            detectDrawable.setBounds(0, 0, view.getWidth(), view.getHeight());
            overlay.add(detectDrawable);
        } else {
            view.setBackgroundColor(DrawableDetectUtil.getTipColorByScale(scale));
        }
    }


}
