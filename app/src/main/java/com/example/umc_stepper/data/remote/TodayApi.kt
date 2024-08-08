package com.example.umc_stepper.data.remote

import com.example.umc_stepper.base.BaseListResponse
import com.example.umc_stepper.base.BaseResponse
import com.example.umc_stepper.domain.model.response.CheckExerciseResponseDTO
import com.example.umc_stepper.domain.model.response.ExerciseCardStatusResponseDto
import com.example.umc_stepper.domain.model.response.ExerciseCardWeekResponseDto
import com.example.umc_stepper.domain.model.response.ToDayExerciseResponseDto
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface TodayApi {

    // 운동 카드 Api

    // 오늘의 운동 진행 상태 조회 -> 투데이 홈
    @GET("/api/exercise-card/today")
    suspend fun getTodayExerciseState(
        @Query("date") date: String
    ) : BaseListResponse<ToDayExerciseResponseDto>

    // 운동 부위의 운동 카드 요일 조회
    @GET("/api/exercise-card/check-date")
    suspend fun getExerciseCheckDate(
        @Query("bodyPart") bodyPart: String
    ) : BaseListResponse<ExerciseCardWeekResponseDto>

    // 월별 운동 카드 상태 조회 API
    @GET("/api/exercise-card")
    suspend fun getExerciseMonthCheck(
        @Query("month") month: Int
    ) : BaseListResponse<ExerciseCardStatusResponseDto>

    // 나만의 운동

    // 나만의 운동 조회 -> 스크랩 화면
    @GET("/api/myexercise/check")
    suspend fun getMyExercise(
        @Query("bodyPart") bodyPart: String
    ) : BaseListResponse<CheckExerciseResponseDTO>

}