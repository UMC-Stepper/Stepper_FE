package com.example.umc_stepper.ui.stepper

import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseAdapter
import com.example.umc_stepper.base.BaseDiffCallback
import com.example.umc_stepper.base.BaseListResponse
import com.example.umc_stepper.databinding.ItemStepperTodayExerciseRecyclerBinding
import com.example.umc_stepper.domain.model.response.exercise_card_controller.ExerciseStepResponse
import com.example.umc_stepper.domain.model.response.exercise_card_controller.ToDayExerciseResponseDto
import com.example.umc_stepper.utils.listener.AdapterNextClick
import com.example.umc_stepper.utils.listener.ItemClickListener

class ExerciseViewAdapter(
    private val listener: ItemClickListener,
    private val listener2 : AdapterNextClick
) : BaseAdapter<ToDayExerciseResponseDto, ItemStepperTodayExerciseRecyclerBinding>(
    BaseDiffCallback(
        itemsTheSame = { oldItem, newItem -> oldItem == newItem },
        contentsTheSame = { oldItem, newItem -> oldItem == newItem }
    )
) {
    private lateinit var btList : List<ImageButton>
    lateinit var unBtList : List<ImageButton>
    override val layoutId: Int
        get() = R.layout.item_stepper_today_exercise_recycler

    override fun bind(
        binding: ItemStepperTodayExerciseRecyclerBinding,
        item: ToDayExerciseResponseDto
    ) {
        btList = listOf(binding.bt1, binding.bt2, binding.bt3)
        unBtList = listOf(binding.unBt1, binding.unBt2, binding.unBt3)
        Log.d("토큰 ",item.stepList.size.toString() + "사이즈")
        when(item.stepList.size) {
            0 -> {
                binding.cl1.visibility = View.GONE
                binding.cl2.visibility = View.GONE
                binding.cl3.visibility = View.GONE
                item.stepList = listOf(ExerciseStepResponse(), ExerciseStepResponse(), ExerciseStepResponse())
            }
            1 -> {
                binding.cl1.visibility = View.VISIBLE
                binding.cl2.visibility = View.GONE
                binding.cl3.visibility = View.GONE
                for(i in 0..<item.stepList.size){
                    if(item.stepList[i].step_status){
                        btList[i].visibility = View.GONE
                        unBtList[i].visibility = View.VISIBLE
                    }
                }
                item.stepList += listOf(ExerciseStepResponse(), ExerciseStepResponse())
            }
            2 -> {
                binding.cl1.visibility = View.VISIBLE
                binding.cl2.visibility = View.VISIBLE
                binding.cl3.visibility = View.GONE
                for(i in 0..<item.stepList.size){
                    if(item.stepList[i].step_status){
                        btList[i].visibility = View.GONE
                        unBtList[i].visibility = View.VISIBLE
                    }
                }
                item.stepList += listOf(ExerciseStepResponse())
            }
            else -> {
                binding.cl1.visibility = View.VISIBLE
                binding.cl2.visibility = View.VISIBLE
                binding.cl3.visibility = View.VISIBLE
                for(i in 0..<item.stepList.size){
                    if(item.stepList[i].step_status){
                        Log.d("토큰",item.stepList[i].step_status.toString())
                        btList[i].visibility = View.GONE
                        unBtList[i].visibility = View.VISIBLE
                    }
                }
            }
        }
        binding.stepList = item
        binding.pick = listener
        binding.pick2 = listener2
    }

}

