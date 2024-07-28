package com.example.umc_stepper.ui.stepper.additional

import android.view.WindowManager
import androidx.navigation.fragment.findNavController
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentAdditionalExerciseYoutube2Binding

class AdditionalExerciseYoutube2Fragment: BaseFragment <FragmentAdditionalExerciseYoutube2Binding>(R.layout.fragment_additional_exercise_youtube2) {
    override fun setLayout() {
        barTransparent()
    }
    private fun barTransparent() {
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
    }
}