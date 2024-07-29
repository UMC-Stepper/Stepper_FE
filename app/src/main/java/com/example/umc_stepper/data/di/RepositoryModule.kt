
package com.example.umc_stepper.data.di

import com.example.umc_stepper.data.source.fastapi.FastApiDataSource
import com.example.umc_stepper.data.source.fastapi.FastApiRepositoryImpl
import com.example.umc_stepper.data.source.youtube.YoutubeDataSource
import com.example.umc_stepper.data.source.youtube.YoutubeRepositoryImpl
import com.example.umc_stepper.domain.repository.FastApiRepository
import com.example.umc_stepper.domain.repository.YoutubeApiRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {


    @Singleton
    @Provides
    fun provideFastApiRepository(fastApiDataSource: FastApiDataSource): FastApiRepository =
        FastApiRepositoryImpl(fastApiDataSource)

    @Singleton
    @Provides
    fun provideYoutubeApiRepository(youtubeApiDataSource: YoutubeDataSource): YoutubeApiRepository =
        YoutubeRepositoryImpl(youtubeApiDataSource)


}