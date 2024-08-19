package com.example.umc_stepper.ui.stepper.additional

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentTodayExerciseSuccessBinding
import com.example.umc_stepper.domain.model.Time
import com.example.umc_stepper.ui.MainActivity
import com.example.umc_stepper.ui.login.MainViewModel
import com.example.umc_stepper.ui.stepper.StepperViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates
import com.google.gson.Gson

//앞에서 시간 넘어와야 함
@AndroidEntryPoint
class AdditionalExerciseSuccessFragment :
    BaseFragment<FragmentTodayExerciseSuccessBinding>(R.layout.fragment_today_exercise_success) {
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var mainActivity : MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
    private val stepperViewModel: StepperViewModel by activityViewModels()
    private val titleList: List<String> = listOf(
        "오늘 이만큼 운동 했어요!",
        "오늘 이만큼 추가 운동 했어요!"
    )

    private val btnList: List<String> = listOf(
        "운동 평가하기",
        "추가 운동 완료"
    )

    //이거 필요
    private var titleNumber by Delegates.notNull<Int>()
    override fun setLayout() {
        updateMainToolbar()
        setTitle()
        setOnClickBtn()
    }


    // 오늘 이만큼 운동 했어요!
    private fun setTitle() {
        with(binding) {
            // 받는 부분
            val args = arguments
            val timeJson = args?.getString("time")

            if (timeJson != null) {
                val gson = Gson()
                val time: Time = gson.fromJson(timeJson, Time::class.java)

                Log.d("AdditionalExerciseSuccessFragment", "time: $time")

                titleNumber = arguments?.getInt("titleNumber") ?: 0


                fragmentTodayExerciseSuccessTitleTv.text = titleList[titleNumber]
                fragmentTodayExerciseSuccessOkBtn.text= btnList[titleNumber]
                resultTimeData = time
            }
        }
    }

    private fun setOnClickBtn() {
        binding.fragmentTodayExerciseSuccessOkBtn.setOnClickListener {
            when (titleNumber) {
                0 -> {
                    //운동
                    val action = AdditionalExerciseSuccessFragmentDirections.actionFragmentAdditionalExerciseSuccessToFragmentEvaluationExercise()
                    findNavController().navigateSafe(action.actionId)
                }

                1 -> {
                    stepperViewModel.saveMoreExerciseTime(
                        //추가운동
                        Time(
                            hour = binding.fragmentTodayExerciseSuccessHourTv.text.toString(),
                            minutes = binding.fragmentTodayExerciseSuccessMinuteTv.text.toString(),
                            seconds = binding.fragmentTodayExerciseSuccessSecondTv.text.toString()
                        )
                    )
                    val hour = binding.fragmentTodayExerciseSuccessHourTv.text.toString()
                    val minute = binding.fragmentTodayExerciseSuccessMinuteTv.text.toString()
                    val seconds = binding.fragmentTodayExerciseSuccessSecondTv.text.toString()

                    val bundle = Bundle().apply {
                        putString("hour", hour)
                        putString("minute", minute)
                        putString("seconds", seconds)
                    }
                    updateBadge(2) // 첫 추가 운동 완료 뱃지
                    val action = AdditionalExerciseSuccessFragmentDirections.actionFragmentAdditionalExerciseSuccessToAdditionalExerciseHomeFragment()
                    findNavController().navigateSafe(action.actionId, bundle)
                }

                else -> {

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


    private fun updateMainToolbar() {
        mainActivity.updateToolbarTitle("운동 완료")
        mainActivity.updateToolbarLeftImg(R.drawable.ic_back)
        mainActivity.updateToolbarMiddleImg(R.drawable.ic_toolbar_today)
        mainActivity.updateToolbarRightImg(R.drawable.ic_toolbar_stepper)
    }
}