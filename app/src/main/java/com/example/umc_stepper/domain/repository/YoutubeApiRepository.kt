package com.example.umc_stepper.domain.repository

import com.example.umc_stepper.domain.model.request.AiVideoDto
import com.example.umc_stepper.domain.model.response.AiVideoInfo
import com.example.umc_stepper.domain.model.response.YouTubeVideo
import kotlinx.coroutines.flow.Flow

interface YoutubeApiRepository {
    suspend fun getYoutubeDetail(part: String, id : String, apiKey : String) : Flow<YouTubeVideo>

}