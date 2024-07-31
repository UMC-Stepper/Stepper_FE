package com.example.umc_stepper.ui.today.add

import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentExerciseCardLastBinding
import com.example.umc_stepper.domain.model.local.ExerciseAlarm

class ExerciseCardLastFragment:BaseFragment<FragmentExerciseCardLastBinding>(R.layout.fragment_exercise_card_last) {

    private lateinit var exerciseAlarmAdapter: ExerciseAlarmAdapter

    override fun setLayout() {
        exerciseAlarmAdapter = ExerciseAlarmAdapter()
        binding.fragmentExerciseCardLastScheduleRv.adapter = exerciseAlarmAdapter

        val testItem = ExerciseAlarm(
            day = "매주 화요일, 목요일",
            time = "4:00",
            amPm = "오후",
            materials = "운동밴드"
        )
        val testList = listOf(testItem)
        exerciseAlarmAdapter.submitList(testList)
    }

    private fun setAdapter() {

    }

}