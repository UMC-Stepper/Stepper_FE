package com.example.umc_stepper.ui.community.weekly

import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseAdapter
import com.example.umc_stepper.base.BaseDiffCallback
import com.example.umc_stepper.databinding.ItemWeeklyPostImgBinding

class WeeklyShowPostImageAdapter : BaseAdapter<String, ItemWeeklyPostImgBinding>(
    diffCallback = BaseDiffCallback(
        itemsTheSame = { oldItem, newItem -> oldItem == newItem },
        contentsTheSame = { oldItem, newItem -> oldItem == newItem }
    )
) {

    override val layoutId: Int
        get() = R.id.item_weekly_post_img_iv

    override fun bind(binding: ItemWeeklyPostImgBinding, item: String) {
        binding.imageUri = item
    }
}