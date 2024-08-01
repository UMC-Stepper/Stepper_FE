package com.example.umc_stepper.ui.today

import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseAdapter
import com.example.umc_stepper.base.BaseDiffCallback
import com.example.umc_stepper.databinding.ItemTag2Binding
import com.example.umc_stepper.databinding.ItemTagBinding
import com.example.umc_stepper.domain.model.ExerciseTagData

class TageAdapter(
) : BaseAdapter<ExerciseTagData, ItemTag2Binding>(
    BaseDiffCallback(
        itemsTheSame = { oldItem, newItem -> oldItem.id == newItem.id },
        contentsTheSame = { oldItem, newItem -> oldItem == newItem }
    )
) {

    fun onClick(item: ExerciseTagData) {
        val newList = currentList.map {
            if (it.id == item.id) {
                it.copy(
                    backGroundColor = R.drawable.shape_rounded_square_yellow700_40dp,
                    textColor = R.color.Purple_Black_BG_2
                )
            } else {
                it.copy(
                    backGroundColor = R.drawable.selector_exercise_card_tag,
                    textColor = R.color.Yellow_700
                )
            }
        }
        submitList(newList)
    }

    override val layoutId: Int
        get() = R.layout.item_tag2

    override fun bind(binding: ItemTag2Binding, item: ExerciseTagData) {
        binding.tagData = item
        binding.func = this
    }


}