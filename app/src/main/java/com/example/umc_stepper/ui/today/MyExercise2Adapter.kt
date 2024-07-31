package com.example.umc_stepper.ui.today

import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseAdapter
import com.example.umc_stepper.base.BaseDiffCallback
import com.example.umc_stepper.databinding.ItemCollocateMyExerciseBinding
import com.example.umc_stepper.domain.model.Exercise2Data
import com.example.umc_stepper.utils.listener.ItemClickListener
import dagger.hilt.android.AndroidEntryPoint

class MyExercise2Adapter(
    val listener: ItemClickListener
)  : BaseAdapter<Exercise2Data, ItemCollocateMyExerciseBinding>(
    BaseDiffCallback(
        itemsTheSame = { oldItem, newItem -> oldItem == newItem },
        contentsTheSame = { oldItem, newItem -> oldItem == newItem }
    )
){

    override val layoutId: Int
        get() = R.layout.item_collocate_my_exercise

    override fun bind(binding: ItemCollocateMyExerciseBinding, item: Exercise2Data) {
        binding.listItem = item
        binding.listener = listener
    }


}
