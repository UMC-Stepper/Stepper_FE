package com.example.umc_stepper.data.remote

import com.example.umc_stepper.base.BaseListResponse
import com.example.umc_stepper.base.BaseResponse
import com.example.umc_stepper.domain.model.request.exercise_card_controller.ExerciseCardRequestDto
import com.example.umc_stepper.domain.model.request.my_exercise_controller.AddExerciseRequestDto
import com.example.umc_stepper.domain.model.response.exercise_card_controller.ExerciseCardResponse
import com.example.umc_stepper.domain.model.response.exercise_card_controller.ExerciseCardStatusResponseDto
import com.example.umc_stepper.domain.model.response.exercise_card_controller.ExerciseCardWeekResponseDto
import com.example.umc_stepper.domain.model.response.exercise_card_controller.ToDayExerciseResponseDto
import com.example.umc_stepper.domain.model.response.my_exercise_controller.AddExerciseResponse
import com.example.umc_stepper.domain.model.response.my_exercise_controller.CheckExerciseResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface TodayApi {

    // 운동 카드 Api

    // 운동 카드 추가 API
    @POST("/api/exercise-card/add")
    suspend fun postAddExerciseCard(
        @Body exerciseCardRequestDto: ExerciseCardRequestDto
    ) : BaseResponse<ExerciseCardResponse>

    // 운동 카드 상세 조회 API
    @POST("/api/exercise-card/{exerciseId}/")
    suspend fun postInquiryExerciseCard(
        @Path("exerciseId") exerciseId: Int
    ) : BaseResponse<ExerciseCardResponse>

    // 오늘의 운동 진행 상태 조회 API -> 투데이 홈
    @GET("/api/exercise-card/today")
    suspend fun getTodayExerciseState(
        @Query("date") date: String
    ) : BaseListResponse<ToDayExerciseResponseDto>

    // 운동 부위의 운동 카드 요일 조회 API
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

    // 나만의 운동 추가 API
    @POST("/api/myexercise/add")
    suspend fun postAddMyExercise(
        @Body addExerciseRequestDto: AddExerciseRequestDto
    ) : BaseResponse<AddExerciseResponse>

    // 나만의 운동 조회 API -> 스크랩 화면
    @GET("/api/myexercise/check")
    suspend fun getMyExercise(
        @Query("request") bodyPart: String
    ) : BaseListResponse<CheckExerciseResponse>

}