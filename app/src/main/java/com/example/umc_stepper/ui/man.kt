package com.example.umc_stepper.ui

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.lifecycleScope
import com.example.umc_stepper.R
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDateTime
import kotlin.math.sqrt

class man : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private var stepCount = 0
    private var lastMagnitude = 0f
    private lateinit var stepCountTextView: TextView
    private lateinit var resetBtn: Button
    lateinit var bt: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_man)
        bt = findViewById<Button>(R.id.butt)

        stepCountTextView = findViewById(R.id.stepCountTextView)

        resetBtn = findViewById(R.id.resetButton)

        resetBtn.setOnClickListener {
            stepCount = 0
            updateStepCount()
        }


        bt.setOnClickListener {
            val now = LocalDateTime.now()

            // 비교할 날짜와 시간 설정 (2024년 8월 22일 18시)
            val targetDateTime = LocalDateTime.of(2024, 8, 22, 18, 0)

            // 현재 시간이 targetDateTime 이후인지 체크
            if (now.isAfter(targetDateTime)) {
                // 조건을 만족하면 Gridd 액티비티로 이동
                startActivity(Intent(this@man, Gridd::class.java))
            }
            else{
                Toast.makeText(this@man,"2024년 8월 22일 18시에 공개 됩니다.",Toast.LENGTH_SHORT).show()
            }
        }

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    override fun onResume() {
        super.onResume()
        accelerometer?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            val magnitude = sqrt(x * x + y * y + z * z)
            val delta = magnitude - lastMagnitude
            lastMagnitude = magnitude

            if (delta > 6) {
                stepCount++
                updateStepCount()
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // 정확도 변경 시 필요한 처리
    }

    private fun updateStepCount() {

        stepCountTextView.text = "걸음 수: $stepCount"
        lifecycleScope.launch {
            if (stepCount == 100) {
                bt.visibility = View.VISIBLE
            }
        }
    }
}