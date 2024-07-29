package com.example.umc_stepper.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T, VB : ViewDataBinding>(
    diffCallback: DiffUtil.ItemCallback<T>
) : ListAdapter<T, BaseAdapter.BaseViewHolder<VB>>(diffCallback) {

    abstract val layoutId: Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<VB> {
        val binding: VB = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), layoutId, parent, false
        )
        return BaseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<VB>, position: Int) {
        holder.bind(getItem(position), this)
    }

    abstract fun bind(binding: VB, item: T)

    class BaseViewHolder<VB : ViewDataBinding>(private val binding: VB) :
        RecyclerView.ViewHolder(binding.root) {
        fun <T> bind(item: T, adapter: BaseAdapter<T, VB>) {
            adapter.bind(binding, item)
            binding.executePendingBindings()
        }
    }
}
