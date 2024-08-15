package com.example.umc_stepper.ui.today.add

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentExerciseCardLastBinding
import com.example.umc_stepper.domain.model.local.ExerciseAlarm

class ExerciseCardLastFragment:BaseFragment<FragmentExerciseCardLastBinding>(R.layout.fragment_exercise_card_last) {

    private lateinit var exerciseAlarmAdapter: ExerciseAlarmAdapter

    private val dayOfWeekMap = mapOf(
        "일" to "일요일",
        "월" to "월요일",
        "화" to "화요일",
        "수" to "수요일",
        "목" to "목요일",
        "금" to "금요일",
        "토" to "토요일"
    )

    override fun setLayout() {
        setButton()
        setAdapter()
    }

    private fun setButton() {

        binding.fragmentExerciseCardLastCompleteBtn.setOnClickListener {
            val action = ExerciseCardLastFragmentDirections.actionExerciseCardLastFragmentToExerciseCompleteFragment()
            findNavController().navigateSafe(action.actionId, Bundle().apply {
                putAll(arguments)    // 앞에서 받은 Bundle 그대로 다음 프래그먼트로 전달
            })
        }
    }

    private fun setAdapter() {
        exerciseAlarmAdapter = ExerciseAlarmAdapter()
        binding.fragmentExerciseCardLastScheduleRv.adapter = exerciseAlarmAdapter

        arguments?.let { args ->
            val alarm = createExerciseAlarm(
                week = args.getString("week"),
                hourTime = args.getInt("hourTime"),
                minuteTime = args.getInt("minuteTime"),
                material = args.getString("material"),
                ampm = args.getString("ampm")
            )
            exerciseAlarmAdapter.submitList(listOf(alarm))
        }
    }

    private fun createExerciseAlarm(week: String?, hourTime: Int, minuteTime: Int, material: String?, ampm: String?): ExerciseAlarm {
        val alarmWeek = week?.let { dayOfWeekMap[it] } ?: "알 수 없는 요일"
        val alarmHour = if (ampm == "PM") hourTime + 12 else hourTime
        val alarmAmPm = if (ampm == "PM") "오후" else "오전"
        val alarmTime = String.format("%02d:%02d", alarmHour, minuteTime)

        return ExerciseAlarm(
            day = alarmWeek,
            time = alarmTime,
            amPm = alarmAmPm,
            materials = material ?: "",
            isEnabled = true
        )
    }

    private fun getDayOfWeek(week: String): String {
        return dayOfWeekMap[week] ?: "?요일"
    }

}