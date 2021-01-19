package com.zhangke.filewatch

import androidx.multidex.MultiDexApplication
import com.zhangke.filewatch.utils.initApplication

/**
 * Created by ZhangKe on 2021/1/19.
 */
class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        initApplication(this)
    }
}