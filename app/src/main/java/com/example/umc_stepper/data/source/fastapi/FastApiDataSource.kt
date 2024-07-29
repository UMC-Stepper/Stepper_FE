package com.example.umc_stepper.data.source.fastapi

import android.util.Log
import com.example.umc_stepper.data.remote.FastApi
import com.example.umc_stepper.domain.model.request.AiVideoDto
import com.example.umc_stepper.domain.model.response.AiVideoInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FastApiDataSource @Inject constructor(
    private val fastApi : FastApi
) {
    fun postAiVideoInfo(alVideoDto: AiVideoDto) : Flow<AiVideoInfo> = flow {
        val result = fastApi.postAiVideoInfo(alVideoDto)
        emit(result)
    }.catch {
        Log.e("Post AIVideo Failure", it.message.toString())
    }
}