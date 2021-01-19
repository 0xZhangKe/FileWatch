package com.zhangke.filewatch.common

import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.widget.ImageView
import androidx.core.widget.ImageViewCompat
import androidx.databinding.BindingAdapter

/**
 * Created by ZhangKe on 2020/12/11.
 */
@BindingAdapter("android:tint")
fun ImageView.setImageTintListCompat(tint: ColorStateList?) {
    ImageViewCompat.setImageTintList(this, tint)
}

@BindingAdapter("tint")
fun ImageView.setImageTintListCompat2(tint: ColorStateList?) {
    setImageTintListCompat(tint)
}

@BindingAdapter("android:tintMode")
fun ImageView.setImageTintModeCompat(mode: PorterDuff.Mode?) {
    ImageViewCompat.setImageTintMode(this, mode)
}

@BindingAdapter("tintMode")
fun ImageView.setImageTintModeCompat2(mode: PorterDuff.Mode?) {
    setImageTintModeCompat(mode)
}
