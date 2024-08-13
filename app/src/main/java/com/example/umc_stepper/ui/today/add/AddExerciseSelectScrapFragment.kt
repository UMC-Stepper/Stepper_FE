package com.example.umc_stepper.ui.today.add

import android.content.Context
import android.os.Bundle
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
import com.example.umc_stepper.domain.model.response.exercise_card_controller.ExerciseStepResponse
import com.example.umc_stepper.domain.model.response.my_exercise_controller.CheckExerciseResponse
import com.example.umc_stepper.ui.MainActivity
import com.example.umc_stepper.ui.today.TodayViewModel
import com.google.gson.Gson
import kotlinx.coroutines.launch

class AddExerciseSelectScrapFragment :
    BaseFragment<FragmentAddExerciseSelectScrapBinding>(R.layout.fragment_add_exercise_select_scrap) {

    private lateinit var mainActivity: MainActivity
    private lateinit var selectScrapListAdapter: SelectScrapListAdapter
    private lateinit var selectScrapBodyPartAdapter: SelectScrapBodyPartAdapter
    private var stepLevel: Int = 0
    private var bodyPart: String = ""
    private lateinit var args: Bundle
    private lateinit var cer: CheckExerciseResponse
    private val todayViewModel: TodayViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun setLayout() {
        stepLevel = arguments?.getInt("stepLevel")!!
        bodyPart = arguments?.getString("bodyPart", "").toString()
        initSettings()
    }

    private fun initSettings() {
        updateMainToolbar()
        setButton()
        observeViewModel()
        setAdapter()

        // 앞 화면에서 운동 부위, 운동 단계 받아야 함
    }

    private fun observeViewModel() {

        val bodyParts: String = when (arguments?.getString("bodyPart", "").toString()) {
            "무릎, 다리" -> "무릎다리"
            "어깨, 팔" -> "어깨팔"
            else -> {
                "머리"
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                todayViewModel.getMyExercise(bodyParts)
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
        selectScrapBodyPartAdapter.submitList(listOf(bodyPart))

        // 스크랩 리스트중 아이템 한 개 클릭하면 버튼 활성화
        selectScrapListAdapter = SelectScrapListAdapter {
            setDataGson(it)
            cer = it
            binding.fragmentAddExerciseDownloadBtn.isEnabled = true
            binding.fragmentAddExerciseDownloadBtn.setBackgroundResource(R.drawable.shape_rounded_square_purple700_60dp)
            binding.fragmentAddExerciseDownloadBtn.setTextColor(
                ContextCompat.getColor(binding.root.context, R.color.White)
            )
        }
        binding.fragmentAddExerciseDownloadCardListRv.adapter = selectScrapListAdapter
    }

    // 다음 화면에 넘기기 위해 서버에서 받은 데이터 Gson으로 가공하는 함수
    private fun setDataGson(item: CheckExerciseResponse) {
        val gson = Gson()
        val checkExerciseResponseJson = gson.toJson(item)
        Log.d("크기", checkExerciseResponseJson)
        args = Bundle().apply {
            putString("CheckExerciseResponse", checkExerciseResponseJson)
        }
    }

    private fun updateMainToolbar() {
        mainActivity.updateToolbarTitle("운동 카드를 작성해봐요!")
    }

    private fun setButton() {
        // 운동 설정 버튼 설정 -> 앞에서 운동 단계 받아야 함
        binding.fragmentAddExerciseDownloadBtn.text = stepLevel.toString() + "단계 운동 설정하기"
        binding.fragmentAddExerciseDownloadBtn.setBackgroundResource(R.drawable.radius_corners_61dp_stroke_1)
        binding.fragmentAddExerciseDownloadBtn.setTextColor(
            ContextCompat.getColor(binding.root.context, R.color.Purple_700)
        )

        // 운동 카드 추가 화면으로 되돌아가기 , 서버에서 받은 정보 넘기기
        binding.fragmentAddExerciseDownloadBtn.setOnClickListener {
            val action = AddExerciseSelectScrapFragmentDirections.actionAddExerciseSelectScrapFragmentToFragmentAddExercise2()
            todayViewModel.addStep(cer)
            findNavController().navigateSafe(action.actionId)
        }
    }

}