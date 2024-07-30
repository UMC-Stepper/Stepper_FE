package com.example.umc_stepper.ui.stepper

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.camera.core.impl.utils.ContextUtil.getApplicationContext
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentExerciseSettingsDateBinding
import java.util.Calendar

class ExerciseSettingsDateFragment : BaseFragment<FragmentExerciseSettingsDateBinding>(R.layout.fragment_exercise_settings_date){
    private lateinit var timePicker: TimePicker
    private lateinit var setAlarmButton: Button
    private lateinit var alarmTextView: TextView
    private lateinit var alarmMaterialEditText: EditText
    private lateinit var dayButtons: List<TextView>

    override fun setLayout() {
        val dayButtons = listOf(
            binding.fragmentExerciseSettingsSundayBt,
            binding.fragmentExerciseSettingsMondayBt,
            binding.fragmentExerciseSettingsTuesdayBt,
            binding.fragmentExerciseSettingsWednesdayBt,
            binding.fragmentExerciseSettingsThursdayBt,
            binding.fragmentExerciseSettingsFridayBt,
            binding.fragmentExerciseSettingsSaturdayBt
        )

        dayButtons.forEach { button ->
            button.setOnClickListener {
                button.isSelected = !button.isSelected
            }
        }

        binding.fragmentExerciseSettingsExerciseSuccessBt.setOnClickListener {
            setAlarmsForSelectedDays(dayButtons)
        }
    }
    private fun setAlarmsForSelectedDays(dayButtons: List<View>) {
        val selectedDays = dayButtons.mapIndexedNotNull { index, textView ->
            if (textView.isSelected) index + 1 else null
        }

        if (selectedDays.isEmpty()) {
            Toast.makeText(requireContext(), "하나 이상의 요일을 선택해주세요.",Toast.LENGTH_SHORT).show()
            return
        }

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, binding.fragmentExerciseSettingsTimeSpinner.hour)
        calendar.set(Calendar.MINUTE, binding.fragmentExerciseSettingsTimeSpinner.minute)
        calendar.set(Calendar.SECOND, 0)

        selectedDays.forEach { dayOfWeek ->
            setRepeatingAlarm(calendar, dayOfWeek)
        }
    }

    private fun setRepeatingAlarm(calendar: Calendar, dayOfWeek: Int) {
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(requireContext(), dayOfWeek, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek)

        // If the alarm time has already passed for today, set it for the next week.
        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.WEEK_OF_YEAR, 1)
        }

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY * 7,
            pendingIntent
        )

    }

}