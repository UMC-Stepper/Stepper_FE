package com.example.umc_stepper.ui.today.home

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentTodayHomeBinding
import com.example.umc_stepper.domain.model.local.WeekCalendar
import com.example.umc_stepper.ui.today.TodayViewModel
import com.example.umc_stepper.utils.extensions.navigateSafe
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.TemporalAdjuster
import org.threeten.bp.temporal.TemporalAdjusters
import java.util.Locale

class TodayHomeFragment : BaseFragment<FragmentTodayHomeBinding>(R.layout.fragment_today_home) {

    private lateinit var todayHomeCalendarAdapter: TodayHomeCalendarAdapter
    private val todayViewModel : TodayViewModel by activityViewModels()
    private var calendarList = ArrayList<WeekCalendar>()


    override fun setLayout() {
        initSettings()
        setNavigationAction()
    }

    private fun initSettings() {
        setMonth()
        initAdapter()
        firstConnect()
        observeViewModel()
    }

    // 첫 접속시 오늘 날짜로 운동카드 설정됨
    private fun firstConnect() {

    }

    // 툴바 달 설정
    private fun setMonth() {
        val monthFormat = DateTimeFormatter.ofPattern("M월").withLocale(Locale.forLanguageTag("ko"))
        val localDateMonth = LocalDateTime.now().format(monthFormat)
        binding.fragmentTodayHomeCalenderMonthTv.text = localDateMonth
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                todayViewModel.toDayExerciseResponseDto.collect { toDayExerciseResponseDto ->
                    toDayExerciseResponseDto.result?.bodyPart
                }
            }
        }
    }

    // 어댑터 날짜 초기화
    private fun initAdapter() {

        var weekDay = resources.getStringArray(R.array.calendar_day_eng)
        val dateFormat = DateTimeFormatter.ofPattern("dd").withLocale(Locale.forLanguageTag("ko"))
        var preSunday: LocalDateTime = LocalDateTime.now().with(TemporalAdjusters.previous(DayOfWeek.SUNDAY))
        Log.d("weekDay", "weekDay : $weekDay, preSunday: $preSunday")

        // 어댑터 초기화 및 클릭 리스너 설정
        todayHomeCalendarAdapter = TodayHomeCalendarAdapter { formattedDate ->
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    todayViewModel.getTodayExerciseState(formattedDate)
                }
            }
        }

        for (i in 0..6) {
            calendarList.apply {
                val currentDate = preSunday.plusDays(i.toLong()).format(dateFormat)
                val currentDay = weekDay[i]
                calendarList.add(WeekCalendar(currentDate, currentDay, "first",false))
            }
        }
        binding.fragmentTodayHomeWeekCalendarRv.adapter = todayHomeCalendarAdapter
        todayHomeCalendarAdapter.submitList(calendarList.subList(0, calendarList.size.coerceAtMost(7)))
    }

    private fun setNavigationAction() {

        // 운동 카드 추가
        binding.fragmentTodayHomePlusExerciseAddIv.setOnClickListener {
            val action = TodayHomeFragmentDirections.actionTodayHomeFragmentToFragmentAddExercise()
            findNavController().navigateSafe(action.actionId)
        }

        // 나만의 운동
        binding.fragmentTodayHomeMyExerciseConstraint.setOnClickListener {
            val action = TodayHomeFragmentDirections.actionTodayHomeFragmentToFragmentMyExercise2()
            findNavController().navigateSafe(action.actionId)
        }

        // 평가 일지
        binding.fragmentTodayHomeEvaluationLogConstraint.setOnClickListener {
            val action = TodayHomeFragmentDirections.actionTodayHomeFragmentToEvaluationLogFragment()
            findNavController().navigateSafe(action.actionId)
        }

    }
}