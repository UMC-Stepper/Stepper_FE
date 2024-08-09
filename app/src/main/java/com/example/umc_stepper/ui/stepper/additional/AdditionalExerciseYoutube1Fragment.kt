package com.example.umc_stepper.ui.stepper.additional

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentAdditionalExerciseYoutube1Binding
import com.example.umc_stepper.ui.MainActivity

class AdditionalExerciseYoutube1Fragment : BaseFragment<FragmentAdditionalExerciseYoutube1Binding>(R.layout.fragment_additional_exercise_youtube1) {

    private lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun setLayout() {
        enabledComplete()
        setupTextWatcher()
        updateToolbar()
        binding.fragmentYoutubeUrlLoadCompleteBtn.setOnClickListener {
            goAdditionalExerciseYoutube2()
        }
    }

    private fun enabledComplete() {
        if (binding.fragmentYoutubeUrlLoadInputUrlEt.text.isNullOrEmpty()) {
            binding.fragmentYoutubeUrlLoadCompleteBtn.isEnabled = false
        } else {
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

    private fun updateToolbar() {
        mainActivity.updateToolbarTitle("추가 운동하기")
        mainActivity.updateToolbarLeftImg(R.drawable.ic_back)
        mainActivity.updateToolbarMiddleImg(R.drawable.ic_toolbar_today)
        mainActivity.updateToolbarRightImg(R.drawable.ic_toolbar_stepper)
    }

    private fun goAdditionalExerciseYoutube2() {
        val urlText = binding.fragmentYoutubeUrlLoadInputUrlEt.text.toString()
        val bundle = Bundle().apply {
            putString("urlText", urlText)
        }
        findNavController().navigate(R.id.action_fragmentAdditionalExerciseYoutube1_to_fragmentAdditionalExerciseYoutube2, bundle)
    }
}
