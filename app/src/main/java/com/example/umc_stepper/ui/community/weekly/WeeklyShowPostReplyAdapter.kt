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
import com.example.umc_stepper.utils.listener.ItemClickListener
import java.text.SimpleDateFormat
import java.util.Locale

class WeeklyShowPostReplyAdapter(val listener: ItemClickListener) : ListAdapter<CommentResponseItem, RecyclerView.ViewHolder>(diffUtil) {

    inner class CommentViewHolder(private val binding: ItemCommunityShowPostCommentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : CommentResponseItem) {
            binding.apply {
                Log.d("WeeklyShowPostReplyAdapter Item", "item: $item")
                binding.commentResponseItem = item
                binding.listener = listener

                val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
                val outputFormat = SimpleDateFormat("yy.MM.dd  HH:mm", Locale.getDefault())

                // 날짜 파싱 및 변환
                val date = item.dateTime.let { inputFormat.parse(it) }
                binding.itemWeeklyShowPostCommentTimeTv.text = date?.let { outputFormat.format(it) }
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

    // 뷰 타입에 따라 홀더에 데이터 바인딩
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = getItem(position)
        when (holder) {
            is CommentViewHolder -> holder.bind(currentItem)
            is ReplyViewHolder -> holder.bind(currentItem)
        }
    }

    // 아이템 ViewType 결정
    override fun getItemViewType(position: Int): Int {
        // 임의의 로직을 통해 COMMENT 또는 REPLY를 반환
        return COMMENT
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
