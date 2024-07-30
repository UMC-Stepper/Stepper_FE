package com.example.umc_stepper.ui.stepper

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "운동 시간 입니다!!", Toast.LENGTH_SHORT).show()

    }
}
