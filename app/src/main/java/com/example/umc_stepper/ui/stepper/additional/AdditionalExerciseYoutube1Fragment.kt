package com.example.umc_stepper.ui.stepper.additional

import android.view.WindowManager
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentAdditionalExerciseYoutube1Binding

class AdditionalExerciseYoutube1Fragment:BaseFragment<FragmentAdditionalExerciseYoutube1Binding>(R.layout.fragment_additional_exercise_youtube1) {
    override fun setLayout() {
        barTransparent()
    }
    private fun barTransparent() {
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
    }
}