package com.example.umc_stepper.ui.today.home

import android.content.res.ColorStateList
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseAdapter
import com.example.umc_stepper.base.BaseDiffCallback
import com.example.umc_stepper.databinding.ItemTodayHomeWeekCalendarBinding
import com.example.umc_stepper.domain.model.local.WeekCalendar
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import java.util.Locale

class TodayHomeCalendarAdapter(private val onItemClick: (WeekCalendar) -> Unit) :
    BaseAdapter<WeekCalendar, ItemTodayHomeWeekCalendarBinding>(
        BaseDiffCallback(
            itemsTheSame = { oldItem, newItem -> oldItem == newItem },
            contentsTheSame = { oldItem, newItem -> oldItem == newItem }
        )
    ) {
    override val layoutId: Int get() = R.layout.item_today_home_week_calendar

    override fun bind(binding: ItemTodayHomeWeekCalendarBinding, item: WeekCalendar) {
        binding.weekCalendarItem = item

        val today = LocalDate.now().format(DateTimeFormatter.ofPattern("dd").withLocale(Locale.forLanguageTag("ko")))
        val tintColor = when {
            item.date == today && item.firstConnect.equals("first") -> ContextCompat.getColor(binding.root.context, R.color.Purple_700)
            item.date == today && item.firstConnect.equals("second") -> ContextCompat.getColor(binding.root.context, R.color.Purple_700)
            item.isSelected -> ContextCompat.getColor(binding.root.context, R.color.Purple_700)
            else -> ContextCompat.getColor(binding.root.context, R.color.Purple_Black_BG_2)
        }
        binding.itemTodayHomeWeekCalendarConstraint.backgroundTintList = ColorStateList.valueOf(tintColor)

        // 클릭 시 색상 변경
        binding.root.setOnClickListener {
            val updatedList = currentList.map { listItem ->
                when (listItem.date) {
                    today -> listItem.copy(firstConnect = "second")
                    item.date -> listItem.copy(isSelected = true)
                    else -> listItem.copy(isSelected = false)
                }
            }
            submitList(updatedList)
            onItemClick(item)
        }
    }

}