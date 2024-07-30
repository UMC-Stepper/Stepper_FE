package com.example.umc_stepper.ui.stepper.additional

import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseAdapter
import com.example.umc_stepper.base.BaseDiffCallback
import com.example.umc_stepper.databinding.ItemSelectMyExerciseBinding
import com.example.umc_stepper.domain.model.request.ExerciseDto

class ExerciseAdapter(
    private val listener: OnExerciseClickListener
) : BaseAdapter<ExerciseDto, ItemSelectMyExerciseBinding>(
    ExerciseDiffCallback()
) {

    interface OnExerciseClickListener {
        fun onExerciseClick(exercise: ExerciseDto)
    }

    override val layoutId: Int
        get() = R.layout.item_select_my_exercise

    override fun bind(binding: ItemSelectMyExerciseBinding, item: ExerciseDto) {
        binding.itemSelectMyExerciseTitleTv.text = item.name
        binding.itemSelectMyExerciseCheckedBt.setImageResource(R.drawable.selector_checked_off_gray_purple) // 실제 체크 상태 로직으로 대체해야 합니다.
        binding.itemSelectMyExerciseThumbnailIv.setBackgroundResource(R.drawable.ic_dummy_thumnail)
        binding.root.setOnClickListener {
            listener.onExerciseClick(item)
            binding.itemSelectMyExerciseCheckedBt.setImageResource(R.drawable.selector_checked_on_white)
            binding.itemSelectMyExerciseBackground.setBackgroundResource(R.drawable.shape_rounded_square_purple700_18dp)
        }
    }

    class ExerciseDiffCallback : BaseDiffCallback<ExerciseDto>(
        areItemsTheSame = { oldItem, newItem -> oldItem == newItem },
        areContentsTheSame = { oldItem, newItem -> oldItem == newItem }
    )
}
