package com.example.umc_stepper.data.remote

import com.example.umc_stepper.domain.model.response.YouTubeVideo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface YoutubeApi {
    @GET("videos")
    suspend fun getYoutubeDetail(
        @Query("part") part: String,
        @Query("id") id: String,
        @Query("key") apiKey: String
    ): YouTubeVideo
}