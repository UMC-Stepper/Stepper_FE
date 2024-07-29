package com.example.umc_stepper.data.source.fastapi

import com.example.umc_stepper.domain.model.request.AiVideoDto
import com.example.umc_stepper.domain.model.response.AiVideoInfo
import com.example.umc_stepper.domain.repository.FastApiRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FastApiRepositoryImpl @Inject constructor(
    private val dataSource: FastApiDataSource
) : FastApiRepository {
    override suspend fun postAiVideoInfo(aiVideoDto: AiVideoDto): Flow<AiVideoInfo> =
        dataSource.postAiVideoInfo(aiVideoDto)
}