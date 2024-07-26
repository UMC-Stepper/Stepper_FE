package com.example.umc_stepper.ui.today

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentMyExercise3Binding
import com.example.umc_stepper.utils.GlobalApplication
import java.lang.Thread.sleep


class MyExercise3Fragment :
    BaseFragment<FragmentMyExercise3Binding>(R.layout.fragment_my_exercise_3) {

    lateinit var tagList: List<TextView>
    var selectItem = -1
    override fun setLayout() {
        initSetting()
    }

    private fun initSetting() {
        setList()
        setOnClickBtn()
    }

    private fun setList() {
        with(binding) {
            tagList = listOf(
                fragmentMyExerciseSelectTag1Tv,
                fragmentMyExerciseSelectTag2Tv,
                fragmentMyExerciseSelectTag3Tv,
                fragmentMyExerciseSelectTag4Tv,
                fragmentMyExerciseSelectTag5Tv,
                fragmentMyExerciseSelectTag6Tv,
                fragmentMyExerciseSelectTag7Tv
            )
        }
    }

    private fun setOnClickBtn() {
        with(binding) {
            fragmentMyExerciseSelectTag1Tv.setOnClickListener {
                selectListen(0)
            }
            fragmentMyExerciseSelectTag2Tv.setOnClickListener {
                selectListen(1)
            }
            fragmentMyExerciseSelectTag3Tv.setOnClickListener {
                selectListen(2)
            }
            fragmentMyExerciseSelectTag4Tv.setOnClickListener {
                selectListen(3)
            }
            fragmentMyExerciseSelectTag5Tv.setOnClickListener {
                selectListen(4)
            }
            fragmentMyExerciseSelectTag6Tv.setOnClickListener {
                selectListen(5)
            }
            fragmentMyExerciseSelectTag7Tv.setOnClickListener {
                selectListen(6)
            }

            fragmentMyExerciseCompleteInputBt.setOnClickListener{
                val youtubeUrl = fragmentMyExerciseUploadYoutubeLinkEt.text.toString()
                //url 담아서 다음 화면 전송
            }
        }
    }

    private fun selectListen(select: Int) {
        for (i in 0..6) {
            if (i == select) {
                tagList[i].setBackgroundResource(setColor(requireContext(), true).first)
                tagList[i].setTextColor(setColor(requireContext(), true).second)
            } else {
                tagList[i].setBackgroundResource(setColor(requireContext(), false).first)
                tagList[i].setTextColor(setColor(requireContext(), false).second)
            }
        }
        callAIVideo(select)
    }

    private fun setColor(context: Context, selectState: Boolean): Pair<Int, Int> {
        val backGroundColor: Int
        val textColor: Int
        if (selectState) {
            backGroundColor = R.drawable.shape_rounded_square_yellow700_40dp
            textColor = ContextCompat.getColor(context, R.color.Purple_Black_BG_2)
        } else {
            backGroundColor = R.drawable.shape_rounded_square_purpleblack_bg2_25dp
            textColor = ContextCompat.getColor(context, R.color.Yellow_700)
        }
        return Pair(backGroundColor, textColor)
    }


    private fun callAIVideo(select: Int) {
        with(binding) {
            fragmentMyExerciseProgressbarPbCl.visibility = View.VISIBLE

            fragmentMyExerciseSearchResultCard1Cl.visibility = View.GONE
            fragmentMyExerciseSearchResultCard2Cl.visibility = View.GONE
            fragmentMyExerciseSelectOkTagTv.text = tagList[select].text.toString()

            fragmentMyExerciseSelectOkTagTv.visibility = View.VISIBLE
            fragmentMyExerciseSelectOkDescriptionTagTv.visibility = View.VISIBLE

            fragmentMyExercise3ProgressbarPb.visibility = View.VISIBLE
            fragmentMyExercise3ProgressbarTextTv.visibility = View.VISIBLE

            // 25%로 고정된 프로그레스 바 설정
            fragmentMyExercise3ProgressbarPb.progress = 25

            // 회전 애니메이션 설정
            val rotateAnimation =
                ObjectAnimator.ofFloat(fragmentMyExercise3ProgressbarPb, "rotation", 0f, 360f)
            rotateAnimation.duration = 2000 // 2초 동안 한 바퀴 회전
            rotateAnimation.repeatCount = ValueAnimator.INFINITE
            rotateAnimation.interpolator = LinearInterpolator()
            rotateAnimation.start()

            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({
                // 2초 후 애니메이션 정지 및 프로그레스 바 숨기기
                rotateAnimation.cancel()
                fragmentMyExercise3ProgressbarPb.visibility = View.GONE
                fragmentMyExercise3ProgressbarTextTv.visibility = View.GONE

                downloadSetting(select)
            }, 2000) // 2000ms = 2초
        }
    }

    private fun downloadSetting(select: Int) {
        with(binding) {
            fragmentMyExerciseSearchResultCard1Cl.visibility = View.VISIBLE
            fragmentMyExerciseSearchResultCard2Cl.visibility = View.VISIBLE

            fragmentMyExercisePlayerTitle1Tv.text =
                "[중환자분들을 위한 재활 운동] 근손실 예방을 위한 스트레칭 | 누운 자세에서 운동하기(초급3)"
            fragmentMyExercisePlayerTitle2Tv.text =
                "[중환자분들을 위한 재활 운동] 근손실 예방을 위한 스트레칭 | 누운 자세에서 운동하기(초급3)"

            fragmentMyExerciseChannelName1Tv.text = "손재웅의 사생활"
            fragmentMyExerciseChannelName2Tv.text = "손재웅의 사생활"

            //GlobalApplication.loadProfileImage(fragmentMyExerciseProfile1Iv,"url")
            //GlobalApplication.loadProfileImage(fragmentMyExerciseProfile1Iv,"url")

            //GlobalApplication.loadCropRoundedSquareImage(fragmentMyExerciseThumbnail1Iv,"url")
            //GlobalApplication.loadCropRoundedSquareImage(fragmentMyExerciseThumbnail2Iv,"url")

        }
        //서버에서 불러온 것
    }
}
