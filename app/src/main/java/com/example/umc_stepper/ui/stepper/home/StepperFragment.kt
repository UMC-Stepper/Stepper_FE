package com.example.umc_stepper.ui.stepper.home

import android.content.Context
import android.os.Bundle
import android.view.View.OnClickListener
import android.view.WindowManager
import android.widget.AdapterView.OnItemClickListener
import androidx.navigation.fragment.findNavController
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentStepperBinding
import com.example.umc_stepper.ui.MainActivity
import com.example.umc_stepper.ui.stepper.additional.AdditionalExerciseHomeFragment
import com.example.umc_stepper.ui.stepper.ExerciseViewAdapter
import com.example.umc_stepper.ui.stepper.LevelItem
import com.example.umc_stepper.ui.stepper.LevelListItem
import com.example.umc_stepper.ui.stepper.StepperViewModel
import com.example.umc_stepper.utils.listener.ItemClickListener
import dagger.hilt.android.AndroidEntryPoint

class StepperFragment : BaseFragment<FragmentStepperBinding>(R.layout.fragment_stepper), ItemClickListener {
    private lateinit var recyclerAdapter: ExerciseViewAdapter
    private lateinit var stepperViewModel : StepperViewModel
    lateinit var days : List<DayData>
    private lateinit var mainActivity : MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
    private fun setTitle(){
        mainActivity.updateToolbarTitle("STEPPER") //타이틀 세팅
    }
    override fun setLayout() {
        setTitle()
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
        init()
        //임시함수적용: 추후 아래의 goCommunityIndex()함수와 함께 꼭 삭제
        binding.stepperMonthTitleTv.setOnClickListener {
            goCommunityIndex()
        }

        //임시함수적용: 추후 아래의 goCommunityPartHome()함수와 함께 꼭 삭제
        binding.stepperExplain1Tv.setOnClickListener {
            goCommunityPartHome()
        }
    }

    private fun init() {
        stepperViewModel = ViewModelProvider(this)[StepperViewModel::class.java]

        val adapter = CalendarAdapter(requireContext(), days)
        binding.stepperCalendarGv.adapter = adapter

        recyclerAdapter = ExerciseViewAdapter(this)
        binding.stepperExerciseRv.adapter = recyclerAdapter
        binding.stepperAdditionalBtn.setOnClickListener {
            goAdditionalExerciseHome()
        }

        stepperViewModel.levelItems.observe(viewLifecycleOwner) { levelItems ->
            recyclerAdapter.submitList(levelItems)
        }

    }
    private fun goAdditionalExerciseHome(){
        findNavController().navigate(R.id.action_stepperFragment_to_additionalExerciseHomeFragment)
    }

    override fun onClick(item: Any) {
        val bd = Bundle()
        if(item is LevelItem){
            bd.putString("pick",item.pick)
        }
        findNavController().navigateSafe(
            R.id.action_stepperFragment_to_fragmentAddExercise,
            bd
        )
    }

    //임시함수(꼭 추후삭제) 작성글목록확인으로 이동
    private fun goCommunityIndex(){
        findNavController().navigate(R.id.action_stepperFragment_to_communityIndexFragment)
    }

    //임시함수(꼭 추후삭제) 커뮤니티 부위별 게시판홈으로 이동
    private fun goCommunityPartHome(){
        findNavController().navigate(R.id.action_stepperFragment_to_communityPartHomeFragment)
    }
}