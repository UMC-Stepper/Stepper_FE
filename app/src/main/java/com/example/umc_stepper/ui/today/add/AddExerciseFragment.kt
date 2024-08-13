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
import com.example.umc_stepper.domain.model.response.exercise_card_controller.ExerciseStepResponse
import com.example.umc_stepper.domain.model.response.my_exercise_controller.CheckExerciseResponse
import com.example.umc_stepper.ui.MainActivity
import com.example.umc_stepper.ui.today.TodayViewModel
import com.example.umc_stepper.utils.GlobalApplication
import com.example.umc_stepper.utils.enums.bodyPartType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    private fun setTitle() {
        mainActivity.updateToolbarTitle("운동 카드를 작성해봐요!")
    }

    override fun setLayout() {
        initSetting()
    }

    private fun initSetting() {
        setTitle()
        setMenuList()
        setList()
        setOnClick()
    }


    private fun setOnClick() {

        // 운동 부위 추가 1
        binding.fragmentAddExerciseNoneAddStep1Constraint.setOnClickListener {

            if (bodyPart.isNotEmpty()) {
                val action =
                    AddExerciseFragmentDirections.actionFragmentAddExerciseToAddExerciseSelectScrapFragment2()
                findNavController().navigateSafe(
                    action.actionId,
                    Bundle().apply {
                        putInt("stepLevel", 1)
                        putString("bodyPart", bodyPart)
                    })
            } else {
                Toast.makeText(requireContext(), "운동 부위를 선택해 주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        // 운동 부위 추가 2
        binding.fragmentAddExerciseNoneAddStep2Constraint.setOnClickListener {
            if (bodyPart.isNotEmpty()) {
                val action =
                    AddExerciseFragmentDirections.actionFragmentAddExerciseToAddExerciseSelectScrapFragment2()
                findNavController().navigateSafe(action.actionId, Bundle().apply {
                    putInt("stepLevel", 2)
                    putString("bodyPart", bodyPart)
                })
            } else {
                Toast.makeText(requireContext(), "운동 부위를 선택해 주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        // 운동 부위 추가 3
        binding.fragmentAddExerciseNoneAddStep3Constraint.setOnClickListener {
            val action =
                AddExerciseFragmentDirections.actionFragmentAddExerciseToAddExerciseSelectScrapFragment2()
            findNavController().navigateSafe(action.actionId, Bundle().apply {
                putInt("stepLevel", 3)
                putString("bodyPart", bodyPart)
            })
        }

        // 다음으로 버튼 클릭
        binding.fragmentAddExerciseNextBtn.setOnClickListener {
            if (bodyPart.isNotEmpty()) {
                val action =
                    AddExerciseFragmentDirections.actionFragmentAddExerciseToFragmentExerciseSettingsDate()
                findNavController().navigateSafe(action.actionId)
            } else {
                Toast.makeText(requireContext(), "운동 부위를 선택해 주세요.", Toast.LENGTH_SHORT).show()
            }
        }

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
                todayViewModel.setExerciseStep.collect {
                    Log.d("크기", it.size.toString())
                    if (it.isNotEmpty()) {
                        for (i in 1..it.size) {
                            addItemList[i - 1].visibility = View.VISIBLE
                            nonAddItemList[i - 1].visibility = View.GONE
                            setMenu(it[i - 1], i)
                        }
                    }
                }
            }
        }
    }

    private fun setMenu(youtubeCard: CheckExerciseResponse, count: Int) {

        when (count) {
            1 -> {
                GlobalApplication.loadCropRoundedSquareImage(
                    this@AddExerciseFragment.requireContext(),
                    binding.fragmentAddExerciseThumbnail1Iv,
                    youtubeCard.url,
                    18
                )
                binding.fragmentAddExerciseTitle1Tv.text = youtubeCard.video_title
                binding.fragmentAddExerciseChannel1Tv.text = youtubeCard.channel_name
                binding.fragmentAddExerciseEdit1Iv.setOnClickListener {
                    goEditPage(1)
                }
            }

            2 -> {
                GlobalApplication.loadCropRoundedSquareImage(
                    this@AddExerciseFragment.requireContext(),
                    binding.fragmentAddExerciseThumbnail2Iv,
                    youtubeCard.url,
                    18
                )
                binding.fragmentAddExerciseTitle2Tv.text = youtubeCard.video_title
                binding.fragmentAddExerciseChannel2Tv.text = youtubeCard.channel_name
                binding.fragmentAddExerciseEdit2Iv.setOnClickListener {
                    goEditPage(2)
                }
            }

            3 -> {
                GlobalApplication.loadCropRoundedSquareImage(
                    this@AddExerciseFragment.requireContext(),
                    binding.fragmentAddExerciseThumbnail3Iv,
                    youtubeCard.url,
                    18
                )
                binding.fragmentAddExerciseTitle3Tv.text = youtubeCard.video_title
                binding.fragmentAddExerciseChannel3Tv.text = youtubeCard.video_title
                binding.fragmentAddExerciseEdit3Iv.setOnClickListener {
                    goEditPage(3)
                }
            }
        }
    }

    private fun goEditPage(number: Int) {
        val bundle = Bundle().apply {
            putInt("count", number)
        }
        val action =
            AddExerciseFragmentDirections.actionFragmentAddExerciseToFragmentExerciseSettingsDate()
        findNavController().navigateSafe(action.actionId, bundle)
    }


}

