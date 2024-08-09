package com.example.umc_stepper.data.source.today

import android.util.Log
import com.example.umc_stepper.base.BaseListResponse
import com.example.umc_stepper.base.BaseResponse
import com.example.umc_stepper.data.remote.TodayApi
import com.example.umc_stepper.domain.model.request.exercise_card_controller.ExerciseCardRequestDto
import com.example.umc_stepper.domain.model.response.exercise_card_controller.ExerciseCardResponse
import com.example.umc_stepper.domain.model.response.exercise_card_controller.ExerciseCardStatusResponseDto
import com.example.umc_stepper.domain.model.response.exercise_card_controller.ExerciseCardWeekResponseDto
import com.example.umc_stepper.domain.model.response.exercise_card_controller.ToDayExerciseResponseDto
import com.example.umc_stepper.domain.model.response.my_exercise_controller.CheckExerciseResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

import javax.inject.Inject

class TodayApiDataSource @Inject constructor(
    private val todayApi: TodayApi
){

    // 운동 카드 추가 API
    fun postAddExerciseCard(exerciseCardRequestDto: ExerciseCardRequestDto)	: Flow<BaseResponse<ExerciseCardResponse>> = flow {
        val result = todayApi.postAddExerciseCard(exerciseCardRequestDto)
        emit(result)
    }.catch {
        Log.e("Post AddExerciseCard Failure", it.message.toString())
    }

    // 운동 카드 상세 조회 API
    fun postInquiryExerciseCard(exerciseId: Int) : Flow<BaseResponse<ExerciseCardResponse>> = flow {
        val result = todayApi.postInquiryExerciseCard(exerciseId)
        emit(result)
    }.catch {
        Log.e("Post InquiryExerciseCard Failure", it.message.toString())
    }

    // 오늘의 운동 진행 상태 조회 API
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

    // 운동 부위의 운동 카드 요일 조회 API
    fun getMyExercise(bodyPart: String): Flow<BaseListResponse<CheckExerciseResponse>> = flow {
        val result = todayApi.getMyExercise(bodyPart)
        emit(result)
    }.catch {
        Log.e("GET MyExercise Failure", it.message.toString())
    }

    // 월별 운동 카드 상태 조회 API
    fun getExerciseMonthCheck(month: Int) : Flow<BaseListResponse<ExerciseCardStatusResponseDto>> = flow {
        val result = todayApi.getExerciseMonthCheck(month)
        emit(result)
    }.catch {
        Log.e("GET ExerciseMonthCheck Failure", it.message.toString())
    }

    // 나만의 운동 조회 API -> 스크랩 화면
    fun getExerciseCheckDate(bodyPart: String) : Flow<BaseListResponse<ExerciseCardWeekResponseDto>> = flow {
        val result = todayApi.getExerciseCheckDate(bodyPart)
        emit(result)
    }.catch {
        Log.e("GET MyExercise Failure", it.message.toString())
    }


}