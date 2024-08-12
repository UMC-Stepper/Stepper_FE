package com.example.umc_stepper.ui.today.my_exercise

import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseAdapter
import com.example.umc_stepper.base.BaseDiffCallback
import com.example.umc_stepper.databinding.ItemTag2Binding
import com.example.umc_stepper.domain.model.ExerciseTagData
import com.example.umc_stepper.ui.today.TodayViewModel

class TageAdapter(
    private val viewModel: TodayViewModel
) : BaseAdapter<ExerciseTagData, ItemTag2Binding>(
    BaseDiffCallback(
        itemsTheSame = { oldItem, newItem -> oldItem.id == newItem.id },
        contentsTheSame = { oldItem, newItem -> oldItem == newItem }
    )
) {

    fun onClick(item: ExerciseTagData) {
        viewModel.getMyExercise(
            when(item.theme){
                "어깨, 팔" -> "어깨팔"
                "무릎, 다리" -> "무릎다리"
                else -> item.theme
            }
        )
        val newList = currentList.map {
            if (it.id == item.id) {
                viewModel.setBodyPart(it.theme)
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