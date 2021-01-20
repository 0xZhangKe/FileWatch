package com.zhangke.filewatch.vm

import android.view.View
import com.zhangke.filewatch.R
import com.zhangke.filewatch.common.ModularizedVm
import com.zhangke.filewatch.db.FileRecord
import com.zhangke.filewatch.utils.FileUtil

/**
 * Created by ZhangKe on 2021/1/19.
 */
class FileItemVm(
    val name: String,
    val desc: String,
    val isFolders: Boolean,
    val onClick: View.OnClickListener
) : ModularizedVm {

    override val layoutId: Int
        get() = R.layout.item_file

    companion object {

        fun fromFileRecord(fileRecord: FileRecord, onClick: View.OnClickListener): FileItemVm {
            val sizeDesc = FileUtil.formatSize(fileRecord.totalSize)
            val desc = if (fileRecord.isFolders) {
                "${fileRecord.childCount}个/$sizeDesc"
            } else {
                sizeDesc
            }
            return FileItemVm(
                fileRecord.name,
                desc,
                fileRecord.isFolders,
                onClick
            )
        }
    }
}