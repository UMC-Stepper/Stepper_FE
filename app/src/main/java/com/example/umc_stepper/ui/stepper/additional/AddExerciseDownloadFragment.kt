package com.example.umc_stepper.ui.stepper.additional

import android.view.WindowManager
import androidx.navigation.fragment.findNavController
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentAddExerciseDownloadBinding

class AddExerciseDownloadFragment : BaseFragment<FragmentAddExerciseDownloadBinding>(R.layout.fragment_add_exercise_download){
    override fun setLayout() {
        barTransparent()
    }
    private fun barTransparent() {
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
    }
}