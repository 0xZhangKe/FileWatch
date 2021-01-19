package com.zhangke.filewatch

/**
 * Created by ZhangKe on 2021/1/16.
 */


fun String.multiplier(count: Int): String {
    val builder = StringBuilder()
    for (i in 0..count) {
        builder.append(this)
    }
    return builder.toString()
}