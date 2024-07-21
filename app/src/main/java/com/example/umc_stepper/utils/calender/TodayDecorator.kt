package com.example.umc_stepper.utils.calender

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.TextAppearanceSpan
import com.example.umc_stepper.R
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.spans.DotSpan
import java.util.Calendar

class TodayDecorator(context: Context) : DayViewDecorator {
    private var date = CalendarDay.today()

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return day?.equals(date)!!
    }
    override fun decorate(view: DayViewFacade?) {

    }
}

class EventDecorator()
    : DayViewDecorator {

    private val datesWithEvent = mutableSetOf<CalendarDay>()

    init {

    }

    override fun shouldDecorate(day: CalendarDay): Boolean {
        // 저장된 결과를 사용하여 데코레이션을 적용할 날짜를 결정합니다.
        return datesWithEvent.contains(day)
    }

    override fun decorate(view: DayViewFacade) {
        view.addSpan(DotSpan(10F, Color.parseColor("#d3d3d3")))
    }
}

// 이전 달, 다음 달 일부 보여줌 (기간 외 날짜)
class SelectedMonthDecorator(private val selectedMonth : Int) : DayViewDecorator {
    override fun shouldDecorate(day: CalendarDay): Boolean {
        return day.month != selectedMonth
    }
    override fun decorate(view: DayViewFacade) {
        view?.addSpan(object:ForegroundColorSpan(Color.parseColor("#525068")){})
        view?.setDaysDisabled(true) // 선택 불가
    }
}

class SaturdayDecorator(private val context: Context) : DayViewDecorator {
    override fun shouldDecorate(day: CalendarDay?): Boolean {
        val localDate = day!!.date
        val calendar = Calendar.getInstance()
        calendar.set(localDate.year, localDate.monthValue - 1, localDate.dayOfMonth)
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        return dayOfWeek == Calendar.SATURDAY

    }
    override fun decorate(view: DayViewFacade?) {
        val textAppearanceSpan = TextAppearanceSpan(context, R.style.CalenderViewDateCustomText)
        view?.addSpan(textAppearanceSpan)
    }
}

class SundayDecorator(private val context: Context) : DayViewDecorator {
    override fun shouldDecorate(day: CalendarDay?): Boolean {
        val localDate = day!!.date
        val calendar = Calendar.getInstance()
        calendar.set(localDate.year, localDate.monthValue - 1, localDate.dayOfMonth)
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        return dayOfWeek == Calendar.SUNDAY

    }
    override fun decorate(view: DayViewFacade?) {
        val textAppearanceSpan = TextAppearanceSpan(context, R.style.CalenderViewDateCustomText)
        view?.addSpan(textAppearanceSpan)
    }
}

class BoldDecorator(private val selectedMonth : Int) : DayViewDecorator {
    override fun shouldDecorate(day: CalendarDay): Boolean {
        //return day.month != selectedMonth || day.month == selectedMonth
        return day.month == selectedMonth
    }
    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(object: StyleSpan(Typeface.BOLD){})
        //view?.addSpan(object: RelativeSizeSpan(1.4f){})
    }
}