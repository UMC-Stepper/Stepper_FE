package com.example.umc_stepper.ui.stepper.additional

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentAdditionalExerciseMiddleBinding
import com.example.umc_stepper.ui.MainActivity

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

    private lateinit var mainActivity : MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }


    override fun setLayout() {
        updateMainToolbar()
        initButton()
        setTimer()
        resetTimer()
        binding.fragmentAdditionalMiddleExerciseCompleteBtn.setOnClickListener {
            goAdditionalExerciseSuccess()
        }
    }

    private fun updateMainToolbar() {
        mainActivity.updateToolbarTitle("추가 운동하기")
    }

    private fun goAdditionalExerciseSuccess(){
        binding.fragmentAdditionalMiddleExerciseCompleteBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.White))
        binding.fragmentAdditionalMiddleExerciseCompleteBtn.setBackgroundResource(R.drawable.shape_rounded_square_purple700_60dp)
        findNavController().navigate(R.id.action_fragmentAdditionalExerciseMiddle_to_fragmentAdditionalExerciseSuccess)
    }

    private fun initButton() {
        startBtn = binding.fragmentAdditionalMiddleStartBtn
        resetBtn = binding.fragmentAdditionalMiddleResetBtn
    }

    private fun setButtonUI(text: String) {
        when(text) {
            "시작", "중지" -> {
                binding.fragmentAdditionalMiddleExerciseCompleteBtn.setBackgroundResource(R.drawable.radius_corners_61dp_stroke_1)
                binding.fragmentAdditionalMiddleExerciseCompleteBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.Purple_700))
            }
            "계속" -> {
                binding.fragmentAdditionalMiddleExerciseCompleteBtn.setBackgroundResource(R.drawable.shape_rounded_square_purple700_60dp)
                binding.fragmentAdditionalMiddleExerciseCompleteBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.White))
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
