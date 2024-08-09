package com.example.umc_stepper.domain.repository

import com.example.umc_stepper.base.BaseListResponse
import com.example.umc_stepper.base.BaseResponse
import com.example.umc_stepper.domain.model.request.exercise_card_controller.ExerciseCardRequestDto
import com.example.umc_stepper.domain.model.response.exercise_card_controller.ExerciseCardResponse
import com.example.umc_stepper.domain.model.response.exercise_card_controller.ExerciseCardStatusResponseDto
import com.example.umc_stepper.domain.model.response.exercise_card_controller.ExerciseCardWeekResponseDto
import com.example.umc_stepper.domain.model.response.exercise_card_controller.ToDayExerciseResponseDto
import com.example.umc_stepper.domain.model.response.my_exercise_controller.AddExerciseResponse
import com.example.umc_stepper.domain.model.response.my_exercise_controller.CheckExerciseResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.POST

interface TodayApiRepository {
    suspend fun postAddExerciseCard(exerciseCardRequestDto: ExerciseCardRequestDto)	: Flow<BaseResponse<ExerciseCardResponse>>// 운동 카드 추가 API
    suspend fun postInquiryExerciseCard(exerciseId: Int) : Flow<BaseResponse<ExerciseCardResponse>>      // 운동 카드 상세 조회 API
    suspend fun getTodayExerciseState(date: String) : Flow<BaseListResponse<ToDayExerciseResponseDto>>  // 오늘의 운동 진행 상태 조회
    suspend fun getMyExercise(bodyPart: String) : Flow<BaseListResponse<CheckExerciseResponse>>     // 나만의 운동 조회
    suspend fun getExerciseMonthCheck(month: Int) : Flow<BaseListResponse<ExerciseCardStatusResponseDto>> // 월별 운동 카드 상태 조회 API
    suspend fun postAddMyExercise(addExerciseRequestDto: ExerciseCardRequestDto) : Flow<BaseResponse<AddExerciseResponse>>  // 나만의 운동 추가 API
    suspend fun getExerciseCheckDate(bodyPart: String) : Flow<BaseListResponse<ExerciseCardWeekResponseDto>> // 나만의 운동 조회 -> 스크랩 화면
}