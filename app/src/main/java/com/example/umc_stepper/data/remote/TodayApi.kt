package com.example.umc_stepper.data.remote

import com.example.umc_stepper.base.BaseListResponse
import com.example.umc_stepper.base.BaseResponse
import com.example.umc_stepper.domain.model.response.CheckExerciseResponseDTO
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


    // 나만의 운동

    // 나만의 운동 조회 -> 스크랩 화면
    @GET("/api/myexercise/check")
    suspend fun getMyExercise(
        @Query("bodyPart") bodyPart: String
    ) : BaseResponse<CheckExerciseResponseDTO>
}