package com.example.umc_stepper.ui.stepper.additional

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.umc_stepper.R
import com.example.umc_stepper.base.BaseFragment
import com.example.umc_stepper.databinding.FragmentAdditionalExerciseMiddleBinding
import com.example.umc_stepper.utils.timer.TimerWorker
import java.util.Timer
import java.util.TimerTask
import kotlin.concurrent.timer

class AdditionalExerciseMiddleFragment : BaseFragment<FragmentAdditionalExerciseMiddleBinding>(R.layout.fragment_additional_exercise_middle) {

    private var isRunning = false
    private var pauseTime = 0L
    private var time = 0
    private var mode = ""

    private lateinit var timer: Timer
    private lateinit var startBtn: Button
    private lateinit var resetBtn: Button


    override fun setLayout() {
        initButton()
    }

    private fun initButton() {
        startBtn = binding.fragmentAdditionalMiddleStartBtn
        resetBtn = binding.fragmentAdditionalMiddleResetBtn
    }

    // 타이머 시작
    private fun startTimer() {
        startBtn.setOnClickListener {
            if(!isRunning) {
                isRunning = true
                startBtn.text = "중지"
            } else {
                isRunning = false
                startBtn.text = "계속"
            }
        }
    }

    private fun startWorkManager() {
        val inputData = Data.Builder().putLong("time", time.toLong()).build()
        val workRequest = OneTimeWorkRequest.Builder(TimerWorker::class.java)
            .setInputData(inputData)
            .build()

        WorkManager.getInstance(requireContext()).enqueue(workRequest)
        WorkManager.getInstance(requireContext()).getWorkInfoByIdLiveData(workRequest.id)
            .observe(viewLifecycleOwner) {  workInfo ->
                if (workInfo != null && workInfo.state.isFinished) {
                    val newTime = workInfo.outputData.getLong("new_time", 0L)
                    time = newTime.toInt()
                    updateTimerUI(time)
                    if (isRunning) {
                        startWorkManager()
                    }
                }
            }
    }

    private fun updateTimerUI(time: Int) {
        val hours = time / 3600
        val minutes = (time % 3600) / 60
        val seconds = time % 60

        binding.fragmentAdditionalMiddleHourTv.text = String.format("%02d", hours)
        binding.fragmentAdditionalMiddleMinTv.text = String.format("%02d", minutes)
        binding.fragmentAdditionalMiddleSecTv.text = String.format("%02d", seconds)
    }


    private fun resetTimer() {
        resetBtn.setOnClickListener {
            isRunning = false
            time = 0
            startBtn.text ="시작"
            updateTimerUI(time)
        }
    }


}