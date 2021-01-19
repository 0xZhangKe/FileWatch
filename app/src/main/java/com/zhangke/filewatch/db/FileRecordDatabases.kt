package com.zhangke.filewatch.db

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zhangke.filewatch.utils.appContext
import io.reactivex.Completable
import io.reactivex.Single

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
    val parent: String,
    val name: String,
    val childCount: Int,
    val totalSize: Int // 单位 KB
)

@Dao
interface FileRecordDao {

    @Query("SELECT * FROM $TABLE_NAME WHERE path = :path")
    fun findByPath(path: String): Single<FileRecord>

    @Query("SELECT * FROM $TABLE_NAME WHERE parent = :parentPath")
    fun findByParent(parentPath: String): Single<List<FileRecord>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(record: FileRecord): Completable

    @Query("DELETE FROM $TABLE_NAME WHERE path = :path")
    fun deleteByPath(path: String): Completable
}