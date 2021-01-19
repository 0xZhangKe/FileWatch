package com.zhangke.filewatch.utils

import android.util.Log
import com.zhangke.filewatch.multiplier
import java.io.File
import java.util.Stack

/**
 * Created by ZhangKe on 2021/1/19.
 */
object FileUtil {

    private const val separator = "    "
    val separatorArray = ArrayList<String>(30)

    init {
        for (i in 0..30) {
            separatorArray += separator.multiplier(i - 1)
        }
    }

    fun formatSize(k: Int): String {
        val oneG = 1024 * 1024
        val g = k / oneG
        val m = k % oneG / 1024
        val lastK = k % 1024
        val builder = StringBuilder()
        if (g > 0) {
            builder.append(g)
            builder.append("G ")
        }
        if (m > 0) {
            builder.append(m)
            builder.append("M ")
        }
        if (lastK > 0) {
            builder.append(lastK)
            builder.append("K")
        }
        return builder.toString()
    }
}

fun File.printTree() {
    val root = HashMap<File, Int>()
    root += this to 0
    val stack = Stack<HashMap<File, Int>>()
    stack.push(root)
    while (stack.isNotEmpty()) {
        val item = stack.pop()
        val node = item.keys.iterator().next()
        val depth = item[node]!!
        Log.d("QT_TEST", "${FileUtil.separatorArray[depth]}${node.name}")
        node.listFiles()?.forEach {
            val map = HashMap<File, Int>()
            map += it to depth + 1
            stack.push(map)
        }
    }
}