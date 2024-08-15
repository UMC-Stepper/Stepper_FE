package com.example.umc_stepper.ui.stepper.additional

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentAddExerciseSelectScrapBinding
import com.example.umc_stepper.ui.MainActivity
import com.example.umc_stepper.ui.today.TodayViewModel
import com.example.umc_stepper.ui.today.add.SelectScrapListAdapter
import kotlinx.coroutines.launch

class AddExerciseDownloadFragment : BaseFragment<FragmentAddExerciseSelectScrapBinding>(R.layout.fragment_add_exercise_select_scrap), CategoryAdapter.OnCategoryClickListener{

    private lateinit var mainActivity : MainActivity
    private lateinit var categoryAdapter: CategoryAdapter
    private val todayViewModel: TodayViewModel by activityViewModels()
    private lateinit var selectScrapListAdapter: SelectScrapListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Adapter 초기화
        selectScrapListAdapter = SelectScrapListAdapter {
            Log.d("SelectedItem", "item : $it")
            binding.fragmentAddExerciseDownloadBtn.isEnabled = true
            binding.fragmentAddExerciseDownloadBtn.setBackgroundResource(R.drawable.shape_rounded_square_purple700_60dp)
            binding.fragmentAddExerciseDownloadBtn.setTextColor(
                ContextCompat.getColor(binding.root.context, R.color.White)
            )
        }
        binding.fragmentAddExerciseDownloadCardListRv.adapter = selectScrapListAdapter
        categoryAdapter = CategoryAdapter(requireContext(),this)

        super.onViewCreated(view, savedInstanceState)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun setLayout() {
        updateMainToolbar()

        val categories = listOf("머리", "어깨, 팔", "가슴", "복부", "골반", "무릎, 다리", "등", "허리", "발")

        categoryAdapter.submitList(categories)
        binding.fragmentAddExerciseDownloadTagRv.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
        }

        binding.fragmentAddExerciseDownloadCardListRv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = selectScrapListAdapter
        }

        binding.fragmentAddExerciseDownloadBtn.setOnClickListener {
            goLastExercise()

        }
    }

    private fun goLastExercise(){
        val args = Bundle().apply {
            putString("urlText", selectScrapListAdapter.getSelectExercise())
            Log.d("링크",selectScrapListAdapter.getSelectExercise())
        }

        findNavController().navigate(R.id.action_fragmentAddExerciseDownload_to_fragmentLastExercise,args)
    }

    override fun onCategoryClick(category: String) {
        loadExercises(category)
    }

    private fun loadExercises(category: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                todayViewModel.getMyExercise(category)
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

    private fun updateMainToolbar() {
        mainActivity.updateToolbarTitle("추가 운동하기")
    }
}
