package com.example.umc_stepper.ui.community.weekly

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_stepper.databinding.ItemCommunityShowPostCommentBinding
import com.example.umc_stepper.databinding.ItemCommunityShowPostCommentReplyBinding
import com.example.umc_stepper.domain.model.response.comment_controller.CommentResponseItem
import com.example.umc_stepper.domain.model.response.comment_controller.ReplyResponse
import java.text.SimpleDateFormat
import java.util.Locale

class WeeklyShowPostReplyAdapter(private val onItemClick: (String) -> Unit) : ListAdapter<Any, RecyclerView.ViewHolder>(diffUtil) {

    inner class CommentViewHolder(private val binding: ItemCommunityShowPostCommentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : CommentResponseItem) {
            binding.apply {
                binding.commentResponseItem = item

                val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
                val outputFormat = SimpleDateFormat("yy.MM.dd  HH:mm", Locale.getDefault())
                val date = item.dateTime.let { inputFormat.parse(it) }
                binding.itemWeeklyShowPostCommentTimeTv.text = date?.let { outputFormat.format(it) }

                binding.fragmentCommunityWeeklyShowReplyUpIv.setOnClickListener {
                    onItemClick(item.commentId.toString())
                }


            }
        }
    }

    inner class ReplyViewHolder(private val binding: ItemCommunityShowPostCommentReplyBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : ReplyResponse) {
            binding.apply {
                binding.replyResponse  = item
                val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
                val outputFormat = SimpleDateFormat("yy.MM.dd  HH:mm", Locale.getDefault())
                val date = item.localDateTime.let { inputFormat.parse(it) }
                binding.itemWeeklyShowPostCommentReplyTimeTv.text = date?.let { outputFormat.format(it) }
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

    // 뷰 타입에 따라 홀더에 데이터 바인딩
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = getItem(position)
        when (holder) {
            is CommentViewHolder -> holder.bind(currentItem as CommentResponseItem)
            is ReplyViewHolder -> holder.bind(currentItem as ReplyResponse)
        }
    }

    // 아이템 ViewType 결정
    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is CommentResponseItem -> COMMENT
            is ReplyResponse -> REPLY
            else -> throw IllegalArgumentException("Unknown item type")
        }
    }

    companion object{
        const val COMMENT = 0
        const val REPLY = 1

        val diffUtil = object : DiffUtil.ItemCallback<Any>() {
            override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
                return if (oldItem is CommentResponseItem && newItem is CommentResponseItem) {
                    oldItem.commentId == newItem.commentId
                } else if (oldItem is ReplyResponse && newItem is ReplyResponse) {
                    oldItem.parentCommentId == newItem.parentCommentId && oldItem.content == newItem.content
                } else {
                    false
                }
            }

            override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
                return when {
                    oldItem is CommentResponseItem && newItem is CommentResponseItem -> oldItem == newItem
                    oldItem is ReplyResponse && newItem is ReplyResponse -> oldItem == newItem
                    else -> false
                }
            }
        }
    }

}
