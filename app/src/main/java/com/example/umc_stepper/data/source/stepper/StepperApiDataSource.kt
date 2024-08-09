package com.example.umc_stepper.data.source.stepper

import android.util.Log
import com.example.umc_stepper.base.BaseListResponse
import com.example.umc_stepper.base.BaseResponse
import com.example.umc_stepper.data.remote.StepperApi
import com.example.umc_stepper.domain.model.Time
import com.example.umc_stepper.domain.model.response.more_exercise_controller.TimeResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class StepperApiDataSource @Inject constructor(
    private val stepperApi: StepperApi
) {
    fun postMoreExercise(time: Time): Flow<BaseResponse<TimeResponse>> = flow {
        val result = stepperApi.postMoreExerciseAdd(time)
        emit(result)
    }.catch { e ->
        Log.e("에러", e.message.toString())
    }

    fun getMoreExercise(date: String): Flow<BaseListResponse<TimeResponse>> = flow {
        val result = stepperApi.getMoreExercise(date)
        emit(result)
    }.catch { e ->
        Log.e("에러", e.message.toString())
    }
}