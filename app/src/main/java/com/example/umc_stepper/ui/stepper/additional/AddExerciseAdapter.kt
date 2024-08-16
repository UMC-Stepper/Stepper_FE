package com.example.umc_stepper.ui.stepper.additional

import androidx.recyclerview.widget.DiffUtil
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseAdapter
import com.example.umc_stepper.base.BaseDiffCallback
import com.example.umc_stepper.databinding.ItemSelectMyExerciseBinding
import com.example.umc_stepper.databinding.ItemStepperAdditionalExerciseBinding
import com.example.umc_stepper.domain.model.request.ExerciseDto
import com.example.umc_stepper.domain.model.response.more_exercise_controller.TimeResponse
import com.example.umc_stepper.domain.model.response.my_exercise_controller.CheckExerciseResponse


class AddExerciseAdapter(
) : BaseAdapter<TimeResponse, ItemStepperAdditionalExerciseBinding>(
    BaseDiffCallback(
        itemsTheSame = { oldItem, newItem -> oldItem == newItem },
        contentsTheSame = { oldItem, newItem -> oldItem == newItem }
    )
) {


    override val layoutId: Int
        get() = R.layout.item_stepper_additional_exercise

    override fun bind(binding: ItemStepperAdditionalExerciseBinding, item: TimeResponse) {
        binding.timeResponse = item
    }

}
