package com.example.umc_stepper.ui.stepper

import androidx.recyclerview.widget.RecyclerView
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseAdapter
import com.example.umc_stepper.base.BaseDiffCallback
import com.example.umc_stepper.databinding.ItemStepperTodayExerciseRecyclerBinding

class ExerciseViewAdapter : BaseAdapter<LevelListItem, ItemStepperTodayExerciseRecyclerBinding>(
    BaseDiffCallback(
        itemsTheSame = { oldItem, newItem -> oldItem.levelList == newItem.levelList },
        contentsTheSame = { oldItem, newItem -> oldItem == newItem }
    )
) {
    override val layoutId: Int
        get() = R.layout.item_stepper_today_exercise_recycler

    override fun bind(binding: ItemStepperTodayExerciseRecyclerBinding, item: LevelListItem) {
        binding.levelItem = item
    }
}

data class LevelListItem(
    val levelList : List<LevelItem> = emptyList()
)
data class LevelItem(
    val imgView: String="",
    val level: String=""
)