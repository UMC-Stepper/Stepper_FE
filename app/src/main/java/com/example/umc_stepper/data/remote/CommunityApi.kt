package com.example.umc_stepper.data.remote

import com.example.umc_stepper.domain.model.request.AiVideoDto
import com.example.umc_stepper.domain.model.response.AiVideoInfo
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param
import retrofit2.http.Body
import retrofit2.http.POST

interface CommunityApi {
//스크랩 등록
    @POST("/api/community/{postId}/scrap")
    fun postCommitScrap(
        @Param()
    )

}