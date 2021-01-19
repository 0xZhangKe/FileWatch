package com.zhangke.filewatch.common

import android.widget.Toast
import androidx.annotation.StringRes
import com.zhangke.filewatch.utils.appContext

/**
 * Created by ZhangKe on 2020/11/28.
 */

@JvmOverloads
fun toastText(text: CharSequence?, isLong: Boolean = false) {
    if (text.isNullOrBlank()) return
    Toast.makeText(appContext, text, if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
}

@JvmOverloads
fun toastText(@StringRes resId: Int, isLong: Boolean = false) {
    if (resId == 0) return
    Toast.makeText(appContext, resId, if (isLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
}
