package com.zhangke.filewatch.db

import com.zhangke.filewatch.utils.ioSubscribeUiObserve
import io.reactivex.Completable
import io.reactivex.Single

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

    fun insert(record: FileRecord): Completable {
        return FileRecordDatabases.instance
                .dao()
                .insert(record)
                .ioSubscribeUiObserve()
    }

    fun insert(record: List<FileRecord>): Completable {
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