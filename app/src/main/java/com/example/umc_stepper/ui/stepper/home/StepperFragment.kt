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
    lateinit var days: List<DayData>
    private lateinit var mainActivity: MainActivity
    val todayViewModel: TodayViewModel by activityViewModels()

    @Inject
    lateinit var tokenManager: TokenManager


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    private fun setTitle() {
        mainActivity.updateToolbarTitle("STEPPER") //타이틀 세팅
    }

    override fun setLayout() {
        days = listOf(
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
            DayData("9", true, false),
            DayData("10", false, false),
            DayData("11", false, false),
            DayData("12", false, false),
            DayData("13", false, false),
            DayData("14", false, false),
            DayData("15", false, false),
            DayData("16", false, true),
            DayData("17", false, false),
            DayData("18", false, false),
            DayData("19", false, false),
            DayData("20", false, false),
            DayData("21", false, false),
            DayData("22", false, false),
            DayData("23", false, false),
            DayData("24", false, false),
            DayData("25", false, false),
            DayData("26", true, false),
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
        //임시함수적용: 추후 아래의 goCommunityIndex()함수와 함께 꼭 삭제
        binding.stepperMonthTitleTv.setOnClickListener {
            goCommunityIndex()
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
            R.id.action_stepperFragment_to_fragmentAddExercise,
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
                //stepList 전송
                bd.putString("stepList", stepList)
                //유투부 정보 전송
                bd.putString("myExercise", myExerciseJson)
                //운동 타입 ( 0 : 오늘의 운동 , 1 : 추가 운동)
                bd.putInt("exerciseType", 0)
            }
            findNavController().navigateSafe(
                R.id.action_stepperFragment_to_fragmentLastExercise,
                args = bd
            )
        }
    }

    private fun saveExerciseCardId(id: String) {
        lifecycleScope.launch {
            tokenManager.deleteExerciseCardId()
            tokenManager.saveExerciseCardId(id)
        }
    }
}