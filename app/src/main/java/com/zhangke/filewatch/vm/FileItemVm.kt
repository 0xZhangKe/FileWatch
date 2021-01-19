package com.zhangke.filewatch.vm

import com.zhangke.filewatch.R
import com.zhangke.filewatch.common.ModularizedVm
import com.zhangke.filewatch.db.FileRecord
import com.zhangke.filewatch.utils.FileUtil

/**
 * Created by ZhangKe on 2021/1/19.
 */
class FileItemVm(
    val path: String,
    val desc: String
) : ModularizedVm {

    override val layoutId: Int
        get() = R.layout.item_file

    companion object {

        fun fromFileRecord(fileRecord: FileRecord): FileItemVm {
            return FileItemVm(fileRecord.path, "${fileRecord.childCount}个/${FileUtil.formatSize(fileRecord.totalSize)}")
        }
    }
}