package com.zhangke.filewatch.common

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.view.menu.MenuItemImpl
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuItemCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by ZhangKe on 2020/11/28.
 */

@BindingAdapter("visible")
fun View.setViewVisible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

@SuppressLint("RestrictedApi") // 为了使overflow menu里item的点击事件生效
@BindingAdapter("menuItems")
fun Toolbar.setMenuItems(items: List<MenuItemData>?) {
    menu.clear()
    items?.forEach { item ->
        menu.add(item.title).apply {
            actionView = item.actionView
            setShowAsAction(item.actionEnum)
            actionView.setOnClickListener { item.action?.invoke(it) }
            (this as MenuItemImpl).setCallback { item.action?.invoke(actionView) }
            if (!item.title.endsWith("按钮")) {
                MenuItemCompat.setContentDescription(this, "${title}按钮")
            }
        }
    }
}

@SuppressLint("ResourceType")
@BindingAdapter(
    value = ["loadingStatus", "contentId", "errorId", "emptyId", "loadingId"],
    requireAll = false
)
fun ViewGroup.bindLoadingStatus(
    loadingStatus: LoadingStatus,
    @LayoutRes contentId: Int,
    @LayoutRes errorId: Int? = null,
    @LayoutRes emptyId: Int? = null,
    @LayoutRes loadingId: Int? = null
) {
    val content = findViewById<View>(contentId)
    content.setViewVisible(loadingStatus == LoadingStatus.CONTENT)
    errorId?.let {
        findViewById<View>(it).setViewVisible(loadingStatus == LoadingStatus.ERROR)
    }
    emptyId?.let {
        findViewById<View>(it).setViewVisible(loadingStatus == LoadingStatus.EMPTY)
    }
    loadingId?.let {
        findViewById<View>(it).setViewVisible(loadingStatus == LoadingStatus.LOADING)
    }
}

@BindingAdapter("itemDecoration")
fun RecyclerView.bindItemDecoration(decoration: RecyclerView.ItemDecoration) {
    addItemDecoration(decoration)
}

@BindingAdapter("itemDecorations")
fun RecyclerView.bindItemDecorations(vararg decorations: RecyclerView.ItemDecoration) {
    decorations.forEach {
        addItemDecoration(it)
    }
}