package com.example.umc_stepper.ui.community.weekly

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_stepper.databinding.ItemCommunityShowPostCommentBinding
import com.example.umc_stepper.databinding.ItemCommunityShowPostCommentReplyBinding
import com.example.umc_stepper.domain.model.response.comment_controller.CommentResponseItem

class WeeklyShowPostReplyAdapter : ListAdapter<CommentResponseItem, RecyclerView.ViewHolder>(diffUtil) {

    inner class CommentViewHolder(private val binding: ItemCommunityShowPostCommentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : CommentResponseItem) {
            binding.apply {
                binding.commentResponseItem = item
            }
        }
    }

    inner class ReplyViewHolder(private val binding: ItemCommunityShowPostCommentReplyBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : CommentResponseItem) {
            binding.apply {
                binding.commentResponseItem = item
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            COMMENT -> {
                val binding = ItemCommunityShowPostCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                CommentViewHolder(binding)
            }
            REPLY -> {
                val binding = ItemCommunityShowPostCommentReplyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ReplyViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = getItem(position)
        when (holder) {
            is CommentViewHolder -> holder.bind(currentItem)
            is ReplyViewHolder -> holder.bind(currentItem)
        }
    }

    override fun getItemViewType(position: Int): Int {
        // 임의의 로직을 통해 COMMENT 또는 REPLY를 반환
        return if (position % 2 == 0) COMMENT else REPLY
    }

    companion object{
        const val COMMENT = 0
        const val REPLY = 1

        val diffUtil = object : DiffUtil.ItemCallback<CommentResponseItem>() {
            override fun areItemsTheSame(oldItem: CommentResponseItem, newItem: CommentResponseItem): Boolean {
                return oldItem.commentId == newItem.commentId
            }

            override fun areContentsTheSame(oldItem: CommentResponseItem, newItem: CommentResponseItem): Boolean {
                return oldItem == newItem
            }
        }
    }

}
