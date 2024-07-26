package com.example.umc_stepper.ui.stepper.additional

import androidx.navigation.fragment.findNavController
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentAddExerciseDownloadBinding

class AddExerciseDownloadFragment : BaseFragment<FragmentAddExerciseDownloadBinding>(R.layout.fragment_add_exercise_download){
    override fun setLayout() {
        goAddExerciseDownload()
    }
    private fun goAddExerciseDownload(){
        findNavController().navigate(R.id.action_additionalExerciseHomeFragment_to_fragmentAddExerciseDownload)
    }
}