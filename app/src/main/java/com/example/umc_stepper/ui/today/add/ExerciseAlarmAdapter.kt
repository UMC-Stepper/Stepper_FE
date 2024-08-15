package com.example.umc_stepper.ui.today.add

import android.util.Log
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseAdapter
import com.example.umc_stepper.base.BaseDiffCallback
import com.example.umc_stepper.databinding.ItemExerciseCardScheduleBinding
import com.example.umc_stepper.domain.model.local.ExerciseAlarm

class ExerciseAlarmAdapter: BaseAdapter<ExerciseAlarm, ItemExerciseCardScheduleBinding>(
    diffCallback = BaseDiffCallback(
        itemsTheSame = {oldItem: ExerciseAlarm, newItem: ExerciseAlarm -> oldItem == newItem },
        contentsTheSame = {oldItem: ExerciseAlarm, newItem: ExerciseAlarm -> oldItem == newItem  }
    )
) {

    override val layoutId: Int
        get() = R.layout.item_exercise_card_schedule

    override fun bind(binding: ItemExerciseCardScheduleBinding, item: ExerciseAlarm) {
        Log.d("로그", "item : $item")
        binding.exerciseAlarm = item
        binding.itemExerciseCardSwitch.isChecked = item.isEnabled

        binding.itemExerciseCardSwitch.setOnCheckedChangeListener { _, isChecked ->
            item.isEnabled = isChecked
        }
    }

}