package com.example.umc_stepper.ui.stepper

import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentTodayExerciseSuccessBinding

class TodayExerciseSuccessFragment :
    BaseFragment<FragmentTodayExerciseSuccessBinding>(R.layout.fragment_today_exercise_success) {
    override fun setLayout() {
        setTitle()
        setOnClickBtn()
    }

    private fun setTitle() {
        with(binding) {
            fragmentTodayExerciseSuccessTitleTv.text = "오늘 이만큼 운동했어요!"
            fragmentTodayExerciseSuccessHourTv.text
            fragmentTodayExerciseSuccessHourTv
            fragmentTodayExerciseSuccessMinuteTv
            fragmentTodayExerciseSuccessSecondTv
        }
    }

    private fun setOnClickBtn(){
        binding.fragmentTodayExerciseSuccessOkBtn.setOnClickListener{
            //다음화면
        }
    }
}