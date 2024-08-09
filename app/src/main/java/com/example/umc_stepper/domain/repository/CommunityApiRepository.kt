package com.example.umc_stepper.domain.repository

import android.util.Log
import com.example.umc_stepper.base.BaseListResponse
import com.example.umc_stepper.base.BaseResponse
import com.example.umc_stepper.domain.model.response.AiVideoInfo
import com.example.umc_stepper.domain.model.response.post_controller.ApiResponseListPostViewResponseItem
import com.example.umc_stepper.domain.model.response.post_controller.ApiResponsePostResponse
import com.example.umc_stepper.domain.model.response.post_controller.ApiResponsePostViewResponse
import com.example.umc_stepper.domain.model.response.post_controller.LikeResponse
import com.example.umc_stepper.domain.model.response.post_controller.ScrapResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

interface CommunityApiRepository {
    suspend fun postCommitScrap(postId : Int) : Flow<BaseResponse<ScrapResponse>>
    suspend fun postLikeEdit(postId: Int): Flow<BaseResponse<LikeResponse>>
    suspend fun deleteCancelLike(postId: Int): Flow<BaseResponse<String>>
    suspend fun postEditPost(): Flow<BaseResponse<ApiResponsePostResponse>>
    suspend fun getDetailPost(postId: Int): Flow<BaseResponse<ApiResponsePostViewResponse>>
    suspend fun getDetailPostList(categoryName: String): Flow<BaseListResponse<ApiResponseListPostViewResponseItem>>
}



