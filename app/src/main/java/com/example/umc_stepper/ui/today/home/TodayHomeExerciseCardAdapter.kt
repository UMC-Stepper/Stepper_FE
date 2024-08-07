package com.example.umc_stepper.ui.today.home

import android.util.Log
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseAdapter
import com.example.umc_stepper.base.BaseDiffCallback
import com.example.umc_stepper.databinding.ItemTodayHomeExerciseCardBinding
import com.example.umc_stepper.domain.model.local.ExerciseState

class TodayHomeExerciseCardAdapter: BaseAdapter<ExerciseState, ItemTodayHomeExerciseCardBinding> (
    diffCallback = BaseDiffCallback(
        itemsTheSame = { oldItem, newItem -> oldItem.id == newItem.id },
        contentsTheSame = { oldItem, newItem -> oldItem == newItem }
    )
) {

    override val layoutId: Int get() = R.layout.item_today_home_exercise_card

    override fun bind(binding: ItemTodayHomeExerciseCardBinding, item: ExerciseState) {
        binding.exerciseState = item
    }

}