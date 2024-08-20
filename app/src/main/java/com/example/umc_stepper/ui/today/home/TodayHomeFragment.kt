package com.example.umc_stepper.ui.today.home

import android.content.Context
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentTodayHomeBinding
import com.example.umc_stepper.domain.model.local.WeekCalendar
import com.example.umc_stepper.domain.model.request.fcm.FCMNotificationRequestDto
import com.example.umc_stepper.domain.model.request.fcm.ScheduleNotificationRequestDto
import com.example.umc_stepper.ui.MainActivity
import com.example.umc_stepper.ui.login.LoginViewModel
import com.example.umc_stepper.ui.login.MainViewModel
import com.example.umc_stepper.ui.today.TodayViewModel
import com.example.umc_stepper.utils.enums.LoadState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.TemporalAdjusters
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

class TodayHomeFragment : BaseFragment<FragmentTodayHomeBinding>(R.layout.fragment_today_home) {

    private lateinit var todayHomeCalendarAdapter: TodayHomeCalendarAdapter
    private lateinit var todayHomeExerciseCardAdapter: TodayHomeExerciseCardAdapter
    private val todayViewModel : TodayViewModel by activityViewModels()
    private var calendarList = ArrayList<WeekCalendar>()
    private lateinit var mainActivity: MainActivity
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

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
        val currentDate = LocalDate.now()
        val selectedDate = LocalDate.of(currentDate.year, currentDate.monthValue, currentDate.dayOfMonth)
        val formattedDate = selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
            todayViewModel.getTodayExerciseState(formattedDate)
             }
        }
    }

    private fun updateUIVisibility() {
        val exerciseStateList = todayViewModel.exerciseState.value
        var isSuccess = exerciseStateList?.firstOrNull()?.isSuccess ?: false

        if (isSuccess) {
            Log.d("로그", "isSuccess true : $isSuccess")
            binding.fragmentTodayHomeExerciseCardRv.visibility = View.VISIBLE
            binding.fragmentTodayHomeNoCardTv.visibility = View.INVISIBLE
            todayHomeExerciseCardAdapter.submitList(exerciseStateList)
        } else {
            Log.d("로그", "isSuccess false : $isSuccess")
            binding.fragmentTodayHomeExerciseCardRv.visibility = View.INVISIBLE
            binding.fragmentTodayHomeNoCardTv.visibility = View.VISIBLE
        }
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
                todayViewModel.exerciseState.collect {
                    Log.d("로그", "Received exerciseState: ${todayViewModel.exerciseState.value}")
                    updateUIVisibility()
                }
            }
        }
    }

    // 어댑터 초기화 및 날짜 설정
    private fun initAdapter() {

        todayHomeExerciseCardAdapter = TodayHomeExerciseCardAdapter()
        binding.fragmentTodayHomeExerciseCardRv.adapter = todayHomeExerciseCardAdapter
        binding.fragmentTodayHomeExerciseCardRv.itemAnimator = null

        // 어댑터 초기화 및 클릭 리스너 설정 (날짜 바뀌면서 운동카드 갱신)
        todayHomeCalendarAdapter = TodayHomeCalendarAdapter { formattedDate ->
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    todayViewModel.getTodayExerciseState(formattedDate)
                    //updateUIVisibility()
                }
            }
        }

        var weekDay = resources.getStringArray(R.array.calendar_day_eng)
        val dateFormat = DateTimeFormatter.ofPattern("dd").withLocale(Locale.forLanguageTag("ko"))
        var preSunday: LocalDateTime = LocalDateTime.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY))
        Log.d("weekDay", "weekDay : $weekDay, preSunday: $preSunday")

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

        // 스태퍼 화면 이동
        binding.fragmentTodayHomeGoStepperIv.setOnClickListener {
            val action = TodayHomeFragmentDirections.actionTodayHomeFragmentToStepperFragment()
            findNavController().navigateSafe(action.actionId)
        }

        // 운동 카드 추가
        binding.fragmentTodayHomePlusExerciseAddIv.setOnClickListener {
            todayViewModel.clearStep()
            todayViewModel.clearExerciseList()
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
            lifecycleScope.launch {
                todayViewModel.loadEvaluationLogData() // 데이터 로드 시작
                todayViewModel.dataLoadState.collectLatest { state ->
                    if (state == LoadState.LOADING) {
                        mainActivity.showProgress()
                        val action = TodayHomeFragmentDirections.actionTodayHomeFragmentToEvaluationLogFragment()
                        findNavController().navigateSafe(action.actionId)
                    }
                }
            }
        }
    }
}