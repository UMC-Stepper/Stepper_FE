package com.example.umc_stepper.ui.today.add

import androidx.navigation.fragment.findNavController
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentAddExerciseSelectScrapBinding
import com.example.umc_stepper.ui.stepper.additional.AddExerciseDownloadFragment
import com.example.umc_stepper.ui.stepper.additional.AddExerciseDownloadFragmentDirections
import com.example.umc_stepper.ui.today.home.TodayHomeFragmentDirections
import com.example.umc_stepper.utils.extensions.navigateSafe

class AddExerciseSelectScrapFragment: BaseFragment<FragmentAddExerciseSelectScrapBinding>(R.layout.fragment_add_exercise_select_scrap) {

    override fun setLayout() {
        setButton()
    }

    private fun setButton() {
        // 운동 카드 추가 화면으로 되돌아가기
        binding.fragmentAddExerciseAddDownloadStep1CompleteBtn.setOnClickListener {
            val action = AddExerciseDownloadFragmentDirections.actionFragmentAddExerciseDownloadToFragmentAddExercise()
            findNavController().navigateSafe(action.actionId)
        }
    }

}