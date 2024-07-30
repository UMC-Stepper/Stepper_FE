package com.example.umc_stepper.ui.stepper

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.TimePicker
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentExerciseSettingsDateBinding

class ExerciseSettingsDateFragment : BaseFragment<FragmentExerciseSettingsDateBinding>(R.layout.fragment_exercise_settings_date) {

    private lateinit var dayTextViews: List<TextView>
    private val selectedDays = mutableSetOf<TextView>()

    override fun setLayout() {
        val timePicker: TimePicker = binding.fragmentExerciseSettingsTimeSpinner
        timePicker.setIs24HourView(false)

        timePicker.setOnTimeChangedListener { _, hour, minute ->
            // 시간 변환
            val hourIn12Format = if (hour % 12 == 0) 12 else hour % 12

            binding.fragmentExerciseSettingsHourTv.text = String.format("%02d", hourIn12Format)
            binding.fragmentExerciseSettingsMinTv.text = String.format("%02d", minute)
        }

        dayTextViews = listOf(
            binding.fragmentExerciseSettingsSundayBt,
            binding.fragmentExerciseSettingsMondayBt,
            binding.fragmentExerciseSettingsTuesdayBt,
            binding.fragmentExerciseSettingsWednesdayBt,
            binding.fragmentExerciseSettingsThursdayBt,
            binding.fragmentExerciseSettingsFridayBt,
            binding.fragmentExerciseSettingsSaturdayBt
        )

        dayTextViews.forEach { textView ->
            textView.setOnClickListener {
                toggleDaySelection(textView)
                updateSelectedDaysText()
            }
        }

        binding.fragmentExerciseSettingsExerciseSuccessBt.setOnClickListener {
            goExerciseCardLast()
        }
    }

    private fun toggleDaySelection(textView: TextView) {
        if (selectedDays.contains(textView)) {
            // 선택풀기
            textView.setBackgroundResource(R.drawable.shape_rounded_square_purple_bg2_21dp)
            textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.White))
            selectedDays.remove(textView)
        } else {
            // 선택하기
            textView.setBackgroundResource(R.drawable.shape_rounded_square_yellow300_21dp)
            textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.Black_500))
            selectedDays.add(textView)
        }
    }

    private fun updateSelectedDaysText() {
        val selectedDaysText = selectedDays.joinToString(" ") { it.text }
        binding.fragmentExerciseSettingsPlanDescription.text = "매주 $selectedDaysText"
    }

    private fun goExerciseCardLast(){
        //fragment_card_last로 넘어가는 네비구현(코틀린코드 작업전이라 보류)
    }
}