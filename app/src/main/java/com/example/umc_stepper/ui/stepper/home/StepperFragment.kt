package com.example.umc_stepper.ui.stepper.home

import android.view.WindowManager
import androidx.navigation.fragment.findNavController
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentStepperBinding
import com.example.umc_stepper.ui.MainActivity
import com.example.umc_stepper.ui.stepper.additional.AdditionalExerciseHomeFragment

class StepperFragment : BaseFragment<FragmentStepperBinding>(R.layout.fragment_stepper) {
    override fun setLayout() {
        val days = listOf(
            DayData("월", false, false), DayData("화", false, false), DayData("수", false, false),
            DayData("목", false, false), DayData("금", false, false), DayData("토", false, false), DayData("일", false, false),
            DayData("29", false, false), DayData("30", false, false), DayData("31", false, false),
            DayData("1", false, false), DayData("2", false, false), DayData("3", false, false), DayData("4", false, false),
            DayData("5", false, false), DayData("6", false, false), DayData("7", false, false), DayData("8", false, false),
            DayData("9", true, false), DayData("10", false, false), DayData("11", false, false),
            DayData("12", false, false), DayData("13", false, false), DayData("14", false, false), DayData("15", false, false),
            DayData("16", false, true), DayData("17", false, false), DayData("18", false, false),
            DayData("19", false, false), DayData("20", false, false), DayData("21", false, false), DayData("22", false, false),
            DayData("23", false, false), DayData("24", false, false), DayData("25", false, false),
            DayData("26", true, false), DayData("27", false, false), DayData("28", false, false), DayData("29", false, false),
            DayData("30", false, false), DayData("31", false, false), DayData("1", false, false)
        )

        val adapter = CalendarAdapter(requireContext(),days)
        binding.stepperCalendarGv.adapter = adapter

        binding.stepperAdditionalBtn.setOnClickListener {
            goAdditionalExerciseHome()
        }

        //테스트를 위한 임시함수 적용(8월을 누르면 평가화면으로)
        binding.stepperMonthTitleTv.setOnClickListener {
            goEvaluationExerciseToday()
        }

    }
    private fun goAdditionalExerciseHome(){
        findNavController().navigate(R.id.action_stepperFragment_to_additionalExerciseHomeFragment)
    }

    //테스트를 위한 임시함수 적용
    private fun goEvaluationExerciseToday(){
        findNavController().navigate(R.id.action_stepperFragment_to_fragmentEvaluationExerciseToday)
    }
}