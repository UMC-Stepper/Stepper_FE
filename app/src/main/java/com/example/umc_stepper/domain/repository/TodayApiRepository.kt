package com.example.umc_stepper.domain.repository

import com.example.umc_stepper.base.BaseListResponse
import com.example.umc_stepper.base.BaseResponse
import com.example.umc_stepper.domain.model.response.CheckExerciseResponseDTO
import com.example.umc_stepper.domain.model.response.ToDayExerciseResponseDto
import kotlinx.coroutines.flow.Flow

interface TodayApiRepository {
    suspend fun getTodayExerciseState(date: String) : Flow<BaseListResponse<ToDayExerciseResponseDto>>  // 오늘의 운동 진행 상태 조회
    suspend fun getMyExercise(bodyPart: String) : Flow<BaseResponse<CheckExerciseResponseDTO>>     // 나만의 운동 조회
}