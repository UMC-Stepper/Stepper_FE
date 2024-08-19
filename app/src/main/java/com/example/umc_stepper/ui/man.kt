package com.example.umc_stepper.ui

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_stepper.R
import kotlin.math.sqrt

class man : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private var stepCount = 0
    private var lastMagnitude = 0f
    private lateinit var stepCountTextView: TextView
    private lateinit var resetBtn : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_man)

        stepCountTextView = findViewById(R.id.stepCountTextView)

        resetBtn = findViewById(R.id.resetButton)

        resetBtn.setOnClickListener {
            stepCountTextView.text = "걸음 수: 0"
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
    }
}