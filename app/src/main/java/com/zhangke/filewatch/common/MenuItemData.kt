package com.zhangke.filewatch.common

import android.view.View
import androidx.core.internal.view.SupportMenuItem

/**
 * Created by ZhangKe on 2020/12/11.
 */
data class MenuItemData(
    val title: String = "",
    val actionView: View? = null,
    var actionEnum: Int = SupportMenuItem.SHOW_AS_ACTION_IF_ROOM,
    val action: ((View) -> Unit)?
)
