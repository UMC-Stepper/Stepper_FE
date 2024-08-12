package com.example.umc_stepper.ui.today.my_exercise

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentMyExercise2Binding
import com.example.umc_stepper.domain.model.Exercise2Data
import com.example.umc_stepper.domain.model.ExerciseTagData
import com.example.umc_stepper.ui.MainActivity
import com.example.umc_stepper.ui.today.TodayViewModel
import com.example.umc_stepper.utils.enums.LoadState
import com.example.umc_stepper.utils.listener.ItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyExercise2Fragment :
    BaseFragment<FragmentMyExercise2Binding>(R.layout.fragment_my_exercise_2), ItemClickListener {

    private val todayViewModel: TodayViewModel by activityViewModels()
    private lateinit var tagAdapter: TageAdapter
    private lateinit var itemAdapter: MyExercise2Adapter
    private lateinit var mainActivity: MainActivity
    private val TAG = "Exercise2Fragment"
    var bodyPart = ""
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    private fun setTitle() {
        mainActivity.updateToolbarTitle("나만의 운동 영상 저장하기") //타이틀 세팅
    }

    override fun setLayout() {
        setTitle()
        initSetting()
        setButton()
    }

    private fun initSetting() {
        initAdapter()
        loadTagData()
        observeLifeCycle()
    }

    private fun initAdapter() {
        tagAdapter = TageAdapter(todayViewModel)
        itemAdapter = MyExercise2Adapter(this)

        binding.fragmentMyExercise2TagRv.adapter = tagAdapter
        binding.fragmentMyExercise2YoutubeCardRv.adapter = itemAdapter
    }

    private fun setButton() {
        // 유튜브에서 불러오기 버튼 클릭
        binding.fragmentMyExercise2YoutubeBtn.setOnClickListener {
            val action =
                MyExercise2FragmentDirections.actionFragmentMyExercise2ToFragmentMyExercise3()
            findNavController().navigateSafe(
                action.actionId,
                Bundle().apply {
                    putString("bodyPart", bodyPart)
                }
            )
        }
    }

    override fun onClick(item: Any) {
        val action = MyExercise2FragmentDirections.actionFragmentMyExercise2ToFragmentMyExercise3()
        if (item is Exercise2Data) {
            findNavController().navigateSafe(action.actionId)
        }
    }

    private fun observeLifeCycle() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    todayViewModel.checkExerciseResponseDTO.collectLatest {
                        if (it.isSuccess) {
                            binding.fragmentMyExercise2YoutubeCardRv.visibility = View.VISIBLE
                            binding.fragmentMyExercise2EmptyTv.visibility = View.GONE
                            when (it.loadState) {
                                LoadState.LOADING -> {
                                    showProgress()
                                }

                                LoadState.SUCCESS -> {
                                    hideProgress()
                                    itemAdapter.submitList(it.result)
                                }

                                LoadState.EMPTY -> Log.e(TAG, "API EMPTY")
                                LoadState.ERROR -> Log.e(TAG, "API ERROR")
                            }
                        } else {
                            binding.fragmentMyExercise2YoutubeCardRv.visibility = View.GONE
                            binding.fragmentMyExercise2EmptyTv.visibility = View.VISIBLE
                        }
                    }
                }
                launch {
                    todayViewModel.bodyPart.collectLatest {
                        bodyPart = it
                    }
                }
            }
        }
    }

    private fun loadTagData() {
        val tagData = listOf(
            ExerciseTagData(
                1,
                "머리",
                R.color.Yellow_700,
                R.drawable.shape_rounded_stroke_square_yellow700_40dp_1dp
            ),
            ExerciseTagData(
                2,
                "가슴",
                R.color.Yellow_700,
                R.drawable.shape_rounded_stroke_square_yellow700_40dp_1dp
            ),
            ExerciseTagData(
                3,
                "복부",
                R.color.Yellow_700,
                R.drawable.shape_rounded_stroke_square_yellow700_40dp_1dp
            ),
            ExerciseTagData(
                4,
                "골반",
                R.color.Yellow_700,
                R.drawable.shape_rounded_stroke_square_yellow700_40dp_1dp
            ),
            ExerciseTagData(
                5,
                "어깨, 팔",
                R.color.Yellow_700,
                R.drawable.shape_rounded_stroke_square_yellow700_40dp_1dp
            ),
            ExerciseTagData(
                6,
                "무릎, 다리",
                R.color.Yellow_700,
                R.drawable.shape_rounded_stroke_square_yellow700_40dp_1dp
            ),
            ExerciseTagData(
                7,
                "등",
                R.color.Yellow_700,
                R.drawable.shape_rounded_stroke_square_yellow700_40dp_1dp
            ),
            ExerciseTagData(
                8,
                "허리",
                R.color.Yellow_700,
                R.drawable.shape_rounded_stroke_square_yellow700_40dp_1dp
            ),
            ExerciseTagData(
                9,
                "발",
                R.color.Yellow_700,
                R.drawable.shape_rounded_stroke_square_yellow700_40dp_1dp
            )
        )
        tagAdapter.submitList(tagData)
    }

    private fun showProgress() {
        binding.fragmentMyExercise3ProgressbarPb.visibility = View.VISIBLE
        binding.fragmentMyExercise3ProgressbarTextTv.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        binding.fragmentMyExercise3ProgressbarPb.visibility = View.GONE
        binding.fragmentMyExercise3ProgressbarTextTv.visibility = View.GONE
    }
}
