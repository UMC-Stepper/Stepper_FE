package com.example.umc_stepper.ui.today

import android.content.Context
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentMyExercise2Binding
import com.example.umc_stepper.domain.model.Exercise2Data
import com.example.umc_stepper.domain.model.ExerciseTagData
import com.example.umc_stepper.ui.MainActivity
import com.example.umc_stepper.utils.extensions.navigateSafe
import com.example.umc_stepper.utils.listener.ItemClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyExercise2Fragment :
    BaseFragment<FragmentMyExercise2Binding>(R.layout.fragment_my_exercise_2), ItemClickListener {

    private val todayViewModel: TodayViewModel by activityViewModels()
    private lateinit var tagAdapter: TageAdapter
    private lateinit var itemAdapter: MyExercise2Adapter
    private lateinit var mainActivity : MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    private fun setTitle(){
        mainActivity.updateToolbarTitle("나만의 운동 영상 저장하기") //타이틀 세팅
    }

    override fun setLayout() {
        setTitle()
        initSetting()
        setButton()
    }

    private fun initSetting() {
        initAdapter()
        loadSampleData()
    }

    private fun initAdapter() {
        tagAdapter = TageAdapter()
        itemAdapter = MyExercise2Adapter(this)

        binding.fragmentMyExercise2TagRv.adapter = tagAdapter
        binding.fragmentMyExercise2YoutubeCardRv.adapter = itemAdapter
    }

    private fun setButton() {
        // 유튜브에서 불러오기 버튼 클릭
        binding.fragmentMyExercise2YoutubeBtn.setOnClickListener {
            val action = MyExercise2FragmentDirections.actionFragmentMyExercise2ToFragmentMyExercise3()
            findNavController().navigateSafe(action.actionId)
        }
    }

    override fun onClick(item: Any) {
        val action = MyExercise2FragmentDirections.actionFragmentMyExercise2ToFragmentMyExercise3()
        if (item is Exercise2Data) {
            findNavController().navigateSafe(action.actionId)
        }
    }
    private fun loadSampleData() {
        val tagData = listOf(
            ExerciseTagData(1, "Yoga", R.color.Yellow_700, R.drawable.shape_rounded_stroke_square_yellow700_40dp_1dp),
            ExerciseTagData(2, "Pilates", R.color.Yellow_700, R.drawable.shape_rounded_stroke_square_yellow700_40dp_1dp)
        )
        tagAdapter.submitList(tagData)

        val exerciseData = listOf(
            Exercise2Data("thumbnail_url_1", "Yoga for Beginners", "Yoga Channel", 1, R.color.White, R.color.Purple_Black_BG_2),
            Exercise2Data("thumbnail_url_2", "Advanced Pilates", "Pilates Channel", 2, R.color.White, R.color.Purple_Black_BG_2)
        )
        itemAdapter.submitList(exerciseData)
    }

}
