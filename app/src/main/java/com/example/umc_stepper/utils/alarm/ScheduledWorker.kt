package com.example.umc_stepper.utils.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.umc_stepper.domain.model.local.ExerciseAlarm
import com.example.umc_stepper.ui.stepper.AlarmReceiver
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.coroutineScope
import java.util.Calendar

class ScheduledWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result = coroutineScope {

        // 알람 정보 추출
        val alarmId = inputData.getInt("alarmId", 0)
        val day = inputData.getString("day") ?: return@coroutineScope Result.failure()
        val time = inputData.getString("time") ?: return@coroutineScope Result.failure()
        val amPm = inputData.getString("amPm") ?: return@coroutineScope Result.failure()
        val materials = inputData.getString("materials") ?: ""

        // 추출한 정보로 ExerciseAlarm 객체 설정
        val alarm = ExerciseAlarm(day, time, amPm, materials, true)
        Log.d("ScheduledWorker", "alarm : $alarm")

        val days = day.split(", ").map { it.trim() }
        days.forEachIndexed { index, singleDay ->
            setAlarm(alarmId + index, singleDay, alarm)
        }

        Result.success()
    }

    // 실제 알람 설정 함수
    private fun setAlarm(alarmId: Int, day: String, alarm: ExerciseAlarm) {
        val alarmManager = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(applicationContext, AlarmReceiver::class.java).apply {
            putExtra("message", "운동할 시간입니다! 준비물: ${alarm.materials}")
        }
        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            alarmId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val calendar = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_WEEK, getDayOfWeek(day))
            set(Calendar.HOUR_OF_DAY, getHour(alarm.time, alarm.amPm))
            set(Calendar.MINUTE, alarm.time.split(":")[1].toInt())
            set(Calendar.SECOND, 0)

            // 설정된 시간이 현재 시간보다 이전이면, 다음 주로 설정
            if (timeInMillis <= System.currentTimeMillis()) {
                add(Calendar.WEEK_OF_YEAR, 1)
            }
        }

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY * 7,
            pendingIntent
        )
    }

    // 요일 문자열을 Calendar.DAY_OF_WEEK로 변환
    private fun getDayOfWeek(day: String): Int {
        return when (day) {
            "일요일" -> Calendar.SUNDAY
            "월요일" -> Calendar.MONDAY
            "화요일" -> Calendar.TUESDAY
            "수요일" -> Calendar.WEDNESDAY
            "목요일" -> Calendar.THURSDAY
            "금요일" -> Calendar.FRIDAY
            "토요일" -> Calendar.SATURDAY
            else -> throw IllegalArgumentException("Invalid day: $day")
        }
    }

    // 시간을 24시간 형식으로 변환
    private fun getHour(time: String, amPm: String): Int {
        val hour = time.split(":")[0].toInt()
        return if (amPm == "오후" && hour != 12) hour + 12 else hour
    }

}