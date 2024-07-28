package com.example.umc_stepper.domain.repository

import com.example.umc_stepper.domain.model.request.AiVideoDto
import com.example.umc_stepper.domain.model.response.AiVideoInfo
import kotlinx.coroutines.flow.Flow

interface FastApiRepository {
    suspend fun postAiVideoInfo(aiVideoDto: AiVideoDto) : Flow<AiVideoInfo>

}