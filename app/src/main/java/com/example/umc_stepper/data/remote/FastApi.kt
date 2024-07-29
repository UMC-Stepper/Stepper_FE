package com.example.umc_stepper.data.remote

import com.example.umc_stepper.domain.model.request.AiVideoDto
import com.example.umc_stepper.domain.model.response.AiVideoInfo
import retrofit2.http.Body
import retrofit2.http.POST

interface FastApi {
    // AI 서버
    @POST("/recommendations")
    suspend fun postAiVideoInfo(
        @Body aiVideoDto: AiVideoDto
    ): AiVideoInfo
}