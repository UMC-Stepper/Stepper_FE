package com.example.umc_stepper.data.source.community

import android.util.Log
import com.example.umc_stepper.base.BaseResponse
import com.example.umc_stepper.data.remote.CommunityApi
import com.example.umc_stepper.data.remote.FastApi
import com.example.umc_stepper.domain.model.request.AiVideoDto
import com.example.umc_stepper.domain.model.response.AiVideoInfo
import com.example.umc_stepper.domain.model.response.post_controller.ScrapResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CommunityApiDataSource @Inject constructor(
    private val communityApi: CommunityApi
){
    fun postCommitScrap(postId : Int) : Flow<BaseResponse<ScrapResponse>> = flow{
        val result = communityApi.postCommitScrap(postId)
        emit(result)
    }.catch {
        Log.e("Post Commit Scrap Failure", it.message.toString())

    }
}
