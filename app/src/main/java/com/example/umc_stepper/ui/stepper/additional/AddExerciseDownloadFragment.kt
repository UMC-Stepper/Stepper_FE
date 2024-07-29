package com.example.umc_stepper.ui.stepper.additional

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentAddExerciseDownloadBinding
import com.example.umc_stepper.domain.model.request.ExerciseDto

class AddExerciseDownloadFragment : BaseFragment<FragmentAddExerciseDownloadBinding>(R.layout.fragment_add_exercise_download), CategoryAdapter.OnCategoryClickListener, ExerciseAdapter.OnExerciseClickListener {

    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var exerciseAdapter: ExerciseAdapter
    private val exerciseList = mutableListOf<ExerciseDto>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // ExerciseAdapter 초기화
        exerciseAdapter = ExerciseAdapter(this)
        categoryAdapter = CategoryAdapter(requireContext(),this)

        super.onViewCreated(view, savedInstanceState)
    }

    override fun setLayout() {
        val categories = listOf("머리", "어깨, 팔", "가슴", "복부", "골반", "무릎, 다리", "등", "허리", "발")

        categoryAdapter.submitList(categories)
        binding.fragmentAddExerciseDownloadTagRv.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
        }

        binding.fragmentAddExerciseDownloadCardListRv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = exerciseAdapter
        }

        // 기본 운동 목록 로드
        loadExercises("어깨, 팔")
    }

    override fun onCategoryClick(category: String) {
        //loadExercises(category)
    }

    private fun loadExercises(category: String) {
        exerciseList.clear()
        exerciseList.add(ExerciseDto("전쟁재활운동", "서울아산병원", "url_to_image"))
        exerciseList.add(ExerciseDto("전쟁재활운동", "서울아산병원", "url_to_image"))
        exerciseList.add(ExerciseDto("전쟁재활운동", "서울아산병원", "url_to_image"))
        exerciseList.add(ExerciseDto("전쟁재활운동", "서울아산병원", "url_to_image"))
        exerciseAdapter.submitList(exerciseList.toList())
    }

    override fun onExerciseClick(exercise: ExerciseDto) {
        binding.fragmentAddExerciseDownloadBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.Purple_Black_BG_2))
        binding.fragmentAddExerciseDownloadBtn.setBackgroundResource(R.drawable.shape_rounded_square_purple700_60dp)
        binding.fragmentAddExerciseDownloadBtn.isEnabled=true
    }
}
