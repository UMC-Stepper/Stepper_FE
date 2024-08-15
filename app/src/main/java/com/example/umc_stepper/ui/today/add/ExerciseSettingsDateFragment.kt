package com.example.umc_stepper.ui.today.add

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentExerciseSettingsDateBinding
import com.example.umc_stepper.domain.model.request.exercise_card_controller.ExerciseCardRequestDto
import com.example.umc_stepper.domain.model.request.exercise_card_controller.ExerciseStepRequestDto
import com.example.umc_stepper.token.TokenManager
import com.example.umc_stepper.ui.today.TodayViewModel
import com.example.umc_stepper.utils.enums.DayOfWeek
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@AndroidEntryPoint
class ExerciseSettingsDateFragment : BaseFragment<FragmentExerciseSettingsDateBinding>(R.layout.fragment_exercise_settings_date) {

    private lateinit var dayTextViews: List<TextView>
    private val selectedDays = mutableSetOf<TextView>()
    val todayViewModel: TodayViewModel by activityViewModels()
    private lateinit var ecrd: ExerciseCardRequestDto
    @Inject
    lateinit var tokenManager: TokenManager
    private var hourTime : String = "0"
    private var hourTime2 : String = "0"
    private var minuteTime : String = "0"
    // 한글 요일을 영어로 변환하는 매핑
    val koreanToEnglishDays = mapOf(
        "일" to "SUNDAY",
        "월" to "MONDAY",
        "화" to "TUESDAY",
        "수" to "WEDNESDAY",
        "목" to "THURSDAY",
        "금" to "FRIDAY",
        "토" to "SATURDAY"
    )

    // 영어 요일을 한글로 변환하는 매핑
    val englishToKoreanDays = koreanToEnglishDays.entries.associate { (k, v) -> v to k }


    @SuppressLint("DefaultLocale")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun setLayout() {
        selectedDays.clear()
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

            hourTime = String.format("%02d", hour)
            hourTime2 = binding.fragmentExerciseSettingsHourTv.text.toString()
            minuteTime = String.format("%02d", minute)
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

    @RequiresApi(Build.VERSION_CODES.O)
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
                            textView?.let {
                                // 요일 비활성화+스타일 변경
                                it.isEnabled = false
                                it.setBackgroundResource(R.drawable.shape_rounded_square_purple_bg2_21dp)
                                it.setTextColor(ContextCompat.getColor(requireContext(), R.color.White))
                                selectedDays.remove(it) // selectedDays 리스트에서 제거
                            }
                        }

                        // 영어 요일명을 한글로 변환
                        val dayNamesInKorean = overlappingDays.joinToString(", ") { englishToKoreanDays[it] ?: it }
                        Toast.makeText(requireContext(), "$dayNamesInKorean 에 이미 운동이 존재합니다.", Toast.LENGTH_SHORT).show()
                    }
                }

                if (!isOverlapping) {
                    goExerciseCardLast() // 겹치는 요일이 없으면 이동
                    postExerciseCard()
                }
            }
        }
    }
    private fun changeDaysToEnglish(day: String) : String = when(day){
        "일" -> "SUNDAY"
        "월" -> "MONDAY"
        "화" -> "TUESDAY"
        "수" -> "WEDNESDAY"
        "목" -> "THURSDAY"
        "금" -> "FRIDAY"
        "토" -> "SATURDAY"
        else -> ""
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun postExerciseCard(){
        // 현재 날짜와 시간을 가져오고 형식변경
        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formattedDate = currentDateTime.format(formatter)
        val stepList : ArrayList<ExerciseStepRequestDto> = arrayListOf()
        val list = todayViewModel.getExerciseList()
        for(i in 0..<todayViewModel.getExerciseListSize()){
            stepList.add(
                ExerciseStepRequestDto(
                    myExerciseId = list[i],
                    step = i + 1
                )
            )
        }
        // 현재 날짜의 요일을 가져와서 대문자로 저장
        val dayOfWeek: java.time.DayOfWeek? = currentDateTime.dayOfWeek
        for(i in 0..<selectedDays.size) {
            ecrd = ExerciseCardRequestDto(
                bodyPart = arguments?.getString("bodyPart") ?: "",
                date = formattedDate,
                week = changeDaysToEnglish(selectedDays.elementAt(i).text.toString()),
                hour = hourTime.toInt(),
                minute = minuteTime.toInt(),
                second = 0,
                materials = binding.fragmentExerciseSettingsExerciseMaterialsEt.text.toString(),
                stepList = stepList
            )

            todayViewModel.postAddExerciseCard(ecrd)
        }
    }

    private fun goExerciseCardLast(){
        // TimePicker  am/pm 정보
        val ampm = if (hourTime < 12.toString()) "AM" else "PM"
        val dayOfWeekMap = mapOf(
            "일" to "일요일",
            "월" to "월요일",
            "화" to "화요일",
            "수" to "수요일",
            "목" to "목요일",
            "금" to "금요일",
            "토" to "토요일"
        )

        val args = Bundle().apply {
            putString("week", selectedDays.joinToString(", ") { textView ->
                // 요일->한국어->0요일  로 변환
                val koreanDay = englishToKoreanDays[textView.text.toString()] ?: textView.text.toString()
                dayOfWeekMap[koreanDay] ?: koreanDay
            }) // 선택된 요일 값 (예: '화요일, 목요일')
            putInt("hourTime", hourTime2.toInt()) // 시
            putInt("minuteTime", minuteTime.toInt()) //분
            putString("material", binding.fragmentExerciseSettingsExerciseMaterialsEt.text.toString()) //준비물
            putString("ampm", ampm) // 오전,오후 값
            putString("type","success")
        }
        val action = ExerciseSettingsDateFragmentDirections.actionFragmentExerciseSettingsDateToExerciseCardLastFragment()
        findNavController().navigate(action.actionId, args)
    }
}