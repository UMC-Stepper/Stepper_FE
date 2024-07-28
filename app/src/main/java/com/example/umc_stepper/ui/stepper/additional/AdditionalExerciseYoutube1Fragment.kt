package com.example.umc_stepper.ui.stepper.additional

import android.text.Editable
import android.text.TextWatcher
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentAdditionalExerciseYoutube1Binding

class AdditionalExerciseYoutube1Fragment:BaseFragment<FragmentAdditionalExerciseYoutube1Binding>(R.layout.fragment_additional_exercise_youtube1) {
    override fun setLayout() {
        enabledComplete()
        setupTextWatcher()
        binding.fragmentYoutubeUrlLoadCompleteBtn.setOnClickListener {
            goAdditionalExerciseYoutube2()
        }
    }

    private fun enabledComplete() {
        if (binding.fragmentYoutubeUrlLoadInputUrlEt.text.isNullOrEmpty()){
            binding.fragmentYoutubeUrlLoadCompleteBtn.isEnabled = false
        }else {
            binding.fragmentYoutubeUrlLoadCompleteBtn.isEnabled = true
            binding.fragmentYoutubeUrlLoadCompleteBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.White))
            binding.fragmentYoutubeUrlLoadCompleteBtn.setBackgroundResource(R.drawable.shape_rounded_square_purple700_60dp)
        }
    }

    private fun setupTextWatcher() {
        binding.fragmentYoutubeUrlLoadInputUrlEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Do nothing
            }

            override fun afterTextChanged(s: Editable?) {
                enabledComplete()
            }
        })
    }

    private fun goAdditionalExerciseYoutube2(){
        findNavController().navigate(R.id.action_fragmentAdditionalExerciseYoutube1_to_fragmentAdditionalExerciseYoutube2)
    }
}