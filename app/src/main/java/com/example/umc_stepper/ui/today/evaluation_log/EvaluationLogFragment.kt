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
import com.example.umc_stepper.base.BaseListResponse
import com.example.umc_stepper.databinding.FragmentEvaluationLogCalenderBinding
import com.example.umc_stepper.domain.model.response.exercise_card_controller.ExerciseCardStatusResponseDto
import com.example.umc_stepper.ui.MainActivity
import com.example.umc_stepper.ui.stepper.StepperViewModel
import com.example.umc_stepper.ui.today.TodayViewModel
import com.example.umc_stepper.utils.calender.*
import com.example.umc_stepper.utils.enums.LoadState
import com.google.gson.Gson
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDate
import java.text.SimpleDateFormat
import java.util.*

class EvaluationLogFragment :
    BaseFragment<FragmentEvaluationLogCalenderBinding>(R.layout.fragment_evaluation_log_calender) {

    private lateinit var evaluationLogBodyPartAdapter: EvaluationLogBodyPartAdapter
    private lateinit var mainActivity: MainActivity
    private lateinit var materialCalendarView: MaterialCalendarView

    private val bodyPartDateMap: MutableMap<String, MutableList<String>> = mutableMapOf()
    private val eventDateSet: MutableSet<CalendarDay> = mutableSetOf()
    private val todayViewModel: TodayViewModel by activityViewModels()
    private val stepperViewModel: StepperViewModel by activityViewModels()

    private lateinit var sundayDecorator: SundayDecorator
    private lateinit var saturdayDecorator: SaturdayDecorator
    private lateinit var todayDecorator: TodayDecorator

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity.hideProgress()

    }
    override fun setLayout() {
        observeDataLoadState()
        initSettings()
    }

    private fun observeDataLoadState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                todayViewModel.dataLoadState.collectLatest { state ->
                    when (state) {
                        LoadState.LOADING -> mainActivity.showProgress()
                        LoadState.SUCCESS -> {
                            mainActivity.hideProgress()
                        }
                        else -> mainActivity.hideProgress()
                    }
                }
            }
        }
    }

    private fun initSettings() {
        initAdapter()
        initCalendarDecorators()
        observeViewModel()
        setCalendarView()
        setClickDate()
        setButton()
        todayViewModel.successEvaluationLogData()
    }


    private fun initAdapter() {
        evaluationLogBodyPartAdapter = EvaluationLogBodyPartAdapter { item ->
            viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Default) {
                val bodyPart = item.bodyPart.toString()
                val selectedDates = bodyPartDateMap[bodyPart]?.map { date ->
                    val parts = date.split("-")
                    CalendarDay.from(parts[0].toInt(), parts[1].toInt(), parts[2].toInt())
                }?.toSet() ?: emptySet()
                eventDateSet.clear()
                eventDateSet.addAll(selectedDates)
                withContext(Dispatchers.Main) {
                    updateCalendarView()
                }
            }
        }
        binding.fragmentEvaluationLogCalenderRv.adapter = evaluationLogBodyPartAdapter
    }


    private fun initCalendarDecorators() {
        sundayDecorator = SundayDecorator(requireContext())
        saturdayDecorator = SaturdayDecorator(requireContext())
        todayDecorator = TodayDecorator(requireContext())
    }

    private fun observeViewModel() {
        val month = LocalDate.now().monthValue
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                todayViewModel.getExerciseMonthCheck(month)
                todayViewModel.exerciseCardStatusResponseDto.collect { res ->
                    if (res.result.isNullOrEmpty()) {
                    }
                    processExerciseData(res)
                }
            }
        }
    }

    private fun processExerciseData(res: BaseListResponse<ExerciseCardStatusResponseDto>) {
        viewLifecycleOwner.lifecycleScope.launch {
            // 백그라운드에서 데이터 처리
            val processedData = withContext(Dispatchers.Default) {
                val newBodyPartDateMap = mutableMapOf<String, MutableList<String>>()
                val distinctResults = res.result?.distinctBy { it.bodyPart }

                res.result?.forEach { item ->
                    newBodyPartDateMap.getOrPut(item.bodyPart.toString()) { mutableListOf() }
                        .add(item.date)
                }

                val newEventDateSet = res.result?.mapNotNull { item ->
                    val parts = item.date.split("-")
                    if (parts.size == 3) {
                        CalendarDay.from(parts[0].toInt(), parts[1].toInt(), parts[2].toInt())
                    } else {
                        null
                    }
                }?.toSet() ?: emptySet()

                Triple(newBodyPartDateMap, distinctResults, newEventDateSet)
            }

            // UI 스레드에서 결과 적용
            withContext(Dispatchers.Main) {
                bodyPartDateMap.clear()
                bodyPartDateMap.putAll(processedData.first)
                evaluationLogBodyPartAdapter.submitList(processedData.second)

            }
            //eventDateSet.clear()
            //eventDateSet.addAll(processedData.third)
            //updateCalendarView()
        }
    }

    private var lastClickTime = 0L

    private fun setClickDate() {
        materialCalendarView.setOnDateChangedListener { _, date, _ ->
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastClickTime > 300) {
                lastClickTime = currentTime
                handleDateClick(date)
            }
        }
    }

    private fun handleDateClick(date: CalendarDay) {
        viewLifecycleOwner.lifecycleScope.launch {
            val formattedDate = String.format("%02d.%02d", date.month, date.day)
            val setFormattedDate = String.format("%04d-%02d-%02d", date.year, date.month, date.day)
            stepperViewModel.getDiaryConfirm()
            stepperViewModel.diaryList.collect { diaryResponse ->
                if(diaryResponse.loadState == LoadState.LOADING){

                }
                if (diaryResponse.loadState == LoadState.SUCCESS && diaryResponse.result != null) {
                    val diaryList = diaryResponse.result.filter { it.date == setFormattedDate }
                    val gson = Gson()
                    val diaryListJson = gson.toJson(diaryList)

                    val args = Bundle().apply {
                        putString("selectedDate", formattedDate)
                        putString("diaryListValue", diaryListJson)
                    }

                    val action =
                        EvaluationLogFragmentDirections.actionEvaluationLogFragmentToEvaluationExerciseTodayFragment()
                    findNavController().navigateSafe(action.actionId, args)
                } else {
                    Log.d("materialCalendarView", "diaryList is loading or empty")
                }
            }
        }
    }

    private fun setCalendarView() {

        materialCalendarView = binding.fragmentEvaluationLogCalenderCalendarview
        materialCalendarView.isDynamicHeightEnabled = true
        materialCalendarView.setHeaderTextAppearance(R.style.CalendarWidgetHeader)
        materialCalendarView.setTitleFormatter(::formatCalendarTitle)
        val today = CalendarDay.today()
        val minDate = CalendarDay.from(today.year, 8, 1)
        val maxDate = CalendarDay.from(today.year,8, 31)
        materialCalendarView.state().edit()
            .setMinimumDate(minDate)
            .setMaximumDate(maxDate)
            .commit()

        updateCalendarView()

        materialCalendarView.setOnMonthChangedListener { _, date ->
            updateCalendarView(date.month)
        }
    }

    private fun formatCalendarTitle(day: CalendarDay): String {
        val calendar = Calendar.getInstance().apply { set(day.year, day.month - 1, 1) }
        val dateFormat = SimpleDateFormat("yyyy년 MM월", Locale.getDefault())
        val formattedDate = dateFormat.format(calendar.time)
        val calendarHeaderElements = formattedDate.split("년 ")
        val month = calendarHeaderElements[1].replace("월", "").toInt()
        return "${calendarHeaderElements[0]}년 $month 월"
    }

    private fun updateCalendarView(month: Int = CalendarDay.today().month) {
        val boldDecorator = BoldDecorator(month)
        val selectedMonthDecorator = SelectedMonthDecorator(month)
        val eventDecorator = EventDecorator(requireContext(), eventDateSet)

        materialCalendarView.removeDecorators()
        materialCalendarView.addDecorators(
            sundayDecorator,
            saturdayDecorator,
            boldDecorator,
            todayDecorator,
            selectedMonthDecorator,
            eventDecorator
        )
    }

    private fun setButton() {
        binding.fragmentEvaluationLogCalenderBackIv.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.fragmentEvaluationLogCalenderGoTodayIv.setOnClickListener {
            val action =
                EvaluationLogFragmentDirections.actionEvaluationLogFragmentToTodayHomeFragment()
            findNavController().navigateSafe(action.actionId)
        }

        binding.fragmentEvaluationLogCalenderGoStepperIv.setOnClickListener {
            lifecycleScope.launch {
                todayViewModel.loadEvaluationLogData() // 데이터 로드 시작
                todayViewModel.dataLoadState.collectLatest { state ->
                    if (state == LoadState.LOADING) {
                        todayViewModel.loadEvaluationLogData()
                        val action =
                            EvaluationLogFragmentDirections.actionEvaluationLogFragmentToStepperFragment()
                        findNavController().navigateSafe(action.actionId)
                    }
                }
            }
        }
    }

}