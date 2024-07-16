package com.example.umc_stepper.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T : Any, VB : ViewDataBinding>(
    diffCallback: BaseDiffCallback<T>
) : ListAdapter<T, BaseAdapter.BaseViewHolder<VB>>(diffCallback) {

    abstract val layoutId: Int

    abstract fun bind(binding: VB, item: T)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<VB> {
        val binding: VB = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            layoutId, parent, false
        )
        return BaseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<VB>, position: Int) {
        bind(holder.binding, getItem(position))
        holder.binding.executePendingBindings()
    }

    class BaseViewHolder<VB : ViewDataBinding>(val binding: VB) : RecyclerView.ViewHolder(binding.root)
}