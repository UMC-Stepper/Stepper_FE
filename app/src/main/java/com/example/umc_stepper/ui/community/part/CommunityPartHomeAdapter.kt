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

class CommunityPartHomeAdapter(private val itemClickListener: ItemClickListener) : BaseAdapter<ApiResponseListPostViewResponseItem, ItemCommunityPartHomePostBinding> (
    BaseDiffCallback(
    itemsTheSame = { oldItem, newItem -> oldItem == newItem},
    contentsTheSame = { oldItem, newItem -> oldItem == newItem }
    )
){
    override val layoutId: Int
        get() = R.layout.item_community_part_home_post

    override fun bind(binding: ItemCommunityPartHomePostBinding, item: ApiResponseListPostViewResponseItem) {
        binding.partPostItem = item
        binding.root.setOnClickListener {
            itemClickListener.onClick(item)
        }
        GlobalApplication.loadCropRoundedSquareImage(binding.root.context,binding.itemWeeklyHomeDescIv,item.imageUrl,12)
    }
}

