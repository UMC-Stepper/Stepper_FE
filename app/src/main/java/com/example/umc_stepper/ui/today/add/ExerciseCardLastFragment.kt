package com.example.umc_stepper.ui.today.add

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.navigation.fragment.findNavController
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentExerciseCardLastBinding
import com.example.umc_stepper.domain.model.local.ExerciseAlarm
import com.example.umc_stepper.utils.alarm.ScheduledWorker
import com.google.gson.Gson
import java.util.concurrent.TimeUnit


class ExerciseCardLastFragment:BaseFragment<FragmentExerciseCardLastBinding>(R.layout.fragment_exercise_card_last) {

    private lateinit var exerciseAlarmAdapter: ExerciseAlarmAdapter

    override fun setLayout() {
        setButton()
        setAdapter()
    }

    private fun setButton() {
        binding.fragmentExerciseCardLastCompleteBtn.setOnClickListener {
            val action = ExerciseCardLastFragmentDirections.actionExerciseCardLastFragmentToExerciseCompleteFragment()
            findNavController().navigateSafe(action.actionId, Bundle().apply {
                putString("type","운동 설정 완료!")
                putString("description","더 나은 단계로 나아갈 수 있게 STEPPER가\n함께 할게요")
            })
        }
    }

    private fun setAdapter() {
        exerciseAlarmAdapter = ExerciseAlarmAdapter()
        binding.fragmentExerciseCardLastScheduleRv.adapter = exerciseAlarmAdapter

        val args = arguments ?: Bundle()
        val week = args.getString("week")
        val hourTime = args.getInt("hourTime")
        val minuteTime = args.getInt("minuteTime")
        val material = args.getString("material")
        val ampm = args.getString("ampm")


        // 시간 형식화
        val (alarmHour, alarmAmPm) = when (ampm) {
            "PM" -> hourTime + 12 to "오후"
            else -> hourTime to "오전"
        }

        val alarmTime = String.format("%02d:%02d", alarmHour, minuteTime)

        val testItem = week?.let {
            ExerciseAlarm(
                day = it,
                time = alarmTime,
                amPm = alarmAmPm,
                materials = material,
                true
            )
        }

        val testList = listOf(testItem)
        exerciseAlarmAdapter.submitList(testList)
    }

    private fun saveAlarms(alarms: List<ExerciseAlarm>) {
        val sharedPreferences = requireContext().getSharedPreferences("ExerciseAlarms", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(alarms)
        editor.putString("alarms", json)
        editor.apply()

        scheduleWorker()
    }

    private fun scheduleWorker() {
        val workRequest = PeriodicWorkRequestBuilder<ScheduledWorker>(15, TimeUnit.MINUTES)
            .build()
        WorkManager.getInstance(requireContext()).enqueueUniquePeriodicWork(
            "worker",
            ExistingPeriodicWorkPolicy.REPLACE,
            workRequest
        )
    }

}