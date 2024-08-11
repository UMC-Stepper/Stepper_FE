package com.example.umc_stepper.ui.stepper.additional

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentAdditionalExerciseHomeBinding
import com.example.umc_stepper.ui.MainActivity
import com.example.umc_stepper.ui.stepper.StepperViewModel
import com.example.umc_stepper.ui.today.TodayViewModel
import kotlinx.coroutines.launch

class AdditionalExerciseHomeFragment : BaseFragment<FragmentAdditionalExerciseHomeBinding>(R.layout.fragment_additional_exercise_home) {

    private lateinit var mainActivity : MainActivity
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
    override fun setLayout() {
        setTodayExerciseTime()
        updateMainToolbar()
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

    private fun setTodayExerciseTime(){
        val hour = arguments?.getString("hour")
        val minute = arguments?.getString("minute")
        val seconds = arguments?.getString("seconds")
        if(hour!=null){
            binding.fragmentAdditionalExerciseHourTv.text = hour
        }
        if(minute!=null){
            binding.fragmentAdditionalExerciseMinTv.text=minute
        }
        if(seconds!=null){
            binding.fragmentAdditionalExerciseSecTv.text=seconds
        }

    }

    private fun updateMainToolbar() {
        mainActivity.updateToolbarTitle("추가 운동하기")
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