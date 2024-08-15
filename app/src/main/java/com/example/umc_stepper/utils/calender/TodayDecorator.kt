package com.example.umc_stepper.utils.calender

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.TextAppearanceSpan
import androidx.core.content.ContextCompat
import com.example.umc_stepper.R
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.spans.DotSpan
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class TodayDecorator(context: Context) : DayViewDecorator {
    private var date = CalendarDay.today()

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return day?.equals(date)!!
    }
    override fun decorate(view: DayViewFacade?) {

    }
}

class EventDecorator(private val context: Context, private val bodyPartDateMap: Map<String, Set<String>>) : DayViewDecorator {

    private val datesWithEvent = mutableSetOf<CalendarDay>()

    init {
        // 날짜 파싱
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        bodyPartDateMap.values.flatten().forEach { dateString ->
            try {
                val date = dateFormat.parse(dateString)
                if (date != null) {
                    val calendar = Calendar.getInstance().apply {
                        time = date
                    }
                    val calendarDay = CalendarDay.from(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH))
                    datesWithEvent.add(calendarDay)
                }
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }
    }

    override fun shouldDecorate(day: CalendarDay): Boolean {
        // 저장된 결과를 사용하여 데코레이션을 적용할 날짜를 결정합니다.
        return datesWithEvent.contains(day)
    }

    override fun decorate(view: DayViewFacade) {
        //view.addSpan(DotSpan(10F, Color.parseColor("#d3d3d3")))
        val drawable: Drawable = ContextCompat.getDrawable(context, R.drawable.circle_purple_700_40dp)!!
        view?.setBackgroundDrawable(drawable)
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