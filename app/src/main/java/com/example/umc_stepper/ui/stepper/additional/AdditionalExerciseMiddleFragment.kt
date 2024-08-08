package com.example.umc_stepper.ui.stepper.additional

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentAdditionalExerciseMiddleBinding
import com.example.umc_stepper.domain.model.Time
import com.example.umc_stepper.ui.MainActivity
import com.google.gson.Gson

class AdditionalExerciseMiddleFragment : BaseFragment<FragmentAdditionalExerciseMiddleBinding>(R.layout.fragment_additional_exercise_middle) {

    private var isRunning = false
    private var time = 0L // 밀리초 단위

    private lateinit var startBtn: AppCompatButton
    private lateinit var resetBtn: AppCompatButton
    private lateinit var completeBtn : AppCompatButton

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

    private lateinit var mainActivity : MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }


    override fun setLayout() {
        initSettings()

        completeBtn.setOnClickListener {
            goAdditionalExerciseSuccess()
        }
    }

    private fun initSettings() {
        updateMainToolbar()
        initButton()
        setTimer()
        resetTimer()
    }

    private fun updateMainToolbar() {
        mainActivity.updateToolbarTitle("추가 운동하기")
    }

    private fun goAdditionalExerciseSuccess(){

        handler.removeCallbacks(timerRunnable)  // 타이머 중지

        val hour = binding.fragmentAdditionalMiddleHourTv.text.toString()
        val min = binding.fragmentAdditionalMiddleMinTv.text.toString()
        val sec = binding.fragmentAdditionalMiddleSecTv.text.toString()

        val time = Time(hour, min, sec)
        val gson = Gson()

        val timeJson = gson.toJson(time)
        val args = Bundle().apply {
            putString("time", timeJson)
        }

        val action = AdditionalExerciseMiddleFragmentDirections.actionFragmentAdditionalExerciseMiddleToFragmentAdditionalExerciseSuccess()
        findNavController().navigate(action.actionId, args)
    }

    private fun initButton() {
        startBtn = binding.fragmentAdditionalMiddleStartBtn
        resetBtn = binding.fragmentAdditionalMiddleResetBtn
        completeBtn = binding.fragmentAdditionalMiddleExerciseCompleteBtn
    }

    private fun setButtonUI(text: String) {
        when(text) {
            "시작", "중지" -> {
                completeBtn.setBackgroundResource(R.drawable.radius_corners_61dp_stroke_1)
                completeBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.Purple_700))
            }
            "계속" -> {
                completeBtn.setBackgroundResource(R.drawable.shape_rounded_square_purple700_60dp)
                completeBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.White))
            }
        }
    }

    private fun setTimer() {
        startBtn.setOnClickListener {
            if (!isRunning) {
                isRunning = true
                startBtn.text = "중지"
                setButtonUI(startBtn.text.toString())
                handler.post(timerRunnable) // 타이머 시작

            } else {
                isRunning = false
                startBtn.text = "계속"
                setButtonUI(startBtn.text.toString())
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

    override fun onStop() {
        super.onStop()

        isRunning = false
        time = 0
        startBtn.text = "시작"
        setButtonUI(startBtn.text.toString())
        updateTimerUI(time)
        handler.removeCallbacks(timerRunnable)
    }

    private fun resetTimer() {
        resetBtn.setOnClickListener {
            isRunning = false
            time = 0
            startBtn.text = "시작"
            setButtonUI(startBtn.text.toString())
            updateTimerUI(time)
            handler.removeCallbacks(timerRunnable) // 타이머 중지
        }
    }
}
