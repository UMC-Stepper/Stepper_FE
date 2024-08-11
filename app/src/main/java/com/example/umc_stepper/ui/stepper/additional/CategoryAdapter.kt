package com.example.umc_stepper.ui.stepper.additional

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_stepper.R
import com.example.umc_stepper.databinding.ItemTagBinding

class CategoryAdapter(
    private val context: Context, // 컨텍스트를 추가로 받음
    private val listener: OnCategoryClickListener
) : ListAdapter<String, CategoryAdapter.CategoryViewHolder>(CategoryDiffCallback()) {

    private var selectedPosition: Int = RecyclerView.NO_POSITION // 선택된 아이템의 인덱스를 저장

    interface OnCategoryClickListener {
        fun onCategoryClick(category: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemTagBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    inner class CategoryViewHolder(private val binding: ItemTagBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String, position: Int) {
            binding.tagTv.text = item

            // 선택된 아이템인지 확인하여 스타일 적용
            if (position == selectedPosition) {
                binding.tagTv.setTextColor(ContextCompat.getColor(context, R.color.Purple_Black_BG_2))
                binding.tagTv.setBackgroundResource(R.drawable.shape_rounded_square_yellow700_40dp)
            } else {
                binding.tagTv.setTextColor(ContextCompat.getColor(context, R.color.Yellow_700))
                binding.tagTv.setBackgroundResource(R.drawable.selector_tag)
            }

            binding.root.setOnClickListener {
                // 선택된 아이템의 인덱스 갱신
                val previousSelectedPosition = selectedPosition
                selectedPosition = position

                // 선택된 아이템과 이전에 선택된 아이템을 업데이트
                notifyItemChanged(previousSelectedPosition)
                notifyItemChanged(selectedPosition)

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
