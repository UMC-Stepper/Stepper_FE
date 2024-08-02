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
        // 임시용 enable true
        binding.fragmentAddExerciseDownloadBtn.isEnabled = true

        // 운동 카드 추가 화면으로 되돌아가기 action_addExerciseSelectScrapFragment_to_fragmentAddExercise2
        binding.fragmentAddExerciseDownloadBtn.setOnClickListener {
            val action = AddExerciseSelectScrapFragmentDirections.actionAddExerciseSelectScrapFragmentToFragmentAddExercise2()
            findNavController().navigateSafe(action.actionId)
        }
    }

}