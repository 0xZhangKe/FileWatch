package com.zhangke.filewatch.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.zhangke.filewatch.R
import com.zhangke.filewatch.common.MenuItemFactory
import com.zhangke.filewatch.common.toastText
import com.zhangke.filewatch.databinding.ActivityFileListBinding
import com.zhangke.filewatch.utils.FileHelper
import com.zhangke.filewatch.utils.neverDispose
import com.zhangke.filewatch.vm.FileListVm
import java.io.File

/**
 * Created by ZhangKe on 2021/1/19.
 */
class FileListActivity : AppCompatActivity() {

    private val vm: FileListVm by lazy {
        ViewModelProvider(this).get(FileListVm::class.java)
    }

    private var binding: ActivityFileListBinding? = null

    private var filePath: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initVm()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_file_list)
        binding?.lifecycleOwner = this
        binding?.vm = vm
        val filePath = intent?.getStringExtra(ARG_FILE_PATH)
        if (filePath.isNullOrEmpty() || !File(filePath).exists()) {
            toastText(R.string.file_list_page_no_arg)
            finish()
            return
        }
        this.filePath = filePath
        vm.init(filePath)
    }

    private fun initVm() {
        vm.toolbarVm.title.value = getString(R.string.file_list_page)
        val refreshMenu = MenuItemFactory.newImageItem(
                this,
                getString(R.string.refresh),
                R.drawable.ic_baseline_refresh_24
        ) { forceRefreshFileRecord() }
        vm.toolbarVm.menuItems.value = listOf(refreshMenu)
    }

    private fun forceRefreshFileRecord() {
        val dialog = AlertDialog.Builder(this)
                .setTitle("计算中...")
                .setNegativeButton("取消") { dialog, _ ->
                    dialog.dismiss()
                }
                .setCancelable(false)
                .setMessage("...")
                .create()
        dialog.show()
        val helper = FileHelper(File(filePath!!)) {
            dialog.setMessage(it.absolutePath)
        }
        helper.refresh()
                .subscribe({
                    dialog.cancel()
                    Toast.makeText(this, "完成", Toast.LENGTH_SHORT).show()
                }, {
                    it.printStackTrace()
                }).neverDispose()
    }

    companion object {

        private const val ARG_FILE_PATH = "file_path"

        fun open(context: Context, path: String) {
            Intent(context, FileListActivity::class.java).apply {
                putExtra(ARG_FILE_PATH, path)
            }.also {
                context.startActivity(it)
            }
        }
    }
}