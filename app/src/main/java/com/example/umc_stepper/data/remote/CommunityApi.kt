package com.example.umc_stepper.data.remote

import com.example.umc_stepper.base.BaseResponse
import com.example.umc_stepper.domain.model.request.AiVideoDto
import com.example.umc_stepper.domain.model.response.AiVideoInfo
import com.example.umc_stepper.domain.model.response.post_controller.ScrapResponse
import com.example.umc_stepper.domain.model.response.post_controller.CommunityMyPostsResponse
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

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

}