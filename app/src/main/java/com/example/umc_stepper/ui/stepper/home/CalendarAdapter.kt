package com.example.umc_stepper.ui.stepper.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.content.ContextCompat
import com.example.umc_stepper.R
import com.example.umc_stepper.databinding.ItemStepperHomeCalendarBinding

class CalendarAdapter(private val context: Context, private val days: List<DayData>) : BaseAdapter() {

    override fun getCount(): Int {
        return days.size
    }

    override fun getItem(position: Int): Any {
        return days[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding: ItemStepperHomeCalendarBinding
        val view: View

        if (convertView == null) {
            binding = ItemStepperHomeCalendarBinding.inflate(LayoutInflater.from(context), parent, false)
            view = binding.root
            view.tag = binding
        } else {
            binding = convertView.tag as ItemStepperHomeCalendarBinding
            view = convertView
        }

        val dayData = days[position]
        binding.stepperHomeCalendarDayTv.text = dayData.day

        if (position < 7) {
            binding.stepperHomeCalendarDayTv.setTextColor(ContextCompat.getColor(context, R.color.Purple_700)) // 요일색 설정
        }else if (position in 7..9){   // 7월의 날짜 3개(+글씨체 글자색 수정)
            binding.stepperHomeCalendarDayTv.setTextColor(ContextCompat.getColor(context, R.color.Gray_Purple))
        }else if (position in 10..40){   // 8월 날짜(+글씨체 글자색 수정)
            binding.stepperHomeCalendarDayTv.setTextColor(ContextCompat.getColor(context, R.color.White))
        } else{ // 9월의 날짜 1개(+글씨체 글자색 수정)
            binding.stepperHomeCalendarDayTv.setTextColor(ContextCompat.getColor(context, R.color.Gray_Purple))
        }

        // 점과 아이콘 찍기
        binding.stepperHomeCalendarDotIv.visibility = if (dayData.hasDot) View.VISIBLE else View.GONE
        binding.stepperHomeCalendarIconIv.visibility = if (dayData.hasIcon) View.VISIBLE else View.GONE

        return view
    }
}

data class DayData(val day: String, var hasDot: Boolean, var hasIcon: Boolean)
