package com.rzm.testapplication.android_api.databinding;

import android.graphics.BitmapRegionDecoder;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

public class ImageLoaderAdapter {

    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView view, String url) {
        Glide.with(view).load(url).into(view);
    }
}
