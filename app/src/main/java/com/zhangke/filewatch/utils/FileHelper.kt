package com.zhangke.filewatch.utils

import com.zhangke.filewatch.db.FileRecord
import com.zhangke.filewatch.db.FileRecordRepo
import io.reactivex.Completable
import io.reactivex.Single
import java.io.File

/**
 * Created by ZhangKe on 2021/1/19.
 */
class FileHelper(val file: File,
                 private val onDirChanged: (File) -> Unit) {

    private val oneMB = 1024 * 1024
    private val countThreshold = 100
    private val sizeThreshold = 50 * oneMB

    fun refresh(): Completable {
        return Single.create<List<FileRecord>> {
            val list = ArrayList<FileRecord>(1000)
            val (count, size) = getChildCountAndSize(file, null, list)
            list += FileRecord.from(null, file, count, size)
        }
                .flatMapCompletable { FileRecordRepo.insert(it) }
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
        }
        return fileCount to size
    }
}