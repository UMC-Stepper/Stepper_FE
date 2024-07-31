package com.example.umc_stepper.ui.today.add

import android.content.Context
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
        setAdapter()
    }

    private fun setAdapter() {
        exerciseAlarmAdapter = ExerciseAlarmAdapter()
        binding.fragmentExerciseCardLastScheduleRv.adapter = exerciseAlarmAdapter

        val testItem = ExerciseAlarm(
            day = "수요일, 목요일",
            time = "12:17",
            amPm = "오후",
            materials = "운동밴드",
            true
        )
        val testItem2 = ExerciseAlarm(
            day = "월요일, 금요일",
            time = "6:00",
            amPm = "오후",
            materials = "줄넘기",
            true
        )

        val testList = listOf(testItem, testItem2)
        exerciseAlarmAdapter.submitList(testList)
        saveAlarms(testList)
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