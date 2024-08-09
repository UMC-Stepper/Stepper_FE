package com.example.umc_stepper.ui.today.add

import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseAdapter
import com.example.umc_stepper.base.BaseDiffCallback
import com.example.umc_stepper.databinding.ItemSelectMyExerciseBinding
import com.example.umc_stepper.domain.model.response.my_exercise_controller.CheckExerciseResponse

class SelectScrapListAdapter : BaseAdapter<CheckExerciseResponse, ItemSelectMyExerciseBinding>(
    diffCallback = BaseDiffCallback(
        itemsTheSame = { oldItem, newItem -> oldItem.exerciseId == newItem.exerciseId },
        contentsTheSame = { oldItem, newItem -> oldItem == newItem }
    )
) {

    override val layoutId: Int
        get() = R.layout.item_select_my_exercise

    override fun bind(binding: ItemSelectMyExerciseBinding, item: CheckExerciseResponse) {
        binding.checkExerciseResponse = item
    }

}