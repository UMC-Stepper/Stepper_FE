package com.example.umc_stepper.data.source.today

import android.util.Log
import com.example.umc_stepper.base.BaseResponse
import com.example.umc_stepper.data.remote.TodayApi
import com.example.umc_stepper.domain.model.response.CheckExerciseResponseDTO
import com.example.umc_stepper.domain.model.response.ToDayExerciseResponseDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

import javax.inject.Inject

class TodayApiDataSource @Inject constructor(
    private val todayApi: TodayApi
){
    fun getTodayExerciseState(date: String): Flow<BaseResponse<ToDayExerciseResponseDto>> = flow {
        val result = todayApi.getTodayExerciseState(date)
        emit(result)
    }.catch {
        Log.e("getTodayExerciseState Failure", it.message.toString())
    }

    fun getMyExercise(bodyPart: String): Flow<BaseResponse<CheckExerciseResponseDTO>> = flow {
        val result = todayApi.getMyExercise(bodyPart)
        emit(result)
    }.catch {
        Log.e("getMyExercise Failure", it.message.toString())
    }
}