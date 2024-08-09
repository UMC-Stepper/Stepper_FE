package com.example.umc_stepper.data.remote

import com.example.umc_stepper.base.BaseResponse
import com.example.umc_stepper.domain.model.request.AiVideoDto
import com.example.umc_stepper.domain.model.request.comment_controller.CommentWriteDto
import com.example.umc_stepper.domain.model.request.member_controller.LogInDto
import com.example.umc_stepper.domain.model.response.AiVideoInfo
import com.example.umc_stepper.domain.model.response.comment_controller.CommentResponse
import com.example.umc_stepper.domain.model.response.comment_controller.CommentWriteResponse
import com.example.umc_stepper.domain.model.response.post_controller.CommunityMyCommentsResponse
import com.example.umc_stepper.domain.model.response.post_controller.ScrapResponse
import com.example.umc_stepper.domain.model.response.post_controller.CommunityMyPostsResponse
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CommunityApi {
    //스크랩 등록
    @POST("/api/community/{postId}/scrap")
    suspend fun postCommitScrap(
        @Path("postId") postId : Int
    ) : BaseResponse<ScrapResponse>



    //내가 작성한 글 조회
    @GET("/api/community/my_posts")
    suspend fun getCommunityMyPosts(

    ):BaseResponse<CommunityMyPostsResponse>

    //내가 작성한 댓글 조회
    @GET("/api/community/my_comments")
    suspend fun getCommunityMyComments(

    ):BaseResponse<CommunityMyCommentsResponse>

    //댓글 작성
    @POST("/api/comment/write")
    suspend fun postCommentWrite(
        @Body commentWriteDto: CommentWriteDto
    ):BaseResponse<CommentWriteResponse>

    //댓글 조회
    @GET("api/comment/{postId}/comment")
    suspend fun  getComment(
        @Path("postId") postId : Int
    ):BaseResponse<CommentResponse>

}