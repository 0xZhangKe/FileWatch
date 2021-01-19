package com.zhangke.filewatch.common

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.internal.view.SupportMenuItem
import androidx.core.widget.ImageViewCompat
import com.zhangke.filewatch.R

/**
 * Created by ZhangKe on 2020/12/12.
 */
object MenuItemFactory {

    fun newTextItem(
        context: Context,
        title: String,
        color: Int = 0xff333333.toInt(),
        actionEnum: Int = SupportMenuItem.SHOW_AS_ACTION_IF_ROOM,
        action: ((View) -> Unit)
    ): MenuItemData {
        return MenuItemData(
            title,
            createTextView(context, title, color),
            actionEnum,
            action
        )
    }

    fun newImageItem(
        context: Context,
        title: String,
        @DrawableRes resId: Int,
        tintColor: Int? = null,
        actionEnum: Int = SupportMenuItem.SHOW_AS_ACTION_IF_ROOM,
        action: ((View) -> Unit)
    ): MenuItemData {
        val view = createImageView(context, resId, tintColor)
        return MenuItemData(
            title,
            view,
            actionEnum,
            action
        )
    }

    private fun createTextView(
        context: Context,
        text: String,
        color: Int = 0xff333333.toInt()
    ): TextView {
        val textView = LayoutInflater.from(context).inflate(
            R.layout.menu_item_text,
            LinearLayout(context), false
        ) as TextView
        textView.text = text
        textView.setTextColor(color)
        return textView
    }

    private fun createImageView(
        context: Context,
        @DrawableRes resId: Int,
        tintColor: Int?
    ): View {
        val imageView = LayoutInflater.from(context).inflate(
            R.layout.menu_item_imageview,
            LinearLayout(context), false
        ) as ImageView
        imageView.setImageResource(resId)
        if (tintColor != null) {
            ImageViewCompat.setImageTintList(imageView, ColorStateList.valueOf(tintColor))
        }
        return imageView
    }

}