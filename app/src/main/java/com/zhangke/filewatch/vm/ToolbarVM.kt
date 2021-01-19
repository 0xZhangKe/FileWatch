package com.zhangke.filewatch.vm

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zhangke.filewatch.common.MenuItemData
import com.zhangke.filewatch.utils.extractActivity

/**
 * Created by ZhangKe on 2020/12/11.
 */
class ToolbarVM : ViewModel() {

    val backMenuVisible = MutableLiveData(true)

    val onBackClick = MutableLiveData(View.OnClickListener {
        it.context.extractActivity().finish()
    })

    val iconColor = MutableLiveData(0xff333333.toInt())

    val title = MutableLiveData<CharSequence?>()

    val titleColor = MutableLiveData(0xff333333.toInt())

    val menuItems = MutableLiveData<List<MenuItemData>?>()

    val lineVisible = MutableLiveData(true)
}