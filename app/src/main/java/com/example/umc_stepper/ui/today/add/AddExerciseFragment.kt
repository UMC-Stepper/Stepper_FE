package com.example.umc_stepper.ui.today.add

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentAddExerciseBinding
import com.example.umc_stepper.ui.MainActivity
import com.example.umc_stepper.utils.GlobalApplication
import kotlin.properties.Delegates

class AddExerciseFragment :
    BaseFragment<FragmentAddExerciseBinding>(R.layout.fragment_add_exercise) {

    private lateinit var tagList: List<TextView>
    private lateinit var addItemList: List<ConstraintLayout>
    private lateinit var nonAddItemList: List<ConstraintLayout>
    private lateinit var thumbnailList: List<ImageView>
    private lateinit var titleList: List<TextView>
    private lateinit var editBtnList: List<Button>
    private lateinit var channelList: List<TextView>
    private val youtubeCardList : ArrayList<YoutubeCard> = arrayListOf(
        YoutubeCard("","진정재활운동","서울아산병원 이비인후과"),
        YoutubeCard("","진정재활운동","서울아산병원 이비인후과"),
        YoutubeCard("","진정재활운동","서울아산병원 이비인후과")
    )

    private var menuCount by Delegates.notNull<Int>()
    private lateinit var mainActivity : MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
    private fun setTitle(){
        mainActivity.updateToolbarTitle("운동 카드를 작성해봐요!")
    }

    override fun setLayout() {
        initSetting()
    }

    private fun initSetting() {
        setTitle()
        setList()
        setMenuList()
        setOnClick()
    }


    private fun setOnClick() {

        // 운동 부위 추가 1
        binding.fragmentAddExerciseNoneAddStep1Constraint.setOnClickListener {
            val action = AddExerciseFragmentDirections.actionFragmentAddExerciseToAddExerciseSelectScrapFragment2()
            findNavController().navigateSafe(action.actionId)
        }

        // 운동 부위 추가 2
        binding.fragmentAddExerciseNoneAddStep2Constraint.setOnClickListener {
            val action = AddExerciseFragmentDirections.actionFragmentAddExerciseToAddExerciseSelectScrapFragment2()
            findNavController().navigateSafe(action.actionId)
        }

        // 운동 부위 추가 3
        binding.fragmentAddExerciseNoneAddStep3Constraint.setOnClickListener {
            val action = AddExerciseFragmentDirections.actionFragmentAddExerciseToAddExerciseSelectScrapFragment2()
            findNavController().navigateSafe(action.actionId)
        }

        // 다음으로 버튼 클릭
        binding.fragmentAddExerciseNextBtn.setOnClickListener {
            val action = AddExerciseFragmentDirections.actionFragmentAddExerciseToFragmentExerciseSettingsDate()
            findNavController().navigateSafe(action.actionId)
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
                fragmentAddExerciseAddStep1Constraint,
                fragmentAddExerciseAddStep1Constraint,
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

    private fun setOnClickBtn(select : Int) {
        for (i in 0..8) {
            if (select == i) {
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
        menuCount = youtubeCardList.size
        for (i in 0..menuCount) {
            addItemList[i].visibility = View.VISIBLE
            nonAddItemList[i].visibility = View.GONE
            setMenu(youtubeCardList[i],i)
        }
    }

    private fun setMenu(youtubeCard: YoutubeCard , count : Int) {

        when(count) {
            1 -> {
                GlobalApplication.loadCropRoundedSquareImage(
                    this@AddExerciseFragment.requireContext(),
                    binding.fragmentAddExerciseThumbnail1Iv,
                    "",
                    18
                )
                binding.fragmentAddExerciseTitle1Tv.text = youtubeCard.title
                binding.fragmentAddExerciseChannel1Tv.text = youtubeCard.description
                binding.fragmentAddExerciseEdit1Iv.setOnClickListener {
                    goEditPage(1)
                }
            }
            2 -> {
                GlobalApplication.loadCropRoundedSquareImage(
                    this@AddExerciseFragment.requireContext(),
                    binding.fragmentAddExerciseThumbnail2Iv,
                    "",
                    18
                )
                binding.fragmentAddExerciseTitle2Tv.text = youtubeCard.title
                binding.fragmentAddExerciseChannel2Tv.text = youtubeCard.description
                binding.fragmentAddExerciseEdit2Iv.setOnClickListener {
                    goEditPage(2)
                }
            }
            3 -> {
                GlobalApplication.loadCropRoundedSquareImage(
                    this@AddExerciseFragment.requireContext(),
                    binding.fragmentAddExerciseThumbnail3Iv,
                    "",
                    18
                )
                binding.fragmentAddExerciseTitle3Tv.text = youtubeCard.title
                binding.fragmentAddExerciseChannel3Tv.text = youtubeCard.description
                binding.fragmentAddExerciseEdit3Iv.setOnClickListener {
                    goEditPage(3)
                }
            }
        }
    }

    private fun goEditPage(number : Int) {
        val bundle = Bundle().apply {
            putInt("count", number)
        }
        val action = AddExerciseFragmentDirections.actionFragmentAddExerciseToFragmentExerciseSettingsDate()
        findNavController().navigateSafe(action.actionId,
            bundle
        )
    }


}

data class YoutubeCard(
    val thumbnail: String,
    val title: String,
    val description : String,
)