package com.example.umc_stepper.data.source.youtube

import com.example.umc_stepper.data.source.fastapi.FastApiDataSource
import com.example.umc_stepper.domain.model.response.YouTubeVideo
import com.example.umc_stepper.domain.repository.YoutubeApiRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class YoutubeRepositoryImpl @Inject constructor(
    private val dataSource: YoutubeDataSource
) : YoutubeApiRepository{
    override suspend fun getYoutubeDetail(
        part: String,
        id: String,
        apiKey: String
    ): Flow<YouTubeVideo> = dataSource.getYoutubeDetail(part, id, apiKey)

}
