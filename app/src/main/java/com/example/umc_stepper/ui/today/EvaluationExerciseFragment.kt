package com.example.umc_stepper.ui.today

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentEvaluationExerciseBinding
import com.example.umc_stepper.utils.GlobalApplication
import java.lang.NumberFormatException

class EvaluationExerciseFragment :
    BaseFragment<FragmentEvaluationExerciseBinding>(R.layout.fragment_evaluation_exercise) {

    lateinit var imgList: List<ImageView>
    lateinit var tvList: List<TextView>
    lateinit var triangleList: List<ImageView>
    lateinit var blurList: List<Int>
    lateinit var notBlurList: List<Int>
    lateinit var stateTitleList: List<String>
    lateinit var descriptionList: List<String>
    var selectTextDescription = 0
    private lateinit var galleryForResult: ActivityResultLauncher<Intent>
    var profileImage = ""
    var score = 0

    override fun setLayout() {
        initSetting()
    }

    private fun initSetting() {
        initActivityResultLauncher()
        setList()
        setScoreText()
        setOnClickBtn()
    }

    private fun setScoreText() {
        binding.fragmentEvaluationExercisePointTv.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                score = try {
                    binding.fragmentEvaluationExercisePointTv.text.toString().toInt()
                } catch (e: NumberFormatException) {
                    0
                }
                if (score in 0..100) {
                    binding.fragmentEvaluationExerciseProgressbarPb.progress = score
                } else {
                    binding.fragmentEvaluationExerciseProgressbarPb.progress = 0
                    score = 0
                }
                binding.fragmentEvaluationExerciseScoreTv.text = "${score}점"
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }


    private fun setOnClickBtn() {
        with(binding) {
            fragmentEvaluationExerciseBlur20Iv.setOnClickListener {
                selectTextDescription = 0
                stateAllToggle()
            }
            fragmentEvaluationExerciseBlur40Iv.setOnClickListener {
                selectTextDescription = 1
                stateAllToggle()
            }
            fragmentEvaluationExerciseBlur60Iv.setOnClickListener {
                selectTextDescription = 2
                stateAllToggle()
            }
            fragmentEvaluationExerciseBlur80Iv.setOnClickListener {
                selectTextDescription = 3
                stateAllToggle()
            }
            fragmentEvaluationExerciseBlur100Iv.setOnClickListener {
                selectTextDescription = 4
                stateAllToggle()
            }
            fragmentEvaluationExercisePictureExerciseIv.setOnClickListener {
                openGallery()
            }
            fragmentEvaluationExerciseSuccessBt.setOnClickListener{
                val memo = fragmentEvaluationExerciseMemoEt.text.toString() //메모
                val state = selectTextDescription //상태값 0 1 2 3 4
                val imageUrl = profileImage //이미지
                val point = score //점수
                //위의 값들 서버로 전달
            }
        }
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

    private fun stateAllToggle() {
        with(binding) {
            fragmentEvaluationExerciseDescriptionCl.visibility = View.VISIBLE
            for (i in 0..4) {
                if (i == selectTextDescription) {
                    imgList[i].setBackgroundResource(blurList[i])
                    triangleList[i].visibility = View.VISIBLE
                    fragmentEvaluationExerciseStateTv.text = stateTitleList[i]
                    fragmentEvaluationExerciseDescriptionTv.text = descriptionList[i]
                } else {
                    imgList[i].setBackgroundResource(notBlurList[i])
                    triangleList[i].visibility = View.GONE
                }
            }
        }
    }

    private fun initActivityResultLauncher() {
        galleryForResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                val selectImageUrl = result.data?.data
                selectImageUrl?.let {
                    GlobalApplication.loadCropRoundedSquareImage(
                        requireContext(),
                        binding.fragmentEvaluationExercisePictureExerciseIv,
                        it.toString(),
                        18
                    )
                    binding.fragmentEvaluationExercisePictureExerciseIb.visibility = View.GONE
                    profileImage = it.toString()
                }
            }
        }
    }

    private fun openGallery() {
        val galleryIntent = Intent()
            .setType("image/*")
            .setAction(Intent.ACTION_GET_CONTENT)
        galleryForResult.launch(Intent.createChooser(galleryIntent, "Select Picture"))
    }
}