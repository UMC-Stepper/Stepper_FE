package com.example.umc_stepper.ui.community

import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseAdapter
import com.example.umc_stepper.base.BaseDiffCallback
import com.example.umc_stepper.databinding.ItemCommunitySearchBinding
import com.example.umc_stepper.domain.model.PostItem
import com.example.umc_stepper.utils.GlobalApplication

class CommunitySearchAdapter : BaseAdapter<PostItem, ItemCommunitySearchBinding> (
    BaseDiffCallback(
    itemsTheSame = { oldItem, newItem -> oldItem == newItem},
    contentsTheSame = { oldItem, newItem -> oldItem == newItem }
    )
){
    override val layoutId: Int
        get() = R.layout.item_community_search

    override fun bind(binding: ItemCommunitySearchBinding, item: PostItem) {
        binding.postItem = item
        GlobalApplication.loadCropRoundedSquareImage(binding.root.context,binding.itemCommunitySearchProfileIv,item.img,12)
        binding.itemCommunitySearchProfileIv
    }
}

