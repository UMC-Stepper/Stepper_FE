package com.example.umc_stepper.ui.stepper.additional

import android.content.Context
import androidx.navigation.fragment.findNavController
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentTodayExerciseSuccessBinding
import com.example.umc_stepper.ui.MainActivity

class AdditionalExerciseSuccessFragment : BaseFragment<FragmentTodayExerciseSuccessBinding>(R.layout.fragment_today_exercise_success) {

    private lateinit var mainActivity : MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun setLayout() {
        setTitle()
        updateMainToolbar()
        setOnClickBtn()
    }

    private fun updateMainToolbar() {
        mainActivity.updateToolbarLeftImg(R.drawable.ic_back)
        mainActivity.updateToolbarTitle("추가 운동하기")
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
            findNavController().navigate(R.id.action_fragmentAdditionalExerciseSuccess_to_stepperFragment)
        }
    }
}