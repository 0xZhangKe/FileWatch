package com.zhangke.filewatch.vm

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zhangke.filewatch.R
import com.zhangke.filewatch.utils.appContext

/**
 * Created by ZhangKe on 2020/12/12.
 */
class EmptyVM : ViewModel() {

    val emptyTip = MutableLiveData(appContext.getString(R.string.content_empty))

    val emptyClick = MutableLiveData<View.OnClickListener?>()

    fun buildEmptyTip(
        tip: MutableLiveData<String>,
        click: MutableLiveData<View.OnClickListener?>
    ): CharSequence? {
        if (tip.value.isNullOrEmpty()) return null
        val builder = StringBuilder()
        builder.append(tip.value)
        if (click.value != null) {
            builder.append(",")
            builder.append(appContext.getString(R.string.retry_tip))
        }
        return builder.toString()
    }
}