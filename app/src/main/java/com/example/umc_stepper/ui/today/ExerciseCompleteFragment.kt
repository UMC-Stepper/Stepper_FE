package com.example.umc_stepper.ui.today

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentExerciseCompleteBinding

class ExerciseCompleteFragment :BaseFragment<FragmentExerciseCompleteBinding>(R.layout.fragment_exercise_complete) {

    override fun setLayout() {
        setButton()
    }

    private fun setButton(){
        binding.exerciseCompleteBtn.setOnClickListener {
            val action = ExerciseCompleteFragmentDirections.actionExerciseCompleteFragmentToTodayHomeFragment()
            findNavController().navigateSafe(action.actionId)
        }
    }

}