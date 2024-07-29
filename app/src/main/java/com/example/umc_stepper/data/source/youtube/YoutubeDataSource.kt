package com.example.umc_stepper.data.source.youtube

import android.util.Log
import com.example.umc_stepper.data.remote.YoutubeApi
import com.example.umc_stepper.domain.model.response.YouTubeVideo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class YoutubeDataSource @Inject constructor(
    private val youtubeApi: YoutubeApi
) {
    fun getYoutubeDetail(part : String, id : String, apiKey : String) : Flow<YouTubeVideo> = flow {
        val result = youtubeApi.getYoutubeDetail(part, id, apiKey)
        emit(result)
    }.catch {
        Log.e("Get Youtube Failure", it.message.toString())
    }
}
