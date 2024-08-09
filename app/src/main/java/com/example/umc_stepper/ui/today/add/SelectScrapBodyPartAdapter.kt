package com.example.umc_stepper.ui.today.add

import android.util.Log
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseAdapter
import com.example.umc_stepper.base.BaseDiffCallback
import com.example.umc_stepper.databinding.ItemTagBinding

class SelectScrapBodyPartAdapter : BaseAdapter<String, ItemTagBinding>(
    diffCallback = BaseDiffCallback(
        itemsTheSame = { oldItem, newItem -> oldItem == newItem },
        contentsTheSame = { oldItem, newItem -> oldItem == newItem }
    )
) {

    override val layoutId: Int
        get() = R.layout.item_tag

    override fun bind(binding: ItemTagBinding, item: String) {
        Log.d("SelectScrapBodyPartAdapter", "item : $item, ")
        binding.tagTv.text = item

    }
}