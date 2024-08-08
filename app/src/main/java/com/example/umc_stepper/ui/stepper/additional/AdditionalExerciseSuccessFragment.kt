package com.example.umc_stepper.ui.stepper.additional

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentTodayExerciseSuccessBinding
import com.example.umc_stepper.domain.model.Time
import com.example.umc_stepper.ui.stepper.StepperViewModel
import com.example.umc_stepper.utils.extensions.navTop
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.properties.Delegates
import com.google.gson.Gson

//앞에서 시간 넘어와야 함
@AndroidEntryPoint
class AdditionalExerciseSuccessFragment :
    BaseFragment<FragmentTodayExerciseSuccessBinding>(R.layout.fragment_today_exercise_success) {
    private val stepperViewModel: StepperViewModel by activityViewModels()
    private val titleList : List<String> = listOf(
        "오늘 이만큼 운동 했어요!",
        "오늘 이만큼 추가 운동 했어요!"
    )


    //이거 필요
    private var titleNumber by Delegates.notNull<Int>()
    override fun setLayout() {
        setTitle()
        observeLifeCycle()
        setOnClickBtn()

        // 받는 부분
        val args = arguments
        val timeJson = args?.getString("time")

        if (timeJson != null) {
            val gson = Gson()
            val time : Time = gson.fromJson(timeJson, Time::class.java)

            Log.d("AdditionalExerciseSuccessFragment", "time: $time")
        }

    }


    // 오늘 이만큼 운동 했어요!
    private fun setTitle() {
//        titleNumber = arguments?.getInt("titleNumber") ?: 0

        with(binding) {
            fragmentTodayExerciseSuccessTitleTv.text = titleList[titleNumber]
            fragmentTodayExerciseSuccessHourTv.text
            fragmentTodayExerciseSuccessMinuteTv
            fragmentTodayExerciseSuccessSecondTv
        }
    }

    private fun setOnClickBtn() {
        binding.fragmentTodayExerciseSuccessOkBtn.setOnClickListener {
            when (titleNumber) {
                1 ->{
                    //운동
                    findNavController().navTop(R.id.action_fragmentAdditionalExerciseSuccess_to_fragmentEvaluationExercise)
                }
                2 -> {
                    stepperViewModel.saveMoreExerciseTime(
                        //추가운동
                        Time(
                            hour = binding.fragmentTodayExerciseSuccessHourTv.text.toString(),
                            minutes = binding.fragmentTodayExerciseSuccessMinuteTv.text.toString(),
                            seconds = binding.fragmentTodayExerciseSuccessSecondTv.text.toString()
                        )
                    )
                }
                else -> {

                }
            }
        }
    }

    private fun observeLifeCycle() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                stepperViewModel.addTimeState.collectLatest {
                    if (it.isSuccess) {
                        val action =
                            R.id.action_fragmentAdditionalExerciseSuccess_to_stepperFragment
                        findNavController().navTop(action)
                    }
                }
            }
        }
    }
}