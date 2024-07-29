package com.example.umc_stepper.utils.timer

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf

class TimerWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    override fun doWork(): Result {
        val time = inputData.getLong("time", 0L)
        val newTime = time + 1
        val outputData = workDataOf("new_time" to newTime)
        return Result.success(outputData)
    }
}