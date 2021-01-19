package com.zhangke.filewatch.common

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

open class DiffCallback<out T, out C : List<T>>(private val oldDatas: C, private val newDatas: C) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldDatas.size

    override fun getNewListSize(): Int = newDatas.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldDatas[oldItemPosition] === newDatas[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldDatas[oldItemPosition] == newDatas[newItemPosition]
}

/**
 * 通知[RecyclerView.Adapter]分发局部更新
 */
@JvmOverloads
fun <T, C : List<T>, VH : RecyclerView.ViewHolder> RecyclerView.Adapter<VH>.dispatchUpdates(
    oldDatas: C,
    newDatas: C,
    callback: DiffCallback<T, C> = DiffCallback(oldDatas, newDatas),
    detectMoves: Boolean = true
): C {
    val diffResult = DiffUtil.calculateDiff(callback, detectMoves)
    diffResult.dispatchUpdatesTo(this@dispatchUpdates)
    return newDatas
}
