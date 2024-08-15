package com.example.umc_stepper.ui.today

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentExerciseCompleteBinding

class ExerciseCompleteFragment :
    BaseFragment<FragmentExerciseCompleteBinding>(R.layout.fragment_exercise_complete) {

    override fun setLayout() {
        setButton()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSetting()
    }

    private fun setButton() {
        binding.exerciseCompleteBtn.setOnClickListener {
            val action =
                ExerciseCompleteFragmentDirections.actionExerciseCompleteFragmentToTodayHomeFragment()
            findNavController().navigateSafe(action.actionId)
        }
    }

    private fun initSetting() {
        if (!arguments?.getString("description").isNullOrEmpty() &&
            !arguments?.getString("type").isNullOrEmpty()
        ) {
            val des = arguments?.getString("description")
            val type = arguments?.getString("type")

            with(binding){
                exerciseCompleteTv.text = type.toString()
                exerciseCompleteTv2.text = des.toString()
            }
        }

        binding.exerciseCompleteTv
    }

}