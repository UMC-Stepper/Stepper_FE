package com.example.umc_stepper.ui.today

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentTodayHomeBinding

class TodayHomeFragment : BaseFragment<FragmentTodayHomeBinding>(R.layout.fragment_today_home) {
    override fun setLayout() {
        binding.fragmentTodayHomeEvaluationLogConstraint.setOnClickListener {
            findNavController().navigate(R.id.action_todayHomeFragment_to_fragmentEvaluationExercise)
        }
    }
}