package com.example.umc_stepper.ui.stepper.additional

import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentAdditionalExerciseHomeBinding

class AdditionalExerciseHomeFragment :
    BaseFragment<FragmentAdditionalExerciseHomeBinding>(R.layout.fragment_additional_exercise_home) {
    override fun setLayout() {
        binding.addtionalExerciseHomeScrapBtn.setOnClickListener {
            binding.addtionalExerciseHomeScrapBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.White))
            binding.addtionalExerciseHomeScrapBtn.setBackgroundResource(R.drawable.shape_rounded_square_purple700_14dp)
            goAddExerciseDownload()
        }

        binding.addtionalExerciseHomeYoutubeBtn.setOnClickListener {
            binding.addtionalExerciseHomeYoutubeBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.White))
            binding.addtionalExerciseHomeYoutubeBtn.setBackgroundResource(R.drawable.shape_rounded_square_purple700_14dp)
            goAdditionalExerciseYoutube1()

        }

        binding.addtionalExerciseHomeTimeBtn.setOnClickListener {
            binding.addtionalExerciseHomeTimeBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.White))
            binding.addtionalExerciseHomeTimeBtn.setBackgroundResource(R.drawable.shape_rounded_square_purple700_14dp)
            goAdditionalExerciseMiddle()

        }

    }
    private fun goAddExerciseDownload(){
        findNavController().navigate(R.id.action_additionalExerciseHomeFragment_to_fragmentAddExerciseDownload)
    }

    private fun goAdditionalExerciseYoutube1(){
        findNavController().navigate(R.id.action_additionalExerciseHomeFragment_to_fragmentAdditionalExerciseYoutube1)
    }

    private fun goAdditionalExerciseMiddle(){
        findNavController().navigate(R.id.action_additionalExerciseHomeFragment_to_fragmentAdditionalExerciseMiddle)
    }


}