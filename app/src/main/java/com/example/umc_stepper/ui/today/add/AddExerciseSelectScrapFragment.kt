package com.example.umc_stepper.ui.today.add

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
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
import com.example.umc_stepper.utils.enums.UpdateState
import com.google.gson.Gson
import kotlinx.coroutines.launch

class AddExerciseSelectScrapFragment :
    BaseFragment<FragmentAddExerciseSelectScrapBinding>(R.layout.fragment_add_exercise_select_scrap) {
    private var cardListJson = ""

    private lateinit var mainActivity: MainActivity
    private lateinit var selectScrapListAdapter: SelectScrapListAdapter
    private lateinit var selectScrapBodyPartAdapter: SelectScrapBodyPartAdapter
    private var stepLevel: Int = 0
    private var bodyPart: String = ""
    private lateinit var args: Bundle
    private lateinit var cer: CheckExerciseResponse
    private val todayViewModel: TodayViewModel by activityViewModels()
    private var exerciseId : Int = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun setLayout() {
        stepLevel = arguments?.getInt("stepLevel")!!
        bodyPart = arguments?.getString("bodyPart").toString()
        bodyPart = when (bodyPart) {
            "무릎, 다리" -> "무릎다리"
            "어깨, 팔" -> "어깨팔"
            else -> {
                bodyPart
            }
        }
        Log.d("로그",bodyPart)
        initSettings()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cardListJson = arguments?.getString("CardListJson").orEmpty()
        Log.d("카드도착", cardListJson)
    }
    private fun initSettings() {
        updateMainToolbar()
        setButton()
        observeViewModel()
        setAdapter()
        popBack()
    }

    private fun observeViewModel() {

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
        selectScrapBodyPartAdapter.submitList(listOf(bodyPart))

        // 스크랩 리스트중 아이템 한 개 클릭하면 버튼 활성화
        selectScrapListAdapter = SelectScrapListAdapter {
            setDataGson(it)
            cer = it
            exerciseId = cer.exerciseId
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

    private fun popBack() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    val action =
                        AddExerciseSelectScrapFragmentDirections.actionAddExerciseSelectScrapFragmentToFragmentAddExercise2()

                    findNavController().navigateSafe(action.actionId, Bundle().apply {
                        putString("bp", bodyPart)
                    })
                }
            })

    }

    private fun updateMainToolbar() {
        mainActivity.updateToolbarTitle("운동 카드를 작성해봐요!")
        mainActivity.updateToolbarLeftImg(R.drawable.ic_back)
        mainActivity.updateToolbarMiddleImg(R.drawable.ic_toolbar_today)
        mainActivity.updateToolbarRightImg(R.drawable.ic_toolbar_stepper)
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
            val action =
                AddExerciseSelectScrapFragmentDirections.actionAddExerciseSelectScrapFragmentToFragmentAddExercise2()

            val state = arguments?.getString("updateType")?.let { it1 -> UpdateState.valueOf(it1) }
            val pos = arguments?.getInt("stepLevel")
            if(pos != null) {
                when (state) {
                    UpdateState.ADD -> {
                        todayViewModel.addStep(cer)
                        todayViewModel.addExerciseList(cer.exerciseId)
                        Log.d("크기add",todayViewModel.getExerciseListSize().toString())
                    }

                    UpdateState.UPDATE -> {
                        todayViewModel.updateStep(cer, pos)
                        todayViewModel.updateExerciseList(cer.exerciseId, pos - 1)
                        Log.d("크기update",todayViewModel.getExerciseListSize().toString())

                    }
                    else -> {
                        Log.e("error", "error")
                    }
                }
            }
            else{

            }
            findNavController().navigateSafe(action.actionId, Bundle().apply {
                putString("bp", bodyPart)
                putInt("step", stepLevel)
                if(cardListJson.isNotEmpty()){
                    putString("CardListJson", cardListJson)
                }
            })
        }
    }

}