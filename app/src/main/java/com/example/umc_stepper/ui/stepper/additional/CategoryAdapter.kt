package com.example.umc_stepper.ui.stepper.additional

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_stepper.databinding.ItemTagBinding

class CategoryAdapter(
    private val listener: OnCategoryClickListener
) : ListAdapter<String, CategoryAdapter.CategoryViewHolder>(CategoryDiffCallback()) {

    interface OnCategoryClickListener {
        fun onCategoryClick(category: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemTagBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CategoryViewHolder(private val binding: ItemTagBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.tagTv.text = item
            binding.root.setOnClickListener {
                listener.onCategoryClick(item)
            }
        }
    }

    class CategoryDiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}