package com.example.umc_stepper.ui.stepper

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentExerciseCompleteBinding

class ExerciseCompleteFragment :BaseFragment<FragmentExerciseCompleteBinding>(R.layout.fragment_exercise_complete) {
    override fun setLayout() {
        binding.exerciseCompleteBtn.setOnClickListener {
            goAdditionalExerciseHome()
        }
    }
    private fun goAdditionalExerciseHome(){
        findNavController().navigate(R.id.action_fragmentExerciseComplete_to_stepperFragment)
    }
}