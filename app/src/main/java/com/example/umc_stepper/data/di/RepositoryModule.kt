
package com.example.umc_stepper.data.di

import com.example.umc_stepper.data.source.community.CommunityApiDataSource
import com.example.umc_stepper.data.source.community.CommunityApiRepositoryImpl
import com.example.umc_stepper.data.source.fastapi.FastApiDataSource
import com.example.umc_stepper.data.source.fastapi.FastApiRepositoryImpl
import com.example.umc_stepper.data.source.main.MainApiDataSource
import com.example.umc_stepper.data.source.main.MainRepositoryImpl
import com.example.umc_stepper.data.source.stepper.StepperApiDataSource
import com.example.umc_stepper.data.source.stepper.StepperApiRepositoryImpl
import com.example.umc_stepper.data.source.today.TodayApiDataSource
import com.example.umc_stepper.data.source.today.TodayApiRepositoryImpl
import com.example.umc_stepper.data.source.youtube.YoutubeDataSource
import com.example.umc_stepper.data.source.youtube.YoutubeRepositoryImpl
import com.example.umc_stepper.domain.repository.CommunityApiRepository
import com.example.umc_stepper.domain.repository.FastApiRepository
import com.example.umc_stepper.domain.repository.MainApiRepository
import com.example.umc_stepper.domain.repository.StepperApiRepository
import com.example.umc_stepper.domain.repository.TodayApiRepository
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

    @Singleton
    @Provides
    fun provideMainApiRepository(mainApiDataSource: MainApiDataSource) : MainApiRepository =
        MainRepositoryImpl(mainApiDataSource)

    @Singleton
    @Provides
    fun provideTodayApiRepository(todayApiDataSource: TodayApiDataSource) : TodayApiRepository =
        TodayApiRepositoryImpl(todayApiDataSource)

    @Singleton
    @Provides
    fun provideStepperApiRepository(stepperApiDataSource: StepperApiDataSource) : StepperApiRepository =
        StepperApiRepositoryImpl(stepperApiDataSource)

    @Singleton
    @Provides
    fun provideCommunityApiRepository(communityDataSource: CommunityApiDataSource) : CommunityApiRepository =
        CommunityApiRepositoryImpl(communityDataSource)

}