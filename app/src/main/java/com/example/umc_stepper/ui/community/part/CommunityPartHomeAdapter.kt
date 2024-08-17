package com.example.umc_stepper.ui.community.part

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
        binding.root.setOnClickListener {
            itemClickListener.onClick(item)
        }
        loadImage(binding, item.imageUrl)
    }

    private fun loadImage(binding: ItemCommunityPartHomePostBinding, imageUrl: String?) {
        if (imageUrl != null) {
            GlobalApplication.loadCropRoundedSquareImage(
                binding.root.context,
                binding.itemWeeklyHomeDescIv,
                imageUrl, 12
            )
        }
    }
}

