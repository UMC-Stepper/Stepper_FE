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
    private lateinit var mainActivity : MainActivity
    private lateinit var materialCalendarView: MaterialCalendarView

    private val bodyPartDateMap: MutableMap<String, MutableList<String>> = mutableMapOf()
    private val eventDateSet: MutableSet<CalendarDay> = mutableSetOf()
    private val todayViewModel : TodayViewModel by activityViewModels()
    private val stepperViewModel : StepperViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun setLayout() {
        initSettings()
    }

    private fun initSettings() {
        initAdapter()
        observeViewModel()
        setCalendarView()
        setClickDate()
    }

    private fun setButton() {
        // 뒤로 가기
        binding.fragmentEvaluationLogCalenderBackIv.setOnClickListener {
            findNavController().popBackStack()
        }

        // 투데이 버튼
        binding.fragmentEvaluationLogCalenderGoTodayIv.setOnClickListener {
            val action =
                EvaluationLogFragmentDirections.actionEvaluationLogFragmentToTodayHomeFragment()
            findNavController().navigateSafe(action.actionId)
        }

        // 스테퍼 버튼
        binding.fragmentEvaluationLogCalenderGoStepperIv.setOnClickListener {
            val action =
                EvaluationLogFragmentDirections.actionEvaluationLogFragmentToStepperFragment()
            findNavController().navigateSafe(action.actionId)
        }
    }

    private fun initAdapter() {
        // 운동 부위 리사이클러뷰 아이템 클릭
        evaluationLogBodyPartAdapter = EvaluationLogBodyPartAdapter{ item ->
            viewLifecycleOwner.lifecycleScope.launch{
                val bodyPart = item.bodyPart.toString()
                val selectedDates = bodyPartDateMap[bodyPart]?.map { date ->
                    val parts = date.split("-")
                    CalendarDay.from(parts[0].toInt(), parts[1].toInt(), parts[2].toInt())
                }?.toSet() ?: emptySet()
                eventDateSet.clear()
                eventDateSet.addAll(selectedDates)

                setCalendarView()
            }
        }
        binding.fragmentEvaluationLogCalenderRv.adapter = evaluationLogBodyPartAdapter
    }

    private fun observeViewModel() {
        val month = LocalDate.now().monthValue
        viewLifecycleOwner.lifecycleScope.launch {
            todayViewModel.getExerciseMonthCheck(month)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            todayViewModel.exerciseCardStatusResponseDto.collect { res ->
                val distinctResults = res.result?.distinctBy { it.bodyPart }
                evaluationLogBodyPartAdapter.submitList(distinctResults)

                bodyPartDateMap.clear()
                // 운동 부위 - 날짜로 데이터 추가
                res.result?.forEach { item ->
                    bodyPartDateMap.getOrPut(item.bodyPart.toString()) { mutableListOf() }.add(item.date)
                }
                Log.d("bodyPartDateMap", "bodyPartDateMap : $bodyPartDateMap")
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
                    // 데이터를 비동기적으로 로드하고, 로드가 완료될 때까지 기다림
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

            val sundayDecorator = SundayDecorator(requireContext())
            val saturdayDecorator = SaturdayDecorator(requireContext())
            var boldDecorator = BoldDecorator(CalendarDay.today().month)
            val todayDecorator = TodayDecorator(requireContext())
            var selectedMonthDecorator = SelectedMonthDecorator(CalendarDay.today().month)
            val eventDecorator = EventDecorator(requireContext(), eventDateSet)

            // 기존 데코레이터 제거
            materialCalendarView.removeDecorators()

            // 새로운 데코레이터 추가
            materialCalendarView.addDecorators(
                sundayDecorator,
                saturdayDecorator,
                boldDecorator,
                todayDecorator,
                selectedMonthDecorator,
                eventDecorator
            )
            materialCalendarView.setOnMonthChangedListener { _, date ->

                // 월이 변경되면 새로운 데코레이터 설정
                val newMonth = date.month
                boldDecorator = BoldDecorator(newMonth)
                selectedMonthDecorator = SelectedMonthDecorator(newMonth)

                // 새로운 이벤트 데코레이터 설정
                val newEventDecorator = EventDecorator(requireContext(), eventDateSet)

                // 기존 데코레이터 제거
                materialCalendarView.removeDecorators()

                // 새 데코레이터 추가
                materialCalendarView.addDecorators(
                    sundayDecorator,
                    saturdayDecorator,
                    boldDecorator,
                    todayDecorator,
                    selectedMonthDecorator,
                    newEventDecorator
                )
                materialCalendarView.invalidateDecorators() // 데코레이터 갱신
            }
        }
    }

}