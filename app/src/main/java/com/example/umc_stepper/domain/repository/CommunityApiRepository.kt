package com.example.umc_stepper.domain.repository

import com.example.umc_stepper.base.BaseResponse
import com.example.umc_stepper.domain.model.response.AiVideoInfo
import com.example.umc_stepper.domain.model.response.post_controller.ScrapResponse
import kotlinx.coroutines.flow.Flow

interface CommunityApiRepository {
    suspend fun postCommitScrap(postId : Int) : Flow<BaseResponse<ScrapResponse>>
}