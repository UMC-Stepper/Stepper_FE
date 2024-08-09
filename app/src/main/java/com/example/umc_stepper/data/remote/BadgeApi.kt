package com.example.umc_stepper.data.remote

import com.example.umc_stepper.base.BaseListResponse
import com.example.umc_stepper.domain.model.response.BadgeResponse
import retrofit2.http.GET

interface BadgeApi {
    //뱃지 조회
    @GET("/api/badge")
    suspend fun getBadge(

    ):BaseListResponse<BadgeResponse>
}