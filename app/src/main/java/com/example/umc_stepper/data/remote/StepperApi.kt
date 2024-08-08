package com.example.umc_stepper.data.remote

import com.example.umc_stepper.base.BaseListResponse
import com.example.umc_stepper.base.BaseResponse
import com.example.umc_stepper.domain.model.Time
import com.example.umc_stepper.domain.model.request.AiVideoDto
import com.example.umc_stepper.domain.model.response.TimeResponse
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface StepperApi {
    @POST("/api/more-exercise/add")
    suspend fun postMoreExerciseAdd(
        @Body time: Time
    ): BaseResponse<TimeResponse>

    @GET("api/more-exercise/")
    suspend fun getMoreExercise(
        @Query("date") date : String
    ) : BaseListResponse<TimeResponse>
}