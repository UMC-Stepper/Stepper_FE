package com.example.umc_stepper.ui.today.my_exercise

import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseAdapter
import com.example.umc_stepper.base.BaseDiffCallback
import com.example.umc_stepper.databinding.ItemCollocateMyExerciseBinding
import com.example.umc_stepper.domain.model.Exercise2Data
import com.example.umc_stepper.domain.model.response.my_exercise_controller.CheckExerciseResponse
import com.example.umc_stepper.utils.listener.ItemClickListener

class MyExercise2Adapter(
    val listener: ItemClickListener
)  : BaseAdapter<CheckExerciseResponse, ItemCollocateMyExerciseBinding>(
    BaseDiffCallback(
        itemsTheSame = { oldItem, newItem -> oldItem.exerciseId == newItem.exerciseId },
        contentsTheSame = { oldItem, newItem -> oldItem == newItem }
    )
){

    override val layoutId: Int
        get() = R.layout.item_collocate_my_exercise

    override fun bind(binding: ItemCollocateMyExerciseBinding, item: CheckExerciseResponse) {
        binding.listItem = item
        binding.listener = listener
    }


}
