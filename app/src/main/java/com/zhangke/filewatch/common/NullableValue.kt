package com.zhangke.filewatch.common

/**
 * Created by ZhangKe on 2020/12/30.
 */
data class NullableValue<T>(
    val value: T? = null
) {

    companion object {
        val EMPTY = NullableValue<Any>()

        fun <T> empty() = NullableValue<T>()
    }
}
