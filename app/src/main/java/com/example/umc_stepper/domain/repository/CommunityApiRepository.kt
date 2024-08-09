package com.example.umc_stepper.domain.repository

import com.example.umc_stepper.base.BaseListResponse
import com.example.umc_stepper.base.BaseResponse
import com.example.umc_stepper.domain.model.request.comment_controller.CommentWriteDto
import com.example.umc_stepper.domain.model.response.AiVideoInfo
import com.example.umc_stepper.domain.model.response.comment_controller.CommentResponseItem
import com.example.umc_stepper.domain.model.response.comment_controller.CommentWriteResponse
import com.example.umc_stepper.domain.model.response.post_controller.CommunityMyCommentsResponseItem
import com.example.umc_stepper.domain.model.response.post_controller.CommunityMyPostsResponseItem
import com.example.umc_stepper.domain.model.response.post_controller.ScrapResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body

interface CommunityApiRepository {
    suspend fun postCommitScrap(postId : Int) : Flow<BaseResponse<ScrapResponse>>

    //내가 작성한 글 조회
    suspend fun getCommunityMyPosts(): Flow<BaseListResponse<CommunityMyPostsResponseItem>>
    //내가 작성한 댓글 조회
    suspend fun getCommunityMyComments():Flow<BaseListResponse<CommunityMyCommentsResponseItem>>
    //댓글 작성
    suspend fun postCommentWrite(commentWriteDto: CommentWriteDto):Flow<BaseResponse<CommentWriteResponse>>
    //댓글 조회
    suspend fun getComment(postId : Int):Flow<BaseListResponse<CommentResponseItem>>
}