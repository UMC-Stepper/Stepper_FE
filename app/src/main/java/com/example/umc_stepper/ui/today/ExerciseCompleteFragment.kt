package com.example.umc_stepper.ui.today

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
        setButton()
    }

    private fun setButton(){
        binding.exerciseCompleteBtn.setOnClickListener {
            updateBadge(0)
            val action = ExerciseCompleteFragmentDirections.actionExerciseCompleteFragmentToTodayHomeFragment()
            findNavController().navigateSafe(action.actionId)
        }
    }

    //뱃지전역함수(뷰모델에 넣으면 부를때 복잡해지길래..)
    fun updateBadge(i:Int) {
        // 첫 번째 badgeList 항목의 hasBadge 값이 false일 때만 true로 변경하고 토스트 메시지 띄우기
        if (!mainViewModel.badgeList[i].hasBadge) {
            // 첫 번째 badgeList 항목의 hasBadge 값을 true로 설정
            mainViewModel.badgeList[i].hasBadge = true

            // "새로운 뱃지 획득! My Badge를 확인해주세요"라는 토스트 메시지 띄우기
            Toast.makeText(requireContext(), "새로운 뱃지 획득! My Badge를 확인해주세요", Toast.LENGTH_SHORT).show()
        }
    }

}