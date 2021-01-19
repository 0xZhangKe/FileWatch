package com.zhangke.filewatch.db

import android.content.Context
import androidx.room.*
import com.zhangke.filewatch.utils.appContext
import io.reactivex.Completable
import io.reactivex.Single
import java.io.File

/**
 * Created by ZhangKe on 2021/1/19.
 */

private const val DB_NAME = "file_record.db"
private const val TABLE_NAME = "file_record"

@Database(entities = [FileRecord::class], version = 1)
abstract class FileRecordDatabases : RoomDatabase() {

    abstract fun dao(): FileRecordDao

    companion object {

        @JvmStatic
        val instance = createInstance(appContext)

        private fun createInstance(context: Context): FileRecordDatabases {
            return Room.databaseBuilder(context, FileRecordDatabases::class.java, DB_NAME)
                    .allowMainThreadQueries()
                    .build()
        }
    }
}

@Entity(tableName = TABLE_NAME)
data class FileRecord(
        @PrimaryKey val path: String,
        val parent: String?,
        val name: String,
        val childCount: Int,
        val totalSize: Int // 单位 KB
) {

    companion object {

        fun from(parent: File?, file: File, count: Int, size: Long): FileRecord {
            return FileRecord(
                    path = file.absolutePath,
                    parent = parent?.absolutePath,
                    name = file.name,
                    childCount = count,
                    totalSize = (size / 1024).toInt())
        }
    }
}

@Dao
interface FileRecordDao {

    @Query("SELECT * FROM $TABLE_NAME WHERE path = :path")
    fun findByPath(path: String): Single<FileRecord>

    @Query("SELECT * FROM $TABLE_NAME WHERE parent = :parentPath")
    fun findByParent(parentPath: String): Single<List<FileRecord>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(record: FileRecord): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(record: List<FileRecord>): Completable

    @Query("DELETE FROM $TABLE_NAME WHERE path = :path")
    fun deleteByPath(path: String): Completable
}