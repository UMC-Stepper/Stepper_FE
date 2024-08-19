package com.example.umc_stepper.ui.today

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentExerciseCompleteBinding
import com.example.umc_stepper.domain.model.request.exercise_card_controller.ExerciseCardRequestDto
import com.example.umc_stepper.ui.login.MainViewModel
import kotlinx.coroutines.launch

class ExerciseCompleteFragment :BaseFragment<FragmentExerciseCompleteBinding>(R.layout.fragment_exercise_complete) {

    private val mainViewModel: MainViewModel by activityViewModels()
    private val todayViewModel: TodayViewModel by activityViewModels()

    override fun setLayout() {
        setButton()
        initSetting()
    }

    private fun setButton() {
        // 13이상
        val exerciseCardList: ArrayList<ExerciseCardRequestDto>? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelableArrayList("exerciseCardList", ExerciseCardRequestDto::class.java)
        } else {
            @Suppress("DEPRECATION")
            arguments?.getParcelableArrayList<ExerciseCardRequestDto>("exerciseCardList")
        }
        val selectDaysSize = arguments?.getInt("selectDaysSize") ?: 0

        binding.exerciseCompleteBtn.setOnClickListener {
            updateBadge(0)
            exerciseCardList?.let { list ->
                lifecycleScope.launch {
                    for (i in 0 until selectDaysSize) {
                        todayViewModel.postAddExerciseCard(list[i])
                    }
                    val action =
                        ExerciseCompleteFragmentDirections.actionExerciseCompleteFragmentToTodayHomeFragment()
                    findNavController().navigateSafe(action.actionId)
                }
            }
        }
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

    private fun initSetting() {
        val des = arguments?.getString("description")
        val type = arguments?.getString("type")
        with(binding){
            exerciseCompleteTv.text = type.toString()
            exerciseCompleteTv2.text = des.toString()
        }
    }

}