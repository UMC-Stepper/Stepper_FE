package com.example.umc_stepper.ui.today.evaluation_log

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentEvaluationLogCalenderBinding
import com.example.umc_stepper.ui.MainActivity
import com.example.umc_stepper.ui.stepper.StepperViewModel
import com.example.umc_stepper.ui.today.TodayViewModel
import com.example.umc_stepper.utils.calender.BoldDecorator
import com.example.umc_stepper.utils.calender.EventDecorator
import com.example.umc_stepper.utils.calender.SaturdayDecorator
import com.example.umc_stepper.utils.calender.SelectedMonthDecorator
import com.example.umc_stepper.utils.calender.SundayDecorator
import com.example.umc_stepper.utils.calender.TodayDecorator
import com.example.umc_stepper.utils.enums.LoadState
import com.google.gson.Gson
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class EvaluationLogFragment: BaseFragment<FragmentEvaluationLogCalenderBinding>(R.layout.fragment_evaluation_log_calender) {

    private lateinit var evaluationLogBodyPartAdapter: EvaluationLogBodyPartAdapter
    private lateinit var materialCalendarView: MaterialCalendarView
    private val eventDateList : MutableList<String> = mutableListOf()
    private lateinit var mainActivity : MainActivity

    private val todayViewModel : TodayViewModel by activityViewModels()
    private val stepperViewModel : StepperViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onStart() {
        super.onStart()
    }

    override fun setLayout() {
        initSettings()
    }

    private fun initSettings() {
        updateMainToolbar()
        initAdapter()
        observeViewModel()
        setCalendarView()
        setClickDate()
    }

    private fun updateMainToolbar() {
        mainActivity.updateToolbarTitle("평가 일지")
    }

    private fun initAdapter() {
        evaluationLogBodyPartAdapter = EvaluationLogBodyPartAdapter{ item ->
            viewLifecycleOwner.lifecycleScope.launch{
                eventDateList.add(item.date)
                setCalendarView()
            }
        }
        binding.fragmentEvaluationLogCalenderRv.adapter = evaluationLogBodyPartAdapter
    }

    private fun observeViewModel() {
        val month = LocalDate.now().monthValue
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                todayViewModel.getExerciseMonthCheck(month)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            todayViewModel.exerciseCardStatusResponseDto.collect {
                evaluationLogBodyPartAdapter.submitList(it.result)
            }
        }
    }

    private fun setClickDate() {
        materialCalendarView.setOnDateChangedListener { widget, date, selected ->
            val selectedMonth = date.month
            val selectedDay = date.day
            val selectedYear = date.year
            val formattedDate = String.format("%02d.%02d", selectedMonth, selectedDay)

            val setFormattedDate = String.format("%04d-%02d-%02d",selectedYear, selectedMonth,selectedDay)

            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    // 데이터를 비동기적으로 로드하고, 로드가 완료될 때까지 기다립니다.
                    stepperViewModel.getDiaryConfirm()

                    stepperViewModel.diaryList.collect { diaryResponse ->
                        if (diaryResponse.loadState == LoadState.SUCCESS && diaryResponse.result != null) {
                            val diaryList = diaryResponse.result.filter { it.date == setFormattedDate }
                            Log.d("materialCalendarView", "diaryList : $diaryList")

                            val gson = Gson()
                            val diaryListJson = gson.toJson(diaryList)

                            val args = Bundle().apply {
                                putString("selectedDate", formattedDate)
                                putString("diaryListValue", diaryListJson)
                            }
                            Log.d("materialCalendarView", "diaryListJson : $diaryListJson")

                            val action = EvaluationLogFragmentDirections.actionEvaluationLogFragmentToEvaluationExerciseTodayFragment()
                            findNavController().navigateSafe(action.actionId, args)

                            return@collect
                        } else {
                            Log.d("materialCalendarView", "diaryList is loading or empty")
                        }
                    }
                }
            }
        }
    }

    private fun generateDateList(year: Int, month: Int): List<String> {
        val dateList = mutableListOf<String>()

        val firstDayOfMonth = Calendar.getInstance().apply { set(year, month - 1, 1) }
        val lastDayOfMonth = Calendar.getInstance().apply { set(year, month - 1, firstDayOfMonth.getActualMaximum(Calendar.DAY_OF_MONTH)) }
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

        viewLifecycleOwner.lifecycleScope.launch {

            val dateList = generateDateList(CalendarDay.today().year, CalendarDay.today().month)
            val sundayDecorator = SundayDecorator(requireContext())
            val saturdayDecorator = SaturdayDecorator(requireContext())
            var boldDecorator = BoldDecorator(CalendarDay.today().month)
            val todayDecorator = TodayDecorator(requireContext())
            var selectedMonthDecorator = SelectedMonthDecorator(CalendarDay.today().month)
            val eventDecorator = EventDecorator(requireContext(), eventDateList)

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
                val newEventDecorator = EventDecorator(requireContext(), eventDateList)
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

            materialCalendarView.invalidateDecorators()
            materialCalendarView.addDecorators(
                sundayDecorator,
                saturdayDecorator,
                boldDecorator,
                todayDecorator,
                selectedMonthDecorator,
                eventDecorator
            )
        }
    }

}