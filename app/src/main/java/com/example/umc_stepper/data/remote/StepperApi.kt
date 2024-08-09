package com.example.umc_stepper.data.remote

import com.example.umc_stepper.base.BaseListResponse
import com.example.umc_stepper.base.BaseResponse
import com.example.umc_stepper.domain.model.Time
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
    @GET("api/more-exercise/")
    suspend fun getMoreExercise(
        @Query("date") date : String
    ) : BaseListResponse<TimeResponse>
}