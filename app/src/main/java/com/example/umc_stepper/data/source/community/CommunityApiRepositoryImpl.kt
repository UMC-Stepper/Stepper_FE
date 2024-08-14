package com.example.umc_stepper.data.source.community

import com.example.umc_stepper.base.BaseListResponse
import com.example.umc_stepper.base.BaseResponse
import com.example.umc_stepper.domain.model.request.comment_controller.CommentWriteDto
import com.example.umc_stepper.domain.model.request.comment_controller.ReplyRequestDto
import com.example.umc_stepper.domain.model.response.comment_controller.CommentResponse
import com.example.umc_stepper.domain.model.response.comment_controller.CommentResponseItem
import com.example.umc_stepper.domain.model.response.comment_controller.CommentWriteResponse
import com.example.umc_stepper.domain.model.response.post_controller.ApiResponseListPostViewResponseItem
import com.example.umc_stepper.domain.model.response.post_controller.ApiResponsePostResponse
import com.example.umc_stepper.domain.model.response.post_controller.ApiResponsePostViewResponse
import com.example.umc_stepper.domain.model.response.post_controller.CommunityMyCommentsResponseItem
import com.example.umc_stepper.domain.model.response.post_controller.CommunityMyPostsResponseItem
import com.example.umc_stepper.domain.model.response.post_controller.LikeResponse
import com.example.umc_stepper.domain.model.response.post_controller.ScrapResponse
import com.example.umc_stepper.domain.repository.CommunityApiRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CommunityApiRepositoryImpl @Inject constructor(
    private val dataSource: CommunityApiDataSource
) : CommunityApiRepository {
    override suspend fun postCommitScrap(postId : Int): Flow<BaseResponse<ScrapResponse>> =
        dataSource.postCommitScrap(postId)

    override suspend fun deleteCancelScrap(postId: Int): Flow<BaseResponse<String>> =
        dataSource.deleteCancelScrap(postId)

    override suspend fun getCommunityMyPosts(): Flow<BaseListResponse<CommunityMyPostsResponseItem>> =
        dataSource.getCommunityMyPosts()

    override suspend fun getCommunityMyComments(): Flow<BaseListResponse<CommunityMyCommentsResponseItem>> =
        dataSource.getCommunityMyComments()

    override suspend fun getCommunityMyScraps(): Flow<BaseListResponse<CommunityMyCommentsResponseItem>> =
        dataSource.getCommunityMyScraps()

    override suspend fun postCommentWrite(commentWriteDto: CommentWriteDto): Flow<BaseResponse<CommentWriteResponse>> =
        dataSource.postCommentWrite(commentWriteDto)

    override suspend fun getComment(postId: Int): Flow<BaseListResponse<CommentResponseItem>> =
        dataSource.getComment(postId)

    override suspend fun postReply(replyRequestDto: ReplyRequestDto): Flow<BaseResponse<CommentResponse>> =
        dataSource.postReply(replyRequestDto)


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