package com.example.umc_stepper.ui.community.part

import androidx.core.content.ContextCompat
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseAdapter
import com.example.umc_stepper.base.BaseDiffCallback
import com.example.umc_stepper.databinding.ItemCommunityPartHomePostBinding
import com.example.umc_stepper.databinding.ItemCommunitySearchBinding
import com.example.umc_stepper.domain.model.PostItem
import com.example.umc_stepper.domain.model.response.post_controller.ApiResponseListPostViewResponseItem
import com.example.umc_stepper.domain.model.response.post_controller.CommunityMyCommentsResponseItem
import com.example.umc_stepper.utils.GlobalApplication
import com.example.umc_stepper.utils.listener.ItemClickListener

class CommunityPartHomeAdapter(
    private val itemClickListener: ItemClickListener
) : BaseAdapter<ApiResponseListPostViewResponseItem, ItemCommunityPartHomePostBinding>(
    BaseDiffCallback(
        itemsTheSame = { oldItem, newItem -> oldItem.id == newItem.id }, // 아이템의 고유 ID로 비교
        contentsTheSame = { oldItem, newItem -> oldItem == newItem } // 전체 내용 비교
    )
) {
    override val layoutId: Int
        get() = R.layout.item_community_part_home_post

    override fun bind(binding: ItemCommunityPartHomePostBinding, item: ApiResponseListPostViewResponseItem) {
        binding.partPostItem = item
        binding.listener = itemClickListener
        if (item.imageList.isNotEmpty()) {
            GlobalApplication.loadImage(binding.itemWeeklyHomeDescIv, item.imageList[0].imageUrl)
        } else {
            binding.itemWeeklyHomeDescIv.setImageDrawable(
                ContextCompat.getDrawable(binding.root.context, R.drawable.ic_community_logo)
            )
        }
    }
}

