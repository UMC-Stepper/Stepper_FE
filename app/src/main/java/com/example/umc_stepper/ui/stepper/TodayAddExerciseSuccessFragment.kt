package com.example.umc_stepper.ui.stepper

import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentTodayExerciseSuccessBinding

class TodayAddExerciseSuccessFragment :
    BaseFragment<FragmentTodayExerciseSuccessBinding>(R.layout.fragment_today_exercise_success) {
    override fun setLayout() {
        setTitle()
        setOnClickBtn()
    }

    fun setTitle() {
        with(binding) {
            binding.fragmentTodayExerciseSuccessTitleTv.text = "오늘 이만큼 추가 운동했어요!"
            fragmentTodayExerciseSuccessHourTv.text
            fragmentTodayExerciseSuccessHourTv
            fragmentTodayExerciseSuccessMinuteTv
            fragmentTodayExerciseSuccessSecondTv
        }
    }


    private fun setOnClickBtn() {
        binding.fragmentTodayExerciseSuccessOkBtn.setOnClickListener {
            //다음화면
        }

    }
}