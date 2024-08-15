package com.example.umc_stepper.data.remote

import com.example.umc_stepper.base.BaseListResponse
import com.example.umc_stepper.base.BaseResponse
import com.example.umc_stepper.domain.model.Time
import com.example.umc_stepper.domain.model.request.exercise_card_controller.ExerciseCardRequestDto
import com.example.umc_stepper.domain.model.response.exercise_card_controller.ExerciseCardResponse
import com.example.umc_stepper.domain.model.response.more_exercise_controller.TimeResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface StepperApi {

    // 평가일지 작성 API
    @POST("/api/more-exercise/add")
    suspend fun postMoreExerciseAdd(
        @Body time: Time
    ): BaseResponse<TimeResponse>

    // 평가일지 조회 API
    @GET("api/more-exercise")
    suspend fun getMoreExercise(
        @Query("date") date : String
    ) : BaseListResponse<TimeResponse>

    // 운동 카드 수정 API
    @PUT("/api/exercise-card/{exerciseId}/edit")
    suspend fun putEditExerciseCard(
        @Query("exerciseId") exerciseId : Int,
        @Body exerciseCardRequestDto: ExerciseCardRequestDto
    ) : BaseListResponse<ExerciseCardResponse>

    // 운동 카드 단계별 상태 수정 API
    @POST("/api/exerciseStep/step")
    suspend fun postEditExerciseStep(
        @Query("stepId") stepId : Int,
    ) : BaseResponse<Any>

}