package com.example.umc_stepper.data.source.community

import com.example.umc_stepper.base.BaseResponse
import com.example.umc_stepper.data.source.fastapi.FastApiDataSource
import com.example.umc_stepper.domain.model.request.AiVideoDto
import com.example.umc_stepper.domain.model.response.AiVideoInfo
import com.example.umc_stepper.domain.model.response.post_controller.ScrapResponse
import com.example.umc_stepper.domain.repository.CommunityApiRepository
import com.example.umc_stepper.domain.repository.FastApiRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CommunityApiRepositoryImpl @Inject constructor(
    private val dataSource: CommunityApiDataSource
) : CommunityApiRepository {
    override suspend fun postCommitScrap(postId : Int): Flow<BaseResponse<ScrapResponse>> =
        dataSource.postCommitScrap(postId)
}