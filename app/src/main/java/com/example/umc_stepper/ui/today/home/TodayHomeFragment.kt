package com.example.umc_stepper.ui.today.home

import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentTodayHomeBinding
import com.example.umc_stepper.domain.model.local.WeekCalendar
import com.example.umc_stepper.utils.extensions.navigateSafe
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.TemporalAdjuster
import org.threeten.bp.temporal.TemporalAdjusters
import java.util.Locale

class TodayHomeFragment : BaseFragment<FragmentTodayHomeBinding>(R.layout.fragment_today_home) {

    private lateinit var calendarAdapter: TodayHomeCalendarAdapter
    private lateinit var todayHomeCalendarAdapter: TodayHomeCalendarAdapter
    private var calendarList = ArrayList<WeekCalendar>()


    override fun setLayout() {
        setNavigationAction()
        setMonth()
        setAdapter()
        initAdapter()
    }

    private fun setMonth() {
        val monthFormat = DateTimeFormatter.ofPattern("M월").withLocale(Locale.forLanguageTag("ko"))
        val localDateMonth = LocalDateTime.now().format(monthFormat)
        //binding.fragmentTodayHomeCalenderMonthTv.text = localDateMonth
    }

    private fun setAdapter() {
        todayHomeCalendarAdapter = TodayHomeCalendarAdapter { item ->
            //Toast.makeText(requireContext(), "$item", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initAdapter() {
        var weekDay = resources.getStringArray(R.array.calendar_day_eng)
        val dateFormat = DateTimeFormatter.ofPattern("dd").withLocale(Locale.forLanguageTag("ko"))
        var preSunday: LocalDateTime = LocalDateTime.now().with(TemporalAdjusters.previous(DayOfWeek.SUNDAY))

        for (i in 0..6) {
            calendarList.apply {
                val currentDate = preSunday.plusDays(i.toLong()).format(dateFormat)
                val currentDay = weekDay[i]
                calendarList.add(WeekCalendar(currentDate, currentDay, false))
            }
        }
        binding.fragmentTodayHomeWeekCalendarRv.adapter = todayHomeCalendarAdapter
        todayHomeCalendarAdapter.submitList(calendarList.subList(0, calendarList.size.coerceAtMost(7)))
    }

    private fun setNavigationAction() {

        // 운동 추가
        binding.fragmentTodayHomePlusExerciseAddIv.setOnClickListener {
            val action = TodayHomeFragmentDirections.actionTodayHomeFragmentToFragmentAddExercise()
            findNavController().navigateSafe(action.actionId)
        }

        // 나만의 운동
        binding.fragmentTodayHomeMyExerciseConstraint.setOnClickListener {
            val action = TodayHomeFragmentDirections.actionTodayHomeFragmentToFragmentMyExercise3()
            findNavController().navigateSafe(action.actionId)
        }

        // 평가 일지
        binding.fragmentTodayHomeEvaluationLogConstraint.setOnClickListener {
            val action = TodayHomeFragmentDirections.actionTodayHomeFragmentToEvaluationLogFragment()
            findNavController().navigateSafe(action.actionId)
        }

    }
}