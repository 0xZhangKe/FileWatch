package com.zhangke.filewatch.utils

import java.io.File
import java.util.Stack

/**
 * Created by ZhangKe on 2021/1/19.
 */
class FileHelper(val file: File) {

    fun refresh() {

    }

    private fun getChildCountAndSize(file: File): Pair<Int, Long> {
        var fileCount = 0
        var size = 0L
        val stack = Stack<File>()
        stack.push(file)
        while (stack.isNotEmpty()) {
            val item = stack.pop()
            fileCount++
            if (item.isFile) {
                size += item.length()
            } else {
                item.listFiles()?.forEach {
                    stack.push(it)
                }
            }
        }
        return fileCount to size
    }
}