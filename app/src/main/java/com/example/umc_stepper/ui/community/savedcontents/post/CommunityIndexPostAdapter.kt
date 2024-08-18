package com.example.umc_stepper.ui.community.savedcontents.post

import android.util.Log
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseAdapter
import com.example.umc_stepper.base.BaseDiffCallback
import com.example.umc_stepper.databinding.ItemCommunityIndexPostBinding
import com.example.umc_stepper.databinding.ItemCommunityPartHomePostBinding
import com.example.umc_stepper.databinding.ItemCommunityWeeklyHomePostBinding
import com.example.umc_stepper.domain.model.response.post_controller.CommunityMyPostsResponseItem
import com.example.umc_stepper.utils.GlobalApplication
import com.example.umc_stepper.utils.listener.ItemClickListener

class CommunityIndexPostAdapter(private val itemClickListener: ItemClickListener) :
    BaseAdapter<CommunityMyPostsResponseItem, ItemCommunityIndexPostBinding>(
        diffCallback = BaseDiffCallback(
            itemsTheSame = { oldItem, newItem -> oldItem.id == newItem.id },
            contentsTheSame = { oldItem, newItem -> oldItem == newItem }
        )
    ) {

    override val layoutId: Int
        get() = R.layout.item_community_index_post

    override fun bind(
        binding: ItemCommunityIndexPostBinding,
        item: CommunityMyPostsResponseItem
    ) {
        binding.indexPostItem = item
        binding.root.setOnClickListener {
            itemClickListener.onClick(item)
        }
        loadImage(binding, item.imageUrl)
    }

    private fun loadImage(binding: ItemCommunityIndexPostBinding, imageUrl: String?) {
        if (imageUrl != null) {
            GlobalApplication.loadCropRoundedSquareImage(
                binding.root.context,
                binding.itemWeeklyHomeDescIv,
                imageUrl, 12
            )
        }
    }

}