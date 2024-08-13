package com.example.umc_stepper.ui.today.add

import android.widget.NumberPicker
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentExerciseSettingsDateBinding
import com.example.umc_stepper.ui.stepper.home.CalendarAdapter
import com.example.umc_stepper.ui.today.TodayViewModel
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate

class ExerciseSettingsDateFragment : BaseFragment<FragmentExerciseSettingsDateBinding>(R.layout.fragment_exercise_settings_date) {

    private lateinit var dayTextViews: List<TextView>
    private val selectedDays = mutableSetOf<TextView>()
    val todayViewModel: TodayViewModel by activityViewModels()

    override fun setLayout() {
        val timePicker: TimePicker = binding.fragmentExerciseSettingsTimeSpinner
        timePicker.setIs24HourView(false)
        val amPmSpinnerId = resources.getIdentifier("amPm", "id", "android")
        val amPmSpinner: NumberPicker = timePicker.findViewById(amPmSpinnerId)
        amPmSpinner.displayedValues = arrayOf("AM", "PM")

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
            observeExerciseCheckData()
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

    private fun observeExerciseCheckData() {
        val bodyPart: String = when (arguments?.getString("bodyPart").toString()) {
            "무릎, 다리" -> "무릎다리"
            "어깨, 팔" -> "어깨팔"
            else -> "머리"
        }

        viewLifecycleOwner.lifecycleScope.launch {
            todayViewModel.getExerciseCheckDate(bodyPart)

            todayViewModel.exerciseCardWeekResponseDto.collect { response ->
                val checkExercise = response.result
                var isOverlapping = false

                // 한글 요일을 영어로 변환
                val koreanToEnglishDays = mapOf(
                    "일" to "SUNDAY",
                    "월" to "MONDAY",
                    "화" to "TUESDAY",
                    "수" to "WEDNESDAY",
                    "목" to "THURSDAY",
                    "금" to "FRIDAY",
                    "토" to "SATURDAY"
                )

                // 선택한 요일을 영어로 변환
                val selectedEnglishDays = selectedDays.mapNotNull { textView ->
                    koreanToEnglishDays[textView.text.toString()]
                }

                checkExercise?.forEach { check ->
                    val weeks = check.weeks?.map { it.name } ?: emptyList() // DayOfWeek -> String 변환
                    val overlappingDays = selectedEnglishDays.filter { day ->
                        weeks.contains(day)
                    }

                    if (overlappingDays.isNotEmpty()) {
                        isOverlapping = true
                        overlappingDays.forEach { day ->
                            val textView = selectedDays.firstOrNull { koreanToEnglishDays[it.text.toString()] == day }
                            textView?.isEnabled = false // 겹치는 요일 비활성화
                        }
                        val dayNames = overlappingDays.joinToString(", ") { it }
                        Toast.makeText(requireContext(), "$dayNames 에 이미 운동이 존재합니다.", Toast.LENGTH_SHORT).show()
                    }
                }

                if (!isOverlapping) {
                    goExerciseCardLast() // 겹치는 요일이 없으면 이동
                }
            }
        }
    }



    private fun goExerciseCardLast(){
        //fragment_card_last로 넘어가는 네비구현(코틀린코드 작업전이라 보류)
        val action = ExerciseSettingsDateFragmentDirections.actionFragmentExerciseSettingsDateToExerciseCardLastFragment()
        findNavController().navigate(action.actionId)
    }
}