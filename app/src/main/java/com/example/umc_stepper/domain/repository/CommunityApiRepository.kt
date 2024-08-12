package com.example.umc_stepper.domain.repository

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
import kotlinx.coroutines.flow.Flow

interface CommunityApiRepository {
    suspend fun postCommitScrap(postId: Int): Flow<BaseResponse<ScrapResponse>>
    suspend fun postLikeEdit(postId: Int): Flow<BaseResponse<LikeResponse>>
    suspend fun deleteCancelLike(postId: Int): Flow<BaseResponse<String>>
    suspend fun postEditPost(): Flow<BaseResponse<ApiResponsePostResponse>>
    suspend fun getDetailPost(postId: Int): Flow<BaseResponse<ApiResponsePostViewResponse>>
    suspend fun getDetailPostList(categoryName: String): Flow<BaseListResponse<ApiResponseListPostViewResponseItem>>

    //내가 작성한 글 조회
    suspend fun getCommunityMyPosts(): Flow<BaseListResponse<CommunityMyPostsResponseItem>>

    //내가 작성한 댓글 조회
    suspend fun getCommunityMyComments(): Flow<BaseListResponse<CommunityMyCommentsResponseItem>>

    //댓글 작성
    suspend fun postCommentWrite(commentWriteDto: CommentWriteDto): Flow<BaseResponse<CommentWriteResponse>>

    // 대댓글 작성
    suspend fun postReply(replyRequestDto: ReplyRequestDto) : Flow<BaseResponse<CommentResponse>>

    //댓글 조회
    suspend fun getComment(postId: Int): Flow<BaseListResponse<CommentResponseItem>>
}



