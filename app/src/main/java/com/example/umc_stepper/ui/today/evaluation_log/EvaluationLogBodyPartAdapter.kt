package com.example.umc_stepper.ui.today.evaluation_log

import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseAdapter
import com.example.umc_stepper.base.BaseDiffCallback
import com.example.umc_stepper.databinding.ItemEvaluationLogCalenderCategoryBinding
import com.example.umc_stepper.domain.model.response.exercise_card_controller.ExerciseCardStatusResponseDto

class EvaluationLogBodyPartAdapter(private val onItemClick: (ExerciseCardStatusResponseDto) -> Unit) : BaseAdapter<ExerciseCardStatusResponseDto, ItemEvaluationLogCalenderCategoryBinding>(
    diffCallback = BaseDiffCallback (
        itemsTheSame = { oldItem, newItem -> oldItem == newItem },
        contentsTheSame = { oldItem, newItem -> oldItem == newItem }
    )
) {

    override val layoutId: Int
        get() = R.layout.item_evaluation_log_calender_category

    override fun bind(binding: ItemEvaluationLogCalenderCategoryBinding, item: ExerciseCardStatusResponseDto) {
        binding.exerciseCardStatusResponseDto = item

        // 클릭 시 아이템 반환
        binding.root.setOnClickListener {
            onItemClick(item)
        }
    }

}