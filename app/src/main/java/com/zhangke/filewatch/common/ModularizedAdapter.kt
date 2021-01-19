package com.zhangke.filewatch.common

import android.content.Context
import android.util.SparseIntArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.util.set
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.zhangke.filewatch.BR

open class ModularizedAdapter(context: Context) : RecyclerView.Adapter<ModularizedAdapter.ModularizedViewHolder>() {
    val data: List<ModularizedVm> = mutableListOf() // Workaround: effectively mutable for [modifyData].
    private val layoutInflater = LayoutInflater.from(context)

    fun replaceData(newData: List<ModularizedVm>) {
        val diff = DiffUtil.calculateDiff(DiffCallback(data, newData))
        modifyData(false) {
            it.clear()
            it.addAll(newData)
        }
        diff.dispatchUpdatesTo(this)
    }

    inline fun modifyData(needAutoNotify: Boolean = true, block: (MutableList<ModularizedVm>) -> Unit) {
        block(data as MutableList<ModularizedVm>)
        if (needAutoNotify) {
            notifyDataSetChanged()
        }
    }

    private val viewTypeToVariableId = SparseIntArray()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModularizedViewHolder {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, viewType, parent, false)
        genItemLayoutParams()?.let { itemLayoutAttr ->
            if (itemLayoutAttr is RecyclerView.LayoutParams) {
                itemLayoutAttr
            } else {
                RecyclerView.LayoutParams(itemLayoutAttr)
            }
        }?.also {
            binding.root.layoutParams = it
        }
        return ModularizedViewHolder(binding, viewTypeToVariableId[viewType])
    }

    override fun onBindViewHolder(holder: ModularizedViewHolder, position: Int) {
        val item = data[position]
        holder.viewDataBinding.setVariable(item.variableId, item)
        holder.viewDataBinding.executePendingBindings()
    }

    override fun onViewRecycled(holder: ModularizedViewHolder) {
        holder.viewDataBinding.setVariable(holder.variableId, null)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        val item = data[position]
        viewTypeToVariableId[item.layoutId] = item.variableId
        return item.layoutId
    }

    protected open fun genItemLayoutParams(): ViewGroup.LayoutParams? {
        return null
    }

    class ModularizedViewHolder(val viewDataBinding: ViewDataBinding, val variableId: Int) :
        RecyclerView.ViewHolder(viewDataBinding.root)
}

interface ModularizedVm {
    val layoutId: Int
    val variableId: Int
        get() = BR.vm
}

class SimpleObjectVm<T>(val data: T, override val layoutId: Int, override val variableId: Int = BR.vm) : ModularizedVm

@BindingAdapter("modularizedItems")
fun RecyclerView.modularizedItems(items: List<ModularizedVm>?) {
    var adapter = this.adapter as? ModularizedAdapter
    if (adapter == null) {
        adapter = ModularizedAdapter(context)
        this.adapter = adapter
    }
    adapter.replaceData(items ?: emptyList())
}
