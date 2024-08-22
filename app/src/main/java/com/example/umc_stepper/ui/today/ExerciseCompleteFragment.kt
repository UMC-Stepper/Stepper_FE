package com.example.umc_stepper.ui.today

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentExerciseCompleteBinding
import com.example.umc_stepper.domain.model.request.exercise_card_controller.ExerciseCardRequestDto
import com.example.umc_stepper.domain.model.request.fcm.ScheduleNotificationRequestDto
import com.example.umc_stepper.ui.login.LoginViewModel
import com.example.umc_stepper.ui.login.MainViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.TimeZone

class ExerciseCompleteFragment :BaseFragment<FragmentExerciseCompleteBinding>(R.layout.fragment_exercise_complete) {

    private val mainViewModel: MainViewModel by activityViewModels()
    private val todayViewModel: TodayViewModel by activityViewModels()
    private val loginViewModel : LoginViewModel by activityViewModels()
    private var id = 0

    override fun setLayout() {
        observeViewModel()
        setButton()
        initSetting()
    }

    private fun observeViewModel() {
        // FCM 전송 id 얻기 위해 유저 정보 조회
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.getUserInfo()
            }
        }
    }

    private fun setButton() {
        // 13이상
        val exerciseCardList: ArrayList<ExerciseCardRequestDto>? =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                arguments?.getParcelableArrayList(
                    "exerciseCardList",
                    ExerciseCardRequestDto::class.java
                )
            } else {
                @Suppress("DEPRECATION")
                arguments?.getParcelableArrayList<ExerciseCardRequestDto>("exerciseCardList")
            }
        val selectDaysSize = arguments?.getInt("selectDaysSize") ?: 0

        binding.exerciseCompleteBtn.setOnClickListener {
            updateBadge(0)
            exerciseCardList?.let { list ->
                lifecycleScope.launch {
                    // 1. 모든 운동 카드를 추가하는 작업
                    for (i in 0 until selectDaysSize) {
                        if(list[i].bodyPart == "무릎, 다리"){
                            list[i].bodyPart = "무릎다리"
                        }
                        else if(list[i].bodyPart == "어깨, 팔"){
                            list[i].bodyPart = "어깨팔"
                        }
                        todayViewModel.postAddExerciseCard(list[i])
                    }

                    // 2. 사용자 id 수집 -> FCM 알림 시간 설정 API 호출
                    val userInfo = loginViewModel.userInfo.first()
                    Log.d("유저 인포", "userInfo : $userInfo")
                    val dateTimeAfterSeconds = getDateTimeAfterSeconds(15)
                    val userId = userInfo.result?.id ?: 0
                    val scheduleNotificationRequestDto = ScheduleNotificationRequestDto(userId, dateTimeAfterSeconds)
                    mainViewModel.postFcmTime(scheduleNotificationRequestDto)

                    // 3. 네비게이션 이동
                    val action =
                        ExerciseCompleteFragmentDirections.actionExerciseCompleteFragmentToTodayHomeFragment()
                    findNavController().navigateSafe(action.actionId)
                }
            }
        }
    }

    // ISO 8601 시간 반환
    fun getDateTimeAfterSeconds(time: Int): String {

        // 현재 시간 가져오기
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.SECOND, time)

        // ISO 8601 형식으로 날짜 및 시간 포맷 지정
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
        dateFormat.timeZone = TimeZone.getDefault()

        return dateFormat.format(calendar.time)
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