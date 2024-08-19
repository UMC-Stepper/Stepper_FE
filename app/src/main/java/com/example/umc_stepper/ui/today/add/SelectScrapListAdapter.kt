package com.example.umc_stepper.ui.today.add

import android.util.Log
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseAdapter
import com.example.umc_stepper.base.BaseDiffCallback
import com.example.umc_stepper.databinding.ItemSelectMyExerciseBinding
import com.example.umc_stepper.domain.model.response.my_exercise_controller.CheckExerciseResponse

class SelectScrapListAdapter(private val onItemClick: (CheckExerciseResponse) -> Unit) :
    BaseAdapter<CheckExerciseResponse, ItemSelectMyExerciseBinding>(
        diffCallback = BaseDiffCallback(
        itemsTheSame = { oldItem, newItem -> oldItem.exerciseId == newItem.exerciseId },
        contentsTheSame = { oldItem, newItem -> oldItem == newItem }
    )
) {

    override val layoutId: Int
        get() = R.layout.item_select_my_exercise

    private var selectedItem: CheckExerciseResponse? = null

    override fun bind(binding: ItemSelectMyExerciseBinding, item: CheckExerciseResponse) {
        binding.checkExerciseResponse = item
        //Log.d("SelectScrapListAdapter", "item : $item" )

        if (item == selectedItem) {
            binding.itemSelectMyExerciseCheckedBt.setImageResource(R.drawable.selector_checked_on_white)
            binding.itemSelectMyExerciseBackground.setBackgroundResource(R.drawable.shape_rounded_square_purple700_18dp)
        } else {
            binding.itemSelectMyExerciseCheckedBt.setImageResource(R.drawable.selector_checked_off_gray_purple)
            binding.itemSelectMyExerciseBackground.setBackgroundResource(R.drawable.shape_rounded_square_18dp_exercise_yuotube_card_on)
        }


        binding.root.setOnClickListener {
            val previousSelectedItem = selectedItem
            selectedItem = if (item == selectedItem) {
                null
            } else {
                item
            }

            // 이전에 선택된 아이템 UI 갱신
            previousSelectedItem?.let {
                val previousIndex = currentList.indexOf(it)
                if (previousIndex != -1) {
                    notifyItemChanged(previousIndex)
                }
            }

            // 새로 선택된 아이템 UI 갱신
            val newIndex = currentList.indexOf(item)
            if (newIndex != -1) {
                notifyItemChanged(newIndex)
            }

            selectedItem?.let { onItemClick(it) }
        }

    }
    fun getSelectExercise():String = selectedItem?.url ?: ""

}