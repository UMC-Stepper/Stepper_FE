package com.example.umc_stepper.ui.community.weekly

import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseAdapter
import com.example.umc_stepper.base.BaseDiffCallback
import com.example.umc_stepper.databinding.ItemWeeklyPostImgBinding
import com.example.umc_stepper.domain.model.response.ImageResponse

class WeeklyShowPostImageAdapter : BaseAdapter<ImageResponse, ItemWeeklyPostImgBinding>(
    diffCallback = BaseDiffCallback(
        itemsTheSame = { oldItem, newItem -> oldItem.id == newItem.id },
        contentsTheSame = { oldItem, newItem -> oldItem == newItem }
    )
) {

    override val layoutId: Int
        get() = R.layout.item_weekly_post_img

    override fun bind(binding: ItemWeeklyPostImgBinding, item: ImageResponse) {
        binding.imageResponse = item
    }
}