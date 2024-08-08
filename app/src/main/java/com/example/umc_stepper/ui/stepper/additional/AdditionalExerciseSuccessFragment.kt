package com.example.umc_stepper.ui.stepper.additional

import android.util.Log
import androidx.navigation.fragment.findNavController
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentTodayExerciseSuccessBinding
import com.example.umc_stepper.domain.model.Time
import com.example.umc_stepper.domain.model.response.RateDiaryResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AdditionalExerciseSuccessFragment : BaseFragment<FragmentTodayExerciseSuccessBinding>(R.layout.fragment_today_exercise_success) {

    override fun setLayout() {
        setTitle()
        setOnClickBtn()

        // 받는 부분
        val args = arguments
        val timeJson = args?.getString("time")

        if (timeJson != null) {
            val gson = Gson()
            val time : Time = gson.fromJson(timeJson, Time::class.java)

            Log.d("AdditionalExerciseSuccessFragment", "time: $time")
        }

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