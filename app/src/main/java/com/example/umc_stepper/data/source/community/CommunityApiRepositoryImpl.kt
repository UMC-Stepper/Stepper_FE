package com.example.umc_stepper.data.source.community

import com.example.umc_stepper.base.BaseListResponse
import com.example.umc_stepper.base.BaseResponse
import com.example.umc_stepper.data.source.fastapi.FastApiDataSource
import com.example.umc_stepper.domain.model.request.AiVideoDto
import com.example.umc_stepper.domain.model.response.AiVideoInfo
import com.example.umc_stepper.domain.model.response.post_controller.ApiResponseListPostViewResponseItem
import com.example.umc_stepper.domain.model.response.post_controller.ApiResponsePostResponse
import com.example.umc_stepper.domain.model.response.post_controller.ApiResponsePostViewResponse
import com.example.umc_stepper.domain.model.response.post_controller.LikeResponse
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

    override suspend fun postLikeEdit(postId: Int): Flow<BaseResponse<LikeResponse>>
    = dataSource.postLikeEdit(postId)

    override suspend fun deleteCancelLike(postId: Int): Flow<BaseResponse<String>>
    = dataSource.deleteCancelLike(postId)

    override suspend fun postEditPost(): Flow<BaseResponse<ApiResponsePostResponse>>
    = dataSource.postEditPost()

    override suspend fun getDetailPost(postId: Int): Flow<BaseResponse<ApiResponsePostViewResponse>>
    = dataSource.getDetailPost(postId)

    override suspend fun getDetailPostList(categoryName: String): Flow<BaseListResponse<ApiResponseListPostViewResponseItem>>
    = dataSource.getDetailPostList(categoryName)
}