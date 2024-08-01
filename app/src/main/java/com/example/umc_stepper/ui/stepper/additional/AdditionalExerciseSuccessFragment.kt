package com.example.umc_stepper.ui.stepper.additional

import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentTodayExerciseSuccessBinding

class AdditionalExerciseSuccessFragment : BaseFragment<FragmentTodayExerciseSuccessBinding>(R.layout.fragment_today_exercise_success) {
    override fun setLayout() {
        setTitle()
        setOnClickBtn()
    }

    private fun setTitle() {
        with(binding) {
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