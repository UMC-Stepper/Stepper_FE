package com.example.umc_stepper.ui.community.savedcontents.scrap

import android.util.Log
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseAdapter
import com.example.umc_stepper.base.BaseDiffCallback
import com.example.umc_stepper.databinding.ItemCommunityWeeklyHomePostBinding
import com.example.umc_stepper.domain.model.response.post_controller.CommunityMyCommentsResponseItem
import com.example.umc_stepper.utils.listener.ItemClickListener

class MyScrapAdapter(val listener: ItemClickListener) :
    BaseAdapter<CommunityMyCommentsResponseItem, ItemCommunityWeeklyHomePostBinding>(
        diffCallback = BaseDiffCallback(
            itemsTheSame = { oldItem, newItem -> oldItem.id == newItem.id },
            contentsTheSame = { oldItem, newItem -> oldItem == newItem }
        )
    ) {

    override val layoutId: Int
        get() = R.layout.item_community_weekly_home_post

    override fun bind(
        binding: ItemCommunityWeeklyHomePostBinding,
        item: CommunityMyCommentsResponseItem
    ) {
        binding.communityMyCommentsResponseItem = item
        binding.itemWeeklyHomeScrapsUpIv.setImageResource(R.drawable.ic_scrap_fill)
        binding.listener = listener
    }

}