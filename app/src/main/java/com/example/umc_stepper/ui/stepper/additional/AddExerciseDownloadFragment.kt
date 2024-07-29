package com.example.umc_stepper.ui.stepper.additional

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentAddExerciseDownloadBinding
import com.example.umc_stepper.domain.model.request.ExerciseDto

class AddExerciseDownloadFragment : BaseFragment<FragmentAddExerciseDownloadBinding>(R.layout.fragment_add_exercise_download),CategoryAdapter.OnCategoryClickListener,
    ExerciseAdapter.OnExerciseClickListener {
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var exerciseAdapter: ExerciseAdapter
    private val exerciseList = mutableListOf<ExerciseDto>()

    override fun setLayout() {
        val categories = listOf("머리", "어깨, 팔", "가슴", "복부", "골반", "무릎, 다리", "등", "허리", "발")
        val categoryAdapter = CategoryAdapter(this)
        categoryAdapter.submitList(categories)
        binding.fragmentAddExerciseDownloadTagRv.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
        }

        val exerciseAdapter = ExerciseAdapter(this)
        binding.fragmentAddExerciseDownloadCardListRv.apply {
           layoutManager = LinearLayoutManager(requireContext())
           adapter = exerciseAdapter
        }

        loadExercises("어깨, 팔")
    }

    override fun onCategoryClick(category: String) {
       loadExercises(category)
    }

    private fun loadExercises(category: String) {
        exerciseList.clear()
        // Dummy data
        exerciseList.add(ExerciseDto("전쟁재활운동", "서울아산병원", "url_to_image"))
        exerciseList.add(ExerciseDto("전쟁재활운동", "서울아산병원", "url_to_image"))
        exerciseList.add(ExerciseDto("전쟁재활운동", "서울아산병원", "url_to_image"))
        exerciseAdapter.submitList(exerciseList.toList())
    }

    override fun onExerciseClick(exercise: ExerciseDto) {

    }
}