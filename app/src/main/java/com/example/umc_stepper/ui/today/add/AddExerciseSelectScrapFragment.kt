package com.example.umc_stepper.ui.today.add

import android.content.Context
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentAddExerciseSelectScrapBinding
import com.example.umc_stepper.ui.MainActivity
import com.example.umc_stepper.ui.today.TodayViewModel
import kotlinx.coroutines.launch

class AddExerciseSelectScrapFragment: BaseFragment<FragmentAddExerciseSelectScrapBinding>(R.layout.fragment_add_exercise_select_scrap) {

    private lateinit var mainActivity : MainActivity
    private val todayViewModel: TodayViewModel by activityViewModels()
    private lateinit var selectScrapListAdapter: SelectScrapListAdapter
    private lateinit var selectScrapBodyPartAdapter: SelectScrapBodyPartAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun setLayout() {
        initSettings()
    }

    private fun initSettings() {
        updateMainToolbar()
        setButton()
        setAdapter()
        observeViewModel()

        // 앞 화면에서 운동 부위, 운동 단계 받아야 함
    }

    private fun observeViewModel() {

        val bodyPart = "머리"

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                todayViewModel.getMyExercise(bodyPart)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                todayViewModel.checkExerciseResponseDTO.collect {
                    selectScrapListAdapter.submitList(it.result)
                }
            }
        }
    }

    private fun setAdapter() {
        selectScrapBodyPartAdapter = SelectScrapBodyPartAdapter()
        binding.fragmentAddExerciseDownloadTagRv.adapter = selectScrapBodyPartAdapter
        selectScrapBodyPartAdapter.submitList(listOf("머리"))

        selectScrapListAdapter = SelectScrapListAdapter {
            Log.d("SelectedItem", "item : $it")
            binding.fragmentAddExerciseDownloadBtn.isEnabled = true
            binding.fragmentAddExerciseDownloadBtn.setBackgroundResource(R.drawable.shape_rounded_square_purple700_60dp)
            binding.fragmentAddExerciseDownloadBtn.setTextColor(
                ContextCompat.getColor(binding.root.context, R.color.White)
            )
        }
        binding.fragmentAddExerciseDownloadCardListRv.adapter = selectScrapListAdapter
    }

    private fun updateMainToolbar() {
        mainActivity.updateToolbarTitle("운동 카드를 작성해봐요!")
    }

    private fun setButton() {

        // 운동 설정 버튼 설정 -> 앞에서 운동 단계 받아야 함
        binding.fragmentAddExerciseDownloadBtn.setBackgroundResource(R.drawable.radius_corners_61dp_stroke_1)
        binding.fragmentAddExerciseDownloadBtn.setTextColor(
            ContextCompat.getColor(binding.root.context, R.color.Purple_700)
        )

        // 운동 카드 추가 화면으로 되돌아가기 action_addExerciseSelectScrapFragment_to_fragmentAddExercise2
        binding.fragmentAddExerciseDownloadBtn.setOnClickListener {
            val action = AddExerciseSelectScrapFragmentDirections.actionAddExerciseSelectScrapFragmentToFragmentAddExercise2()
            findNavController().navigateSafe(action.actionId)
        }
    }

}