package com.example.umc_stepper.data.di

import com.example.umc_stepper.data.remote.FastApi
import com.example.umc_stepper.data.remote.MainApi
import com.example.umc_stepper.data.remote.YoutubeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideFastApi(
        @NetworkModule.ApiRetrofit retrofit: Retrofit
    ): FastApi = retrofit.create(FastApi::class.java)

    @Provides
    @Singleton
    fun getVideoDetails(
        @NetworkModule.YoutubeRetrofit retrofit: Retrofit
    ): YoutubeApi = retrofit.create(YoutubeApi::class.java)

    @Provides
    @Singleton
    fun getMainServer(
        @NetworkModule.MainRetrofit retrofit: Retrofit
    ): MainApi = retrofit.create(MainApi::class.java)
}