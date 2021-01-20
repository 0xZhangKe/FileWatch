package com.zhangke.filewatch.db

import android.util.Log
import com.zhangke.filewatch.utils.FileUtil
import com.zhangke.filewatch.utils.ioSubscribeUiObserve
import io.reactivex.Completable
import io.reactivex.Single
import java.io.File

/**
 * Created by ZhangKe on 2021/1/19.
 */
object FileRecordRepo {

    fun findByPath(path: String): Single<FileRecord> {
        return FileRecordDatabases.instance
            .dao()
            .findByPath(path)
            .ioSubscribeUiObserve()
    }

    fun findByParent(parentPath: String): Single<List<FileRecord>> {
        return FileRecordDatabases.instance
            .dao()
            .findByParent(parentPath)
            .ioSubscribeUiObserve()
    }

    fun findByParentUseLocal(parentPath: String, forceRequest: Boolean): Single<List<FileRecord>> {
        val fileLocalSource = Single.create<List<File>> {
            it.onSuccess(File(parentPath).listFiles()?.asList() ?: emptyList())
        }
        val dbSource = FileRecordDatabases.instance
            .dao()
            .findByParent(parentPath)
        return Single.zip(fileLocalSource, dbSource, { t1, t2 -> Pair(t1, t2) })
            .map { convertPairToFileRecord(parentPath, it, forceRequest) }
            .ioSubscribeUiObserve()
    }

    private fun convertPairToFileRecord(parentPath: String, pair: Pair<List<File>, List<FileRecord>>, forceRequest: Boolean): List<FileRecord> {
        val realFiles = pair.first
        val recordList = pair.second
        val result = ArrayList<FileRecord>()
        if (!forceRequest && recordList.isEmpty()) return emptyList()
        result += recordList
        realFiles.asSequence()
            .filter { item -> recordList.firstOrNull { it.path == item.absolutePath } == null }
            .map {
                val (count, size) = FileUtil.getChildCountAndSize(it)
                FileRecord.from(
                    parent = File(parentPath),
                    file = it,
                    count,
                    size
                )
            }.also {
                result += it
            }
        return result
    }

    fun insert(record: FileRecord): Completable {
        return FileRecordDatabases.instance
            .dao()
            .insert(record)
            .ioSubscribeUiObserve()
    }

    fun insert(record: List<FileRecord>): Completable {
        Log.d("ZK_TEST", "FileRecordRepo insert(record = ${record.size})")
        return FileRecordDatabases.instance
            .dao()
            .insert(record)
            .ioSubscribeUiObserve()
    }

    fun deleteByPath(path: String): Completable {
        return FileRecordDatabases.instance
            .dao()
            .deleteByPath(path)
            .ioSubscribeUiObserve()
    }
}