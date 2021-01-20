package com.zhangke.filewatch.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zhangke.filewatch.common.LoadingStatus
import com.zhangke.filewatch.db.FileRecord
import com.zhangke.filewatch.db.FileRecordRepo
import com.zhangke.filewatch.utils.neverDispose
import java.io.File
import java.util.Stack

/**
 * Created by ZhangKe on 2021/1/19.
 */
class FileListVm : ViewModel() {

    private val fileStack = Stack<String>()

    val toolbarVm = ToolbarVM()

    val emptyVm = EmptyVM()

    val loadingStatus = MutableLiveData(LoadingStatus.LOADING)

    val items = MutableLiveData(listOf<FileItemVm>())

    private var initPath = ""

    fun initCurrentPath(path: String) {
        initPath = path
        fileStack.apply {
            clear()
            pushPath(path)
        }
    }

    fun onBackPressed(): Boolean {
        return if (fileStack.size <= 1) {
            true
        } else {
            popPath()
            false
        }
    }

    private fun requestFileData(filePath: String) {
        computeTitle(filePath)
        loadingStatus.value = LoadingStatus.LOADING
        val file = File(filePath)
        FileRecordRepo.findByParentUseLocal(file.absolutePath, initPath != filePath)
            .subscribe({ list ->
                if (list.isEmpty()) {
                    loadingStatus.value = LoadingStatus.EMPTY
                } else {
                    loadingStatus.value = LoadingStatus.CONTENT
                    items.value = list.map { convertFileToVM(it) }
                }
            }, {
                loadingStatus.value = LoadingStatus.EMPTY
            }).neverDispose()
    }

    private fun computeTitle(filePath: String) {
        val initName = File(initPath).name
        val curEndPath = filePath.removePrefix(initPath)
        toolbarVm.title.value = "$initName/$curEndPath"
    }

    private fun convertFileToVM(record: FileRecord): FileItemVm {
        return FileItemVm.fromFileRecord(record) {
            if (record.isFolders) {
                pushPath(record.path)
            }
        }
    }

    private fun pushPath(path: String) {
        fileStack.push(path)
        onStackChanged()
    }

    private fun popPath() {
        fileStack.pop()
        onStackChanged()
    }

    private fun onStackChanged() {
        if (fileStack.isNotEmpty()) {
            requestFileData(fileStack.peek())
        }
    }
}