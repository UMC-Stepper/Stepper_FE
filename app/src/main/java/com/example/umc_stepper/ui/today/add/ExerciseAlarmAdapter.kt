package com.example.umc_stepper.ui.today.add

import android.content.Context
import android.util.Log
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseAdapter
import com.example.umc_stepper.base.BaseDiffCallback
import com.example.umc_stepper.databinding.ItemExerciseCardScheduleBinding
import com.example.umc_stepper.domain.model.local.ExerciseAlarm
import com.example.umc_stepper.utils.alarm.ScheduledWorker

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
            if (isChecked) {
                scheduleAlarm(binding.root.context, item)
            } else {
                cancelAlarm(binding.root.context, item)
            }
        }
    }

    private fun scheduleAlarm(context: Context, alarm: ExerciseAlarm) {

        // WorkManager를 사용하여 ScheduledWorker를 예약
        val data = workDataOf(
            "alarmId" to alarm.hashCode(),
            "day" to alarm.day,
            "time" to alarm.time,
            "amPm" to alarm.amPm,
            "materials" to alarm.materials
        )

        val workRequest = OneTimeWorkRequestBuilder<ScheduledWorker>()
            .setInputData(data)
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            "Alarm_${alarm.hashCode()}",
            ExistingWorkPolicy.REPLACE,
            workRequest
        )
    }

    private fun cancelAlarm(context: Context, alarm: ExerciseAlarm) {
        // 알람 취소 로직
        WorkManager.getInstance(context).cancelUniqueWork("Alarm_${alarm.hashCode()}")
    }

}