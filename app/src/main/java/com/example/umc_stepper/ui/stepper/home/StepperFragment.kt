package com.example.umc_stepper.ui.stepper.home

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
import com.example.umc_stepper.databinding.FragmentStepperBinding
import com.example.umc_stepper.domain.model.response.Badge
import com.example.umc_stepper.domain.model.response.exercise_card_controller.ToDayExerciseResponseDto
import com.example.umc_stepper.domain.model.response.my_exercise_controller.CheckExerciseResponse
import com.example.umc_stepper.token.TokenManager
import com.example.umc_stepper.ui.MainActivity
import com.example.umc_stepper.ui.stepper.ExerciseViewAdapter
import com.example.umc_stepper.ui.stepper.StepperViewModel
import com.example.umc_stepper.ui.today.TodayViewModel
import com.example.umc_stepper.utils.listener.AdapterNextClick
import com.example.umc_stepper.utils.listener.ItemClickListener
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Inject

@AndroidEntryPoint
class StepperFragment : BaseFragment<FragmentStepperBinding>(R.layout.fragment_stepper),
    ItemClickListener, AdapterNextClick {

    private lateinit var recyclerAdapter: ExerciseViewAdapter
    private val stepperViewModel: StepperViewModel by activityViewModels()
    lateinit var days: MutableList<DayData> // MutableList로 변경하여 수정 가능하도록
    private lateinit var mainActivity: MainActivity
    val todayViewModel: TodayViewModel by activityViewModels()

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    private fun setTitle() {
        mainActivity.updateToolbarTitle("STEPPER") // 타이틀 세팅
    }

    override fun setLayout() {
        days = mutableListOf(
            DayData("월", false, false),
            DayData("화", false, false),
            DayData("수", false, false),
            DayData("목", false, false),
            DayData("금", false, false),
            DayData("토", false, false),
            DayData("일", false, false),
            DayData("29", false, false),
            DayData("30", false, false),
            DayData("31", false, false),
            DayData("1", false, false),
            DayData("2", false, false),
            DayData("3", false, false),
            DayData("4", false, false),
            DayData("5", false, false),
            DayData("6", false, false),
            DayData("7", false, false),
            DayData("8", false, false),
            DayData("9", false, false),
            DayData("10", false, false),
            DayData("11", false, false),
            DayData("12", false, false),
            DayData("13", false, false),
            DayData("14", false, false),
            DayData("15", false, false),
            DayData("16", false, false),
            DayData("17", false, false),
            DayData("18", false, false),
            DayData("19", false, false),
            DayData("20", false, false),
            DayData("21", false, false),
            DayData("22", false, false),
            DayData("23", false, false),
            DayData("24", false, false),
            DayData("25", false, false),
            DayData("26", false, false),
            DayData("27", false, false),
            DayData("28", false, false),
            DayData("29", false, false),
            DayData("30", false, false),
            DayData("31", false, false),
            DayData("1", false, false)
        )
        firstConnect()
        init()
        setTitle()
        //임시함수적용: 추후 아래의 goCommunityIndex(), goTodayExercise()함수와 함께 꼭 삭제
        binding.stepperMonthTitleTv.setOnClickListener {
            goTodayExercise()
        }

        //임시함수적용: 추후 아래의 goCommunityPartHome()함수와 함께 꼭 삭제
        binding.stepperExplain1Tv.setOnClickListener {
            goCommunityPartHome()
        }
        //추가운동홈으로 이동
        binding.stepperAdditionalBtn.setOnClickListener {
            goAdditionalExerciseHome()
        }
    }

    private fun observeExerciseMonthCheck() {
        val month = LocalDate.now().monthValue
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                todayViewModel.getExerciseMonthCheck(month)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            todayViewModel.exerciseCardStatusResponseDto.collect { response ->
                val monthExercise = response.result
                monthExercise?.forEach { exercise ->
                    val dayOfMonth = exercise.date.substring(8, 10).toInt() // 날짜의 일 부분만 추출+정수 변환
                    // days 리스트에서 position 값이 10 이상 40 이하인 항목 중 -> day가 일치하는 항목 찾아내기
                    days.forEachIndexed { index, dayData ->
                        if (index in 10..40 && dayData.day.toIntOrNull() == dayOfMonth) {
                            days[index] = dayData.copy(
                                hasDot = !exercise.status,  // status가 false일 때 hasDot을 true로 설정
                                hasIcon = exercise.status   // status가 true일 때 hasIcon을 true로 설정
                            )
                        }
                    }
                }
                // Adapter에 데이터 변경 알림
                (binding.stepperCalendarGv.adapter as CalendarAdapter).notifyDataSetChanged()
            }
        }
    }


    private fun goAdditionalExerciseHome() {
        findNavController().navigate(R.id.action_stepperFragment_to_additionalExerciseHomeFragment)
    }

    //임시함수(꼭 추후삭제) 작성글목록확인으로 이동
    private fun goCommunityIndex() {
        findNavController().navigate(R.id.action_stepperFragment_to_communityIndexFragment)
    }

    //임시함수(꼭 추후삭제) 커뮤니티 부위별 게시판홈으로 이동
    private fun goCommunityPartHome() {
        findNavController().navigate(R.id.action_stepperFragment_to_communityPartHomeFragment)
    }

    //임시함수(꼭 추후삭제) 오늘의 운동하기으로 이동
    private fun goTodayExercise() {
        findNavController().navigate(R.id.action_stepperFragment_to_fragmentTodayExercise)
    }


    private fun observeLifecycle() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                todayViewModel.todayExerciseResponseDto.collectLatest {
                    recyclerAdapter.submitList(it.result)
                }
            }
        }
    }
    private fun firstConnect() {
        val currentDate = LocalDate.now()
        val selectedDate =
            LocalDate.of(currentDate.year, currentDate.monthValue, currentDate.dayOfMonth)
        val formattedDate = selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        todayViewModel.getStepperExerciseState(formattedDate)
    }
    private fun init() {
        val adapter = CalendarAdapter(requireContext(), days)
        binding.stepperCalendarGv.adapter = adapter

        recyclerAdapter = ExerciseViewAdapter(this, this)
        binding.stepperExerciseRv.adapter = recyclerAdapter

        observeLifecycle()
        observeExerciseMonthCheck()

    }

    //운동 카드 수정
    override fun onClick(item: Any) {
        val bd = Bundle()
        if (item is ToDayExerciseResponseDto) {
            val gson = Gson()
            val json = gson.toJson(item)
            //운동카드 리스트 전송
            bd.putString("CardListJson", json)
        }
        findNavController().navigateSafe(
            R.id.action_stepperFragment_to_fragmentTodayExercise,
        )
    }

    //다음 페이지 이동
    override fun onClickNextPage(id: Int, item: Any) {
        //운동 카드 ID 넘겨줌 (후에 평가일지 작성에서 활용)
        if (item is ToDayExerciseResponseDto) {
            saveExerciseCardId(item.id.toString())
            Log.d("아이디", item.id.toString())
            val bd = Bundle()
            val pickItem = item.stepList.first { it.stepId == id }
            with(pickItem) {
                val checkExerciseResponse = CheckExerciseResponse(
                    exerciseId = myExercise?.exerciseId ?: 0,
                    video_image = myExercise?.video_image ?: "",
                    video_title = myExercise?.video_title ?: "",
                    url = myExercise?.url ?: "",
                    channel_name = myExercise?.channel_name ?: ""
                )
                val gson = Gson()
                val myExerciseJson = gson.toJson(checkExerciseResponse)
                val stepList = gson.toJson(item.stepList)
                //step Id 전송
                bd.putInt("stepId", id)
                //step 전송
                bd.putInt("step",pickItem.step)
                //stepList 전송
                bd.putString("stepList", stepList)
                //유투부 정보 전송
                bd.putString("myExercise", myExerciseJson)
                //운동 타입 ( 0 : 오늘의 운동 , 1 : 추가 운동)
                bd.putInt("exerciseType", 0)
                //운동 아이디
                bd.putInt("exerciseId",item.id)
            }
            findNavController().navigateSafe(
                R.id.action_stepperFragment_to_fragmentTodayExercise,
                args = bd
            )
        }
    }

    private fun saveExerciseCardId(id: String) {
        lifecycleScope.launch {
            tokenManager.deleteExerciseCardId()
            tokenManager.saveExerciseCardId(id)
            Log.d("토큰1",id)
        }


    }
}