package com.example.umc_stepper.ui.stepper

import android.content.Context
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentTodayExerciseSuccessBinding
import com.example.umc_stepper.ui.MainActivity

class TodayAddExerciseSuccessFragment :
    BaseFragment<FragmentTodayExerciseSuccessBinding>(R.layout.fragment_today_exercise_success) {
    private lateinit var mainActivity : MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
    

    override fun setLayout() {
        setTitle()
        setOnClickBtn()
    }


    private fun setTitle() {
        with(binding) {
            binding.fragmentTodayExerciseSuccessTitleTv.text = "오늘 이만큼 추가 운동했어요!"
            fragmentTodayExerciseSuccessHourTv.text
            fragmentTodayExerciseSuccessHourTv
            fragmentTodayExerciseSuccessMinuteTv
            fragmentTodayExerciseSuccessSecondTv
        }
        mainActivity.updateToolbarTitle("운동 완료")
    }



    private fun setOnClickBtn() {
        binding.fragmentTodayExerciseSuccessOkBtn.setOnClickListener {
            //다음화면
        }

    }
}