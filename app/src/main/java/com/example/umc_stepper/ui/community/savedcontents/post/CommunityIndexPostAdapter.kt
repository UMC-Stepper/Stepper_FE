package com.example.umc_stepper.ui.community.savedcontents.post

import android.util.Log
import androidx.core.content.ContextCompat
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
        if (item.imageList.isNotEmpty()) {
            GlobalApplication.loadImage(binding.itemWeeklyHomeDescIv, item.imageList[0].imageUrl)
        } else {
            binding.itemWeeklyHomeDescIv.setImageDrawable(
                ContextCompat.getDrawable(binding.root.context, R.drawable.ic_community_logo)
            )
        }
    }



}