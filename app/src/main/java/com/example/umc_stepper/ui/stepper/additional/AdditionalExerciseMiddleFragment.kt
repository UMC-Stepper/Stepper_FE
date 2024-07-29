package com.example.umc_stepper.ui.stepper.additional

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentAdditionalExerciseMiddleBinding

class AdditionalExerciseMiddleFragment : BaseFragment<FragmentAdditionalExerciseMiddleBinding>(R.layout.fragment_additional_exercise_middle) {

    private var isRunning = false
    private var time = 0L // 밀리초 단위

    private lateinit var startBtn: Button
    private lateinit var resetBtn: Button

    private val handler = Handler(Looper.getMainLooper())
    private val timerRunnable = object : Runnable {
        override fun run() {
            if (isRunning) {
                time += 1000
                updateTimerUI(time)
                handler.postDelayed(this, 1000)
            }
        }
    }

    override fun setLayout() {
        initButton()
        setTimer()
        resetTimer()
    }

    private fun initButton() {
        startBtn = binding.fragmentAdditionalMiddleStartBtn
        resetBtn = binding.fragmentAdditionalMiddleResetBtn
    }

    private fun setTimer() {
        startBtn.setOnClickListener {
            if (!isRunning) {
                isRunning = true
                startBtn.text = "중지"
                handler.post(timerRunnable) // 타이머 시작
            } else {
                isRunning = false
                startBtn.text = "계속"
                handler.removeCallbacks(timerRunnable) // 타이머 중지
            }
        }
    }

    private fun updateTimerUI(time: Long) {
        val timeInSeconds = time / 1000
        val hours = timeInSeconds / 3600
        val minutes = (timeInSeconds % 3600) / 60
        val seconds = timeInSeconds % 60

        Log.d("updateTimerUI", "시: $hours 분: $minutes 초: $seconds")

        binding.fragmentAdditionalMiddleHourTv.text = String.format("%02d", hours)
        binding.fragmentAdditionalMiddleMinTv.text = String.format("%02d", minutes)
        binding.fragmentAdditionalMiddleSecTv.text = String.format("%02d", seconds)
    }

    private fun resetTimer() {
        resetBtn.setOnClickListener {
            isRunning = false
            time = 0
            startBtn.text = "시작"
            updateTimerUI(time)
            handler.removeCallbacks(timerRunnable) // 타이머 중지
        }
    }
}
