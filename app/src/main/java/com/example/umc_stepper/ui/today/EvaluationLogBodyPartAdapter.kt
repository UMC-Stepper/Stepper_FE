package com.example.umc_stepper.ui.today

import android.content.res.ColorStateList
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseAdapter
import com.example.umc_stepper.base.BaseDiffCallback
import com.example.umc_stepper.base.BaseListResponse
import com.example.umc_stepper.databinding.ItemEvaluationLogCalenderCategoryBinding
import com.example.umc_stepper.domain.model.response.ExerciseCardStatusResponseDto
import com.example.umc_stepper.utils.listener.ItemClickListener
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import java.util.Locale

class EvaluationLogBodyPartAdapter(private val onItemClick: (ExerciseCardStatusResponseDto) -> Unit) : BaseAdapter<ExerciseCardStatusResponseDto, ItemEvaluationLogCalenderCategoryBinding>(
    diffCallback = BaseDiffCallback (
        itemsTheSame = { oldItem, newItem -> oldItem == newItem },
        contentsTheSame = { oldItem, newItem -> oldItem == newItem }
    )
) {

    override val layoutId: Int
        get() = R.layout.item_evaluation_log_calender_category

    override fun bind(binding: ItemEvaluationLogCalenderCategoryBinding, item: ExerciseCardStatusResponseDto) {
        binding.exerciseCardStatusResponseDto = item

        // 클릭 시 아이템 반환
        binding.root.setOnClickListener {
            onItemClick(item)
        }
    }


}