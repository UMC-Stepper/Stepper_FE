package com.example.umc_stepper.data.source.today

import android.util.Log
import com.example.umc_stepper.base.BaseListResponse
import com.example.umc_stepper.base.BaseResponse
import com.example.umc_stepper.data.remote.TodayApi
import com.example.umc_stepper.domain.model.response.CheckExerciseResponseDTO
import com.example.umc_stepper.domain.model.response.ExerciseCardStatusResponseDto
import com.example.umc_stepper.domain.model.response.ExerciseCardWeekResponseDto
import com.example.umc_stepper.domain.model.response.ToDayExerciseResponseDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

import javax.inject.Inject

class TodayApiDataSource @Inject constructor(
    private val todayApi: TodayApi
){
    fun getTodayExerciseState(date: String): Flow<BaseListResponse<ToDayExerciseResponseDto>> = flow {
        val result = todayApi.getTodayExerciseState(date)
        emit(result)
    }.catch { e ->
        val errorBody = (e as? HttpException)?.response()?.errorBody()?.string()
        Log.e("getTodayExerciseState FailureHttpException", "HTTP ${(e as? HttpException)?.code()} Error: $errorBody")
        emit(BaseListResponse<ToDayExerciseResponseDto>(
            isSuccess = false,
            code = (e as? HttpException)?.code().toString(),
            message = errorBody ?: e.message ?: "Unknown error"
        ))
    }

    fun getMyExercise(bodyPart: String): Flow<BaseListResponse<CheckExerciseResponseDTO>> = flow {
        val result = todayApi.getMyExercise(bodyPart)
        emit(result)
    }.catch {
        Log.e("GET MyExercise Failure", it.message.toString())
    }

    fun getExerciseMonthCheck(month: Int) : Flow<BaseListResponse<ExerciseCardStatusResponseDto>> = flow {
        val result = todayApi.getExerciseMonthCheck(month)
        emit(result)
    }.catch {
        Log.e("GET ExerciseMonthCheck Failure", it.message.toString())
    }

    fun getExerciseCheckDate(bodyPart: String) : Flow<BaseListResponse<ExerciseCardWeekResponseDto>> = flow {
        val result = todayApi.getExerciseCheckDate(bodyPart)
        emit(result)
    }.catch {
        Log.e("GET MyExercise Failure", it.message.toString())
    }


}