package com.example.umc_stepper.ui.today.add

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentAddExerciseBinding
import com.example.umc_stepper.domain.model.request.exercise_card_controller.ExerciseCardRequestDto
import com.example.umc_stepper.domain.model.response.exercise_card_controller.ExerciseStepResponse
import com.example.umc_stepper.domain.model.response.exercise_card_controller.ToDayExerciseResponseDto
import com.example.umc_stepper.domain.model.response.my_exercise_controller.CheckExerciseResponse
import com.example.umc_stepper.ui.MainActivity
import com.example.umc_stepper.ui.stepper.StepperViewModel
import com.example.umc_stepper.ui.today.TodayViewModel
import com.example.umc_stepper.utils.GlobalApplication
import com.example.umc_stepper.utils.enums.UpdateState
import com.example.umc_stepper.utils.enums.bodyPartType
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject
import kotlin.properties.Delegates

@AndroidEntryPoint
class AddExerciseFragment :
    BaseFragment<FragmentAddExerciseBinding>(R.layout.fragment_add_exercise) {
    private val todayViewModel: TodayViewModel by activityViewModels()
    private lateinit var tagList: List<TextView>
    private lateinit var addItemList: List<ConstraintLayout>
    private lateinit var nonAddItemList: List<ConstraintLayout>
    private lateinit var thumbnailList: List<ImageView>
    private lateinit var titleList: List<TextView>
    private lateinit var editBtnList: List<Button>
    private lateinit var channelList: List<TextView>
    private var selectNumber = 0
    private var bodyPart = ""
    private lateinit var mainActivity: MainActivity
    private val stepperViewModel : StepperViewModel by activityViewModels()
    private var cardListJson = ""

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    private fun setTitle() {
        mainActivity.updateToolbarTitle("운동 카드를 작성해봐요!")
        mainActivity.updateToolbarLeftImg(R.drawable.ic_back)
        mainActivity.updateToolbarMiddleImg(R.drawable.ic_toolbar_today)
        mainActivity.updateToolbarRightImg(R.drawable.ic_toolbar_stepper)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cardListJson = arguments?.getString("CardListJson").orEmpty()
        Log.d("카드도착", cardListJson)
    }
    override fun setLayout() {
        initSetting()
    }

    private fun initSetting() {
        setTitle()
        setMenuList()
        setList()
        stateSelect()
        setOnClick()
    }


    private fun setOnClick() {
        val us = UpdateState.ADD
        binding.fragmentAddExerciseNoneAddStep1Constraint.setOnClickListener {
            go1(us)
        }
        binding.fragmentAddExerciseNoneAddStep2Constraint.setOnClickListener {
            go2(us)
        }
        binding.fragmentAddExerciseNoneAddStep3Constraint.setOnClickListener {
            go3(us)
        }
        // 다음으로 버튼 클릭
        binding.fragmentAddExerciseNextBtn.setOnClickListener {
            if (cardListJson.isNotEmpty()) {
                //운동카드 스텝 수정
                val toDayExerciseResponseDto: ToDayExerciseResponseDto =
                    Gson().fromJson(cardListJson, ToDayExerciseResponseDto::class.java)
                //운동카드 수정 api 필요
//                stepperViewModel.putEditExerciseCard(toDayExerciseResponseDto.stepList[0].myExercise.exerciseId,
//                    ExerciseCardRequestDto(
//                        bodyPart = bodyPart,
//                        date =
//                    )
//                )
                findNavController().navigateUp()
            } else {
                if (bodyPart.isNotEmpty()) {
                    val action =
                        AddExerciseFragmentDirections.actionFragmentAddExerciseToFragmentExerciseSettingsDate()
                    findNavController().navigateSafe(action.actionId,Bundle().apply {
                        putString("bodyPart",bodyPart)
                    })
                } else {
                    Toast.makeText(requireContext(), "운동 부위를 선택해 주세요.", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun go1(us : UpdateState) {
        // 운동 부위 추가 1
        if (bodyPart.isNotEmpty()) {
            val action =
                AddExerciseFragmentDirections.actionFragmentAddExerciseToAddExerciseSelectScrapFragment2()

            findNavController().navigateSafe(
                action.actionId,
                Bundle().apply {
                    putInt("stepLevel", 1)
                    putString("bodyPart", bodyPart)
                    Log.d("로그",bodyPart)
                    putString("updateType",us.name)
                    if(cardListJson.isNotEmpty()){
                        putString("CardListJson", cardListJson)
                    }
                })
        } else {
            Toast.makeText(requireContext(), "운동 부위를 선택해 주세요.", Toast.LENGTH_SHORT).show()
        }

    }

    private fun go2(us : UpdateState) {
        // 운동 부위 추가 2
        if (bodyPart.isNotEmpty()) {
            val action =
                AddExerciseFragmentDirections.actionFragmentAddExerciseToAddExerciseSelectScrapFragment2()
            findNavController().navigateSafe(action.actionId, Bundle().apply {
                putInt("stepLevel", 2)
                putString("bodyPart", bodyPart)
                putString("updateType",us.name)
                if(cardListJson.isNotEmpty()){
                    putString("CardListJson", cardListJson)
                }
            })
        } else {
            Toast.makeText(requireContext(), "운동 부위를 선택해 주세요.", Toast.LENGTH_SHORT).show()
        }

    }

    private fun go3(us : UpdateState) {
        // 운동 부위 추가 3
        val action =
            AddExerciseFragmentDirections.actionFragmentAddExerciseToAddExerciseSelectScrapFragment2()
        findNavController().navigateSafe(action.actionId, Bundle().apply {
            putInt("stepLevel", 3)
            putString("bodyPart", bodyPart)
            putString("updateType",us.name)
            if(cardListJson.isNotEmpty()){
                putString("CardListJson", cardListJson)
            }
        })

    }

    private fun setList() {
        with(binding) {
            tagList = listOf(
                fragmentAddExerciseSelectTag1Tv,
                fragmentAddExerciseSelectTag2Tv,
                fragmentAddExerciseSelectTag3Tv,
                fragmentAddExerciseSelectTag4Tv,
                fragmentAddExerciseSelectTag5Tv,
                fragmentAddExerciseSelectTag6Tv,
                fragmentAddExerciseSelectTag7Tv,
                fragmentAddExerciseSelectTag8Tv,
                fragmentAddExerciseSelectTag9Tv
            )

            addItemList = listOf(
                fragmentAddExerciseAddStep1Constraint,
                fragmentAddExerciseAddStep2Constraint,
                fragmentAddExerciseAddStep3Constraint,
            )

            nonAddItemList = listOf(
                fragmentAddExerciseNoneAddStep1Constraint,
                fragmentAddExerciseNoneAddStep2Constraint,
                fragmentAddExerciseNoneAddStep3Constraint,
            )

            thumbnailList = listOf(
                fragmentAddExerciseThumbnail1Iv,
                fragmentAddExerciseThumbnail2Iv,
                fragmentAddExerciseThumbnail3Iv,
            )

            titleList = listOf(
                fragmentAddExerciseTitle1Tv,
                fragmentAddExerciseTitle2Tv,
                fragmentAddExerciseTitle3Tv
            )

            editBtnList = listOf(
                fragmentAddExerciseEdit1Iv,
                fragmentAddExerciseEdit2Iv,
                fragmentAddExerciseEdit3Iv
            )

            channelList = listOf(
                fragmentAddExerciseChannel1Tv,
                fragmentAddExerciseChannel2Tv,
                fragmentAddExerciseChannel3Tv
            )


            for (i in 0..8) {
                tagList[i].setOnClickListener {
                    setOnClickBtn(i)
                }
            }
        }
    }

    private fun setOnClickBtn(select: Int) {
        for (i in 0..8) {
            if (select == i) {
                initStep()
                clearExerciseCards() // 추가된 메서드 호출하여 카드 초기화
                selectNumber = i
                bodyPart = tagList[i].text.toString()
                tagList[i].setBackgroundResource(setColor(requireContext(), true).first)
                tagList[i].setTextColor(setColor(requireContext(), true).second)
            } else {
                tagList[i].setBackgroundResource(setColor(requireContext(), false).first)
                tagList[i].setTextColor(setColor(requireContext(), false).second)
            }
        }
    }

    private fun clearExerciseCards() {
        for (i in 0 until 3) {
            addItemList[i].visibility = View.GONE
            nonAddItemList[i].visibility = View.VISIBLE
        }
    }

    private fun stateSelect() {
        var part = arguments?.getString("bp") ?: ""
        when(part){
            "무릎다리" -> part = "무릎, 다리"
            "어깨팔" -> part = "어깨, 팔"
            else -> part
        }
        Log.d("부위",part)
        if (part.isNotEmpty()) {
            val select = tagList.find { it.text.toString() == part }
            if (select != null) {
                select.setBackgroundResource(setColor(requireContext(), true).first)
                select.setTextColor(setColor(requireContext(), true).second)
                bodyPart = part
            } else {
                Log.e("에러", "일치하는 운동 부위를 찾을 수 없습니다: $part")
            }
        } else {
            Log.d("정보", "선택된 운동 부위가 없습니다")
        }
    }

    private fun setColor(context: Context, selectState: Boolean): Pair<Int, Int> {
        val backGroundColor: Int
        val textColor: Int
        if (selectState) {
            backGroundColor = R.drawable.shape_rounded_square_yellow700_40dp
            textColor = ContextCompat.getColor(context, R.color.Purple_Black_BG_2)
        } else {
            backGroundColor = R.drawable.selector_exercise_card_tag
            textColor = ContextCompat.getColor(context, R.color.Yellow_700)
        }
        return Pair(backGroundColor, textColor)
    }


    private fun setMenuList() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                todayViewModel.setExerciseStep.collect { steps ->
                    Log.d("크기", steps.size.toString())
                    for (i in 0 until 3) {
                        if (i < steps.size) {
                            addItemList[i].visibility = View.VISIBLE
                            nonAddItemList[i].visibility = View.GONE
                            setMenu(steps[i], i + 1)
                        } else {
                            addItemList[i].visibility = View.GONE
                            nonAddItemList[i].visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    private fun initStep() {
        todayViewModel.clearStep()
    }

    private fun setMenu(youtubeCard: CheckExerciseResponse, count: Int) {
        val thumbnail = thumbnailList[count - 1]
        val title = titleList[count - 1]
        val channel = channelList[count - 1]
        val editBtn = editBtnList[count - 1]

        Log.d("로그",youtubeCard.url)
        GlobalApplication.loadCropRoundedSquareImage(
            requireContext(),
            thumbnail,
            youtubeCard.video_image,
            18
        )
        title.text = youtubeCard.video_title
        channel.text = youtubeCard.channel_name
        editBtn.setOnClickListener {
            goEditPage(count)
        }
    }

    private fun goEditPage(number: Int) {
        val updateState = UpdateState.UPDATE
        when(number){
            1 -> go1(updateState)
            2 -> go2(updateState)
            3 -> go3(updateState)
            else -> Log.e("error","error")
        }
    }
}

