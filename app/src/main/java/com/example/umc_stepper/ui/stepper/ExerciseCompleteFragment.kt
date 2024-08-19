package com.example.umc_stepper.ui.stepper

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentExerciseCompleteBinding
import com.example.umc_stepper.ui.login.MainViewModel

class ExerciseCompleteFragment :BaseFragment<FragmentExerciseCompleteBinding>(R.layout.fragment_exercise_complete) {
    private val mainViewModel: MainViewModel by activityViewModels()
    override fun setLayout() {
        binding.exerciseCompleteBtn.setOnClickListener {
            goStepperHome()
            updateBadge(1) // 첫 오늘의 운동 완료
        }
    }
    private fun goStepperHome(){
        findNavController().navigateSafe(R.id.action_fragmentExerciseComplete_to_stepperFragment)
    }

    private fun updateBadge(i: Int) {
        // 첫 번째 badgeList 항목의 hasBadge 값이 false일 때만 true로 변경하고 토스트 메시지 띄우기
        if (!mainViewModel.badgeList[i].hasBadge) {
            // 첫 번째 badgeList 항목의 hasBadge 값을 true로 설정
            mainViewModel.updateBadgeState(i, true)

            // "새로운 뱃지 획득! My Badge를 확인해주세요"라는 토스트 메시지 띄우기
            Toast.makeText(requireContext(), "새로운 뱃지 획득! My Badge를 확인해주세요", Toast.LENGTH_SHORT).show()
        }
    }
}