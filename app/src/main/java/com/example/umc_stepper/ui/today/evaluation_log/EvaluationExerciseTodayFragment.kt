package com.example.umc_stepper.ui.today.evaluation_log

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentEvaluationExerciseTodayBinding
import com.example.umc_stepper.domain.model.response.rate_diary_controller.RateDiaryResponse
import com.example.umc_stepper.ui.MainActivity
import com.example.umc_stepper.ui.stepper.StepperViewModel
import com.example.umc_stepper.ui.today.TodayViewModel
import com.example.umc_stepper.ui.today.add.AddExerciseSelectScrapFragmentDirections
import com.example.umc_stepper.utils.GlobalApplication
import com.example.umc_stepper.utils.enums.LoadState
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
//앞 화면에서 받은 리스트로 조작해야 함 , 평가 일지 조회
class EvaluationExerciseTodayFragment :
    BaseFragment<FragmentEvaluationExerciseTodayBinding>(R.layout.fragment_evaluation_exercise_today) {
    private val stepperViewModel: StepperViewModel by activityViewModels()
    private var imgList: List<ImageView> = listOf()
    private var tvList: List<TextView> = listOf()
    private var triangleList: List<ImageView> = listOf()
    private var blurList: List<Int> = listOf()
    private var notBlurList: List<Int> = listOf()
    private var stateTitleList: List<String> = listOf()
    private var descriptionList: List<String> = listOf()
    private var selectTextDescription = 0
    private var profileImage = ""
    private var score = 0
    private val todayViewModel: TodayViewModel by activityViewModels()
    private lateinit var args: Bundle
    private lateinit var selectedDate: String
    private lateinit var diaryListValue: String
    private lateinit var diaryList: List<RateDiaryResponse>
    private lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    private fun setTitle() {
        if (diaryList.isNotEmpty()) {
            binding.fragmentEvaluationExerciseTodayTitleTv.text = selectedDate
            binding.fragmentEvaluationExerciseTodayBodyTv.text = diaryList[0].bodyPart
            binding.fragmentEvaluationExerciseTodayBodyTv.visibility = View.VISIBLE
        } else {
            binding.fragmentEvaluationExerciseTodayBodyTv.visibility = View.INVISIBLE
        }
    }

    private fun takeDataClass() {
        // 받는 부분
        args = arguments ?: Bundle()
        selectedDate = args.getString("selectedDate")!!
        diaryListValue = args.getString("diaryListValue")!!
        val gson = Gson()
        val type = object : TypeToken<List<RateDiaryResponse>>() {}.type
        diaryList = gson.fromJson(diaryListValue, type)

        // 받은 데이터 사용
        Log.d("EvaluationExerciseTodayFragment3333", "diaryList: $diaryList")
        // 받는 부분
    }

    override fun setLayout() {
        takeDataClass()
        initSetting()
    }

    private fun initSetting() {
        setList()
        setTitle()
        initScreen()
        setObserver()
        setOnClickBtn()
        todayViewModel.successEvaluationLogData()
    }

    private fun setObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                todayViewModel.dataLoadState.collectLatest {
                    if (it == LoadState.LOADING) {
                        mainActivity.showProgress()
                    } else {
                        mainActivity.hideProgress()
                    }
                }
            }
        }


        if (diaryList.isNotEmpty()) {
            binding.fragmentEvaluationExerciseScoreTv.text =
                diaryList[0].conditionRate.toString()
            binding.fragmentEvaluationExercisePointTv.text =
                diaryList[0].conditionRate.toString()
            binding.fragmentEvaluationExerciseProgressbarPb.progress =
                diaryList[0].conditionRate
            binding.fragmentEvaluationExerciseMemoEt.text =
                diaryList[0].painMemo
            setDescription(diaryList[0].painRate)
            GlobalApplication.loadCropRoundedSquareImage(
                context = requireContext(),
                source = diaryList[0].painImage,
                imageView = binding.fragmentEvaluationExercisePictureExerciseIv,
                rounded = 18
            )
        } else {
            binding.allConstraintCl.visibility = View.GONE
            binding.allConstraint2Cl.visibility = View.VISIBLE
        }
    }


    //앞에서 보내준 리스트로 파싱 지정날짜 운동카드 넘겨줘야함
    private fun initScreen() {
        stepperViewModel.getDiaryConfirm()
    }

    private fun setDescription(select: Int) {
        imgList[select].setBackgroundResource(blurList[select])
        triangleList[select].visibility = View.VISIBLE
        binding.fragmentEvaluationExerciseStateTv.text = stateTitleList[select]
        binding.fragmentEvaluationExerciseDescriptionTv.text = descriptionList[select]
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
                "조금 덜 아팠어요",
                "큰 차이가 없어요",
                "조금 더 불편해요",
                "많이 아파요",
            )

            descriptionList = listOf(
                "온전한 상태로 회복하고 있는 것 같네요!\n오늘 운동도 고생 많았어요\n" +
                        "차트에 오늘 컨디션 기록할게요!",
                "점점 나아지고 있는 모습 보기 좋아요!\n" +
                        "오늘 운동도 고생 많았어요 \n" +
                        "차트에 오늘 컨디션 기록할게요!",
                "꾸준히 운동을 하면서 통증을 함께 더 \n" +
                        "줄여나가 봅시다!\n오늘 운동도 고생 많았어요\n" +
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
            // 뒤로 가기
            fragmentEvaluationExerciseTodayBackIv.setOnClickListener {
                findNavController().popBackStack()
            }

            // 투데이 버튼
            fragmentEvaluationExerciseTodayGoTodayIv.setOnClickListener {
                val action =
                    EvaluationExerciseTodayFragmentDirections.actionEvaluationExerciseTodayFragmentToTodayHomeFragment()
                findNavController().navigateSafe(action.actionId)
            }

            // 스테퍼 버튼
            fragmentEvaluationExerciseTodayGoStepperIv.setOnClickListener {
                val action =
                    EvaluationExerciseTodayFragmentDirections.actionEvaluationExerciseTodayFragmentToStepperFragment()
                findNavController().navigateSafe(action.actionId)
            }
            // 완료 버튼
            fragmentEvaluationExerciseSuccessBt.setOnClickListener {
                todayViewModel.loadEvaluationLogData()
                mainActivity.showProgress()
                val action =
                    EvaluationExerciseTodayFragmentDirections.actionEvaluationExerciseTodayFragmentToEvaluationLogFragment()
                findNavController().navigateSafe(action.actionId)
            }
        }
        popBack()
    }

    private fun popBack() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    todayViewModel.loadEvaluationLogData()
                    mainActivity.showProgress()
                    val action =
                        EvaluationExerciseTodayFragmentDirections.actionEvaluationExerciseTodayFragmentToEvaluationLogFragment()
                    findNavController().navigateSafe(action.actionId)
                }
            })

    }

    override fun onStop() {
        super.onStop()
        todayViewModel.loadEvaluationLogData()
    }

}