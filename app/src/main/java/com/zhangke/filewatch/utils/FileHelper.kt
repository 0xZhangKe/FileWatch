package com.zhangke.filewatch.utils

import android.util.Log
import com.zhangke.filewatch.db.FileRecord
import com.zhangke.filewatch.db.FileRecordRepo
import io.reactivex.Completable
import java.io.File

/**
 * Created by ZhangKe on 2021/1/19.
 */
class FileHelper(
    private val file: File,
    private val onDirChanged: (File) -> Unit
) {

    private val oneMB = 1024 * 1024
    private val countThreshold = 300
    private val sizeThreshold = 200 * oneMB

    private val recordList = ArrayList<FileRecord>(1000)

    fun refresh(): Completable {
        return Completable.create {
            val (count, size) = getChildCountAndSize(file, null, recordList)
            recordList += FileRecord.from(null, file, count, size)
            Log.d("ZK_TEST", "All file completed :$file, count:$count, size:$size")
            it.onComplete()
        }.doFinally { saveToLocation(recordList) }
            .ioSubscribeUiObserve()
    }

    private fun getChildCountAndSize(file: File, parentFile: File?, list: ArrayList<FileRecord>): Pair<Int, Long> {
        if (file.isFile) {
            return 1 to file.length()
        }
        var fileCount = 0
        var size = 0L
        file.listFiles()?.forEach {
            val result = getChildCountAndSize(it, file, list)
            fileCount += result.first
            size += result.second
        }
        if (fileCount > countThreshold || size > sizeThreshold) {
            list += FileRecord.from(parentFile, file, fileCount, size)
            onDirChanged(file)
            Log.d("ZK_TEST", "on dir record:$file, count:$fileCount, size:$size")
        }
        Log.d("ZK_TEST", "completed :$file, count:$fileCount, size:$size")
        return fileCount to size
    }

    private fun saveToLocation(list: ArrayList<FileRecord>) {
        FileRecordRepo.insert(list)
            .subscribe({
                toastText("保存成功")
            }, {
                toastText("保存失败：${it.message}")
                it.printStackTrace()
            }).neverDispose()
    }
}