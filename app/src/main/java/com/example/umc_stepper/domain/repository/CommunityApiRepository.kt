package com.example.umc_stepper.domain.repository

import com.example.umc_stepper.base.BaseListResponse
import com.example.umc_stepper.base.BaseResponse
import com.example.umc_stepper.domain.model.request.comment_controller.CommentWriteDto
import com.example.umc_stepper.domain.model.request.comment_controller.ReplyRequestDto
import com.example.umc_stepper.domain.model.response.WeeklyMissionResponse
import com.example.umc_stepper.domain.model.response.comment_controller.CommentResponseItem
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
    suspend fun deleteCancelScrap(postId: Int): Flow<BaseResponse<String>>
    suspend fun postLikeEdit(postId: Int): Flow<BaseResponse<LikeResponse>>
    suspend fun deleteCancelLike(postId: Int): Flow<BaseResponse<String>>
    suspend fun postEditPost(): Flow<BaseResponse<ApiResponsePostResponse>>
    suspend fun getDetailPost(postId: Int): Flow<BaseResponse<ApiResponsePostViewResponse>>
    suspend fun getDetailPostList(categoryName: String): Flow<BaseListResponse<ApiResponseListPostViewResponseItem>>

    //내가 작성한 글 목록 조회
    suspend fun getCommunityMyPosts(): Flow<BaseListResponse<CommunityMyPostsResponseItem>>

    //내가 작성한 댓글 글 목록 조회
    suspend fun getCommunityMyComments(): Flow<BaseListResponse<CommunityMyCommentsResponseItem>>

    // 위클리 게시글 조회 API
    suspend fun getWeeklyPostList(weeklyMissionId : Int): Flow<BaseListResponse<ApiResponseListPostViewResponseItem>>

    // 주간 미션 조회 API
    suspend fun getWeeklyMission(id : Int): Flow<BaseResponse<WeeklyMissionResponse>>

    //내가 스크랩 한 글 목록 조회
    suspend fun getCommunityMyScraps(): Flow<BaseListResponse<CommunityMyCommentsResponseItem>>

    //댓글 작성
    suspend fun postCommentWrite(commentWriteDto: CommentWriteDto): Flow<BaseResponse<CommentResponseItem>>

    // 대댓글 작성
    suspend fun postReply(replyRequestDto: ReplyRequestDto) : Flow<BaseResponse<CommentResponseItem>>

    //댓글 조회
    suspend fun getComment(postId: Int): Flow<BaseListResponse<CommentResponseItem>>
}



