package com.example.umc_stepper.ui.today

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentEvaluationLogCalenderBinding
import com.example.umc_stepper.utils.calender.BoldDecorator
import com.example.umc_stepper.utils.calender.EventDecorator
import com.example.umc_stepper.utils.calender.SaturdayDecorator
import com.example.umc_stepper.utils.calender.SelectedMonthDecorator
import com.example.umc_stepper.utils.calender.SundayDecorator
import com.example.umc_stepper.utils.calender.TodayDecorator
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class EvaluationLogFragment: BaseFragment<FragmentEvaluationLogCalenderBinding>(R.layout.fragment_evaluation_log_calender) {

    private lateinit var materialCalendarView: MaterialCalendarView

    override fun setLayout() {
        setCalendarView()
        setClickDate()
    }

    private fun setClickDate() {

        materialCalendarView.setOnDateChangedListener { widget, date, selected ->
            val selectedYear = if (materialCalendarView.selectedDate != null) {
                materialCalendarView.selectedDate?.year ?: 0
            } else {
                CalendarDay.today().year
            }

            val selectedMonth = if (materialCalendarView.selectedDate != null) {
                materialCalendarView.selectedDate?.month ?: 0
            } else {
                CalendarDay.today().month
            }

            val selectedDay = if (materialCalendarView.selectedDate != null) {
                materialCalendarView.selectedDate?.day ?: 0
            } else {
                CalendarDay.today().day
            }

//            val todoFragment = TodoFragment(selectedYear, selectedMonth, selectedDay)
//            val transaction = requireActivity().supportFragmentManager.beginTransaction()
//            transaction.replace(R.id.fragment_container, todoFragment)
//            transaction.addToBackStack(null)
//            transaction.commit()
        }
    }

    private fun generateDateList(year: Int, month: Int): List<String> {
        val dateList = mutableListOf<String>()

        val firstDayOfMonth = Calendar.getInstance()
        firstDayOfMonth.set(year, month - 1, 1)

        val lastDayOfMonth = Calendar.getInstance()
        lastDayOfMonth.set(year, month - 1, firstDayOfMonth.getActualMaximum(Calendar.DAY_OF_MONTH))

        val dateFormat = "%d-%02d-%02d"

        for (day in firstDayOfMonth.get(Calendar.DAY_OF_MONTH)..lastDayOfMonth.get(Calendar.DAY_OF_MONTH)) {
            dateList.add(String.format(dateFormat, year, month, day))
        }

        return dateList
    }

    private  fun setCalendarView() {
        materialCalendarView = binding.fragmentEvaluationLogCalenderCalendarview
        materialCalendarView.isDynamicHeightEnabled = true

        materialCalendarView.setHeaderTextAppearance(R.style.CalendarWidgetHeader)
        materialCalendarView.setTitleFormatter { day ->
            val calendarHeaderBuilder = StringBuilder()
            val calendar = Calendar.getInstance().apply { set(day.year, day.month - 1, 1) }

            val dateFormat = SimpleDateFormat("yyyy년 MM월", Locale.getDefault())
            val formattedDate = dateFormat.format(calendar.time)

            val calendarHeaderElements = formattedDate.split("년 ")
            val month = calendarHeaderElements[1].replace("월", "").toInt()
            calendarHeaderBuilder.append(calendarHeaderElements[0]).append("년 ")
                .append(month).append("월")

            calendarHeaderBuilder.toString()
        }

        val dateList = generateDateList(CalendarDay.today().year, CalendarDay.today().month)

        val sundayDecorator = SundayDecorator(requireContext())
        val saturdayDecorator = SaturdayDecorator(requireContext())
        var boldDecorator = BoldDecorator(CalendarDay.today().month)
        val todayDecorator = TodayDecorator(requireContext())
        var selectedMonthDecorator = SelectedMonthDecorator(CalendarDay.today().month)
        val eventDecorator = EventDecorator()

        materialCalendarView.addDecorators(
            sundayDecorator,
            saturdayDecorator,
            boldDecorator,
            todayDecorator,
            selectedMonthDecorator,
            eventDecorator
        )
        materialCalendarView.setOnMonthChangedListener { _, date ->

            materialCalendarView.removeDecorators()
            materialCalendarView.invalidateDecorators()

            val newYear = date.year
            val newMonth = date.month
            val newDateList = generateDateList(newYear, newMonth)
            val newEventDecorator = EventDecorator()
            Log.d("로그", "${newDateList}")
            // Decorators 추가
            selectedMonthDecorator = SelectedMonthDecorator(date.month)
            boldDecorator = BoldDecorator(date.month)
            materialCalendarView.addDecorators(
                sundayDecorator,
                saturdayDecorator,
                boldDecorator,
                todayDecorator,
                selectedMonthDecorator,
                newEventDecorator
            )
        }
    }

}