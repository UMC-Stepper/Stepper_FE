package com.example.umc_stepper.data.remote

import com.example.umc_stepper.base.BaseListResponse
import com.example.umc_stepper.base.BaseResponse
import com.example.umc_stepper.domain.model.request.AiVideoDto
import com.example.umc_stepper.domain.model.response.AiVideoInfo
import com.example.umc_stepper.domain.model.response.post_controller.ApiResponseListPostViewResponseItem
import com.example.umc_stepper.domain.model.response.post_controller.ApiResponsePostResponse
import com.example.umc_stepper.domain.model.response.post_controller.ApiResponsePostViewResponse
import com.example.umc_stepper.domain.model.response.post_controller.LikeResponse
import com.example.umc_stepper.domain.model.response.post_controller.ScrapResponse
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CommunityApi {
    //스크랩 등록
    @POST("/api/community/{postId}/scrap")
    suspend fun postCommitScrap(
        @Path("postId") postId: Int
    ): BaseResponse<ScrapResponse>

    //스크랩 취소
    @DELETE("/api/community/{postId}/scrap")
    suspend fun deleteCancelScrap(
        @Path("postId") postId : Int
    ) : BaseResponse<String>

    //좋아요 등록
    @POST("/api/community/{postId}/like")
    suspend fun postLikeEdit(
        @Path("postId") postId: Int
    ) : BaseResponse<LikeResponse>

    //좋아요 취소
    @DELETE("/api/community/{postId}/like")
    suspend fun deleteCancelLike(
        @Path("postId") postId: Int
    ) : BaseResponse<String>

    //사용자 게시글 작성
    @POST("/api/community/write")
    suspend fun postEditPost(
    ): BaseResponse<ApiResponsePostResponse>

    //게시글 상세 조회
    @GET("/api/community/{postId}")
    suspend fun getDetailPost(
        @Path("postId") postId: Int
    ): BaseResponse<ApiResponsePostViewResponse>

    //게시글 목록 조회
    @GET("/api/community/{categoryName}/posts")
    suspend fun getDetailPostList(
        @Path("categoryName") categoryName : String
    ): BaseListResponse<ApiResponseListPostViewResponseItem>



}