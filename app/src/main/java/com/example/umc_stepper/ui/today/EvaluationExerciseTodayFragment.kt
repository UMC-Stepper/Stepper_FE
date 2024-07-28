package com.example.umc_stepper.ui.today

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentEvaluationExerciseTodayBinding

class EvaluationExerciseTodayFragment :
    BaseFragment<FragmentEvaluationExerciseTodayBinding>(R.layout.fragment_evaluation_exercise_today) {

    lateinit var imgList: List<ImageView>
    lateinit var tvList: List<TextView>
    lateinit var triangleList: List<ImageView>
    lateinit var blurList: List<Int>
    lateinit var notBlurList: List<Int>
    lateinit var stateTitleList: List<String>
    lateinit var descriptionList: List<String>
    var selectTextDescription = 0
    var profileImage = ""
    var score = 0

    override fun setLayout() {
        initSetting()
    }

    private fun initSetting() {
        setList()
        initScreen()
        setOnClickBtn()
    }

    private fun initScreen(){
        with(binding){
            fragmentEvaluationExerciseHalfBgTv.text = fragmentEvaluationExerciseScoreTv.text.toString()
            when(selectTextDescription){
                0 -> setDescription(0)
                1 -> setDescription(1)
                2 -> setDescription(2)
                3 -> setDescription(3)
                4 -> setDescription(4)
            }
        }
    }

    private fun setDescription(select : Int){
        imgList[select].setBackgroundResource(blurList[select])
        triangleList[select].visibility = View.VISIBLE
        binding.fragmentEvaluationExerciseStateTv.text = stateTitleList[select]
        binding.fragmentEvaluationExerciseDescriptionTv.text = descriptionList[select]
//        binding.fragmentEvaluationExerciseMemoEt.text = 값을 받은 메모
    }

    private fun setList() {
        with(binding) {
            imgList = listOf(
                fragmentEvaluationExerciseBlur20Iv,
                fragmentEvaluationExerciseBlur40Iv,
                fragmentEvaluationExerciseBlur60Iv,
                fragmentEvaluationExerciseBlur80Iv,
                fragmentEvaluationExerciseBlur100Iv
            )

            tvList = listOf(
                fragmentEvaluationExerciseBlur20Tv,
                fragmentEvaluationExerciseBlur40Tv,
                fragmentEvaluationExerciseBlur60Tv,
                fragmentEvaluationExerciseBlur80Tv,
                fragmentEvaluationExerciseBlur100Tv
            )

            triangleList = listOf(
                fragmentEvaluationExerciseTriangle20Iv,
                fragmentEvaluationExerciseTriangle40Iv,
                fragmentEvaluationExerciseTriangle60Iv,
                fragmentEvaluationExerciseTriangle80Iv,
                fragmentEvaluationExerciseTriangle100Iv
            )

            blurList = listOf(
                R.drawable.ic_img_20_bl,
                R.drawable.ic_img_40_bl,
                R.drawable.ic_img_60_bl,
                R.drawable.ic_img_80_bl,
                R.drawable.ic_img_100_bl,
            )

            notBlurList = listOf(
                R.drawable.ic_img_20,
                R.drawable.ic_img_40,
                R.drawable.ic_img_60,
                R.drawable.ic_img_80,
                R.drawable.ic_img_100,
            )

            stateTitleList = listOf(
                "완전 괜찮아요",
                "큰 차이가 없어요",
                "조금 더 불편해요",
                "완전 아파요",
                "조금 덜 아팠어요"
            )

            descriptionList = listOf(
                "온전한 상태로 회복하고 있는 것 같네요! 오늘 운동도 고생 많았어요 \n" +
                        "차트에 오늘 컨디션 기록할게요!",
                "점점 나아지고 있는 모습 보기 좋아요!\n" +
                        "오늘 운동도 고생 많았어요 \n" +
                        "차트에 오늘 컨디션 기록할게요!",
                "꾸준히 운동을 하면서 통증을 함께 더 \n" +
                        "줄여나가 봅시다! 오늘 운동도 고생 많았어요\n" +
                        "차트에 오늘 컨디션 기록할게요!",
                "오늘 운동할 때 몸이 많이 불편했나요? \n" +
                        "스트레칭으로 충분히 몸을 풀어주세요. \n" +
                        "차트에 오늘 컨디션 기록할게요!",
                "해당 운동이 통증에 맞는 건지 한번 확인해봐요!\n" +
                        "꼼꼼하게 스트레칭 해주시고 통증이\n" +
                        "지속된다면 병원을 방문해 보세요\n" +
                        "차트에 오늘 컨디션 기록할게요!"
            )
        }
    }
    private fun setOnClickBtn() {
        with(binding) {
            fragmentEvaluationExerciseSuccessBt.setOnClickListener{
//                findNavController().navigate() 뒤로가기
            }
        }
    }
}