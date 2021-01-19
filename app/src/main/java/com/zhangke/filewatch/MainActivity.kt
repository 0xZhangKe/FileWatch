package com.zhangke.filewatch

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.zhangke.filewatch.ui.FileListActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.util.LinkedList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
            1234
        )

        input.text = "/sdcard/Android/data/com.tencent.mm/MicroMsg".toEditable()

        btn.setOnClickListener {
            FileListActivity.open(this, input.text.toString())
        }
    }

    @SuppressLint("SetTextI18n")
    private fun fileCountAndSize(file: File): Pair<Int, Int> {
        var fileCount = 0
        var size = 0L
        var dirCount = 0
        var deep = 0
        val stack = LinkedList<File>()
        stack.push(file)
        Log.d("QT_TEST", "fileCountAndSize:${file.absolutePath}")
        while (stack.isNotEmpty()) {
            val item = stack.peek()
            val prefixBuilder = StringBuilder()
            for (i in 0..deep) {
                prefixBuilder.append("    ")
            }
            Log.d("QT_TEST", "$prefixBuilder ${item.name}")
            if (item.isFile) {
                fileCount++
            } else {
                dirCount++
                deep--
                item.listFiles()?.forEach {
                    stack.push(it)
                }
                deep++
            }
        }
        return dirCount to fileCount
    }
}

fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)