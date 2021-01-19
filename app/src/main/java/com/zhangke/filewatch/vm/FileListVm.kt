package com.zhangke.filewatch.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zhangke.filewatch.common.LoadingStatus
import com.zhangke.filewatch.db.FileRecordRepo
import com.zhangke.filewatch.utils.neverDispose
import java.io.File

/**
 * Created by ZhangKe on 2021/1/19.
 */
class FileListVm : ViewModel() {

    val toolbarVm = ToolbarVM()

    val emptyVm = EmptyVM()

    val loadingStatus = MutableLiveData(LoadingStatus.LOADING)

    val items = MutableLiveData(listOf<FileItemVm>())

    fun init(filePath: String) {
        loadingStatus.value = LoadingStatus.LOADING
        val file = File(filePath)
        FileRecordRepo.findByParent(file.absolutePath)
            .subscribe({ list ->
                if (list.isEmpty()) {
                    loadingStatus.value = LoadingStatus.EMPTY
                } else {
                    loadingStatus.value = LoadingStatus.CONTENT
                    items.value = list.map { FileItemVm.fromFileRecord(it) }
                }
            }, {
                loadingStatus.value = LoadingStatus.EMPTY
            }).neverDispose()
    }
}