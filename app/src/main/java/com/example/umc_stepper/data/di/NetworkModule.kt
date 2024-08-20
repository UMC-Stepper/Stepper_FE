package com.example.umc_stepper.data.di

import com.example.umc_stepper.token.AccessTokenInterceptor
import com.example.umc_stepper.token.TokenManager
import com.example.umc_stepper.token.YoutubeTokenInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class ApiRetrofit

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class YoutubeRetrofit

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class MainRetrofit

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Singleton
    @Provides
    fun provideAccessTokenInterceptor(tokenManager: TokenManager): AccessTokenInterceptor {
        return AccessTokenInterceptor(tokenManager)
    }

    @Singleton
    @Provides
    fun provideYoutubeTokenInterceptor(tokenManager: TokenManager): YoutubeTokenInterceptor {
        return YoutubeTokenInterceptor(tokenManager)
    }

    @Provides
    @Singleton
    fun provideConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Singleton
    @Provides
    @Named("defaultOkHttpClient")
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        accessTokenInterceptor: AccessTokenInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(accessTokenInterceptor)
            .build()
    }

    @Singleton
    @Provides
    @Named("defaultFastApiClient")
    fun provideFastApiClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        accessTokenInterceptor: AccessTokenInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(accessTokenInterceptor)
            .build()
    }

    @Singleton
    @Provides
    @Named("defaultYoutubeClient")
    fun provideOkHttpYoutubeClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        youtubeTokenInterceptor: YoutubeTokenInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(youtubeTokenInterceptor)
            .build()
    }


    //fast api
    @ApiRetrofit
    @Singleton
    @Provides
    fun provideRetrofit(
        @Named("defaultYoutubeClient") okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .baseUrl("http://34.47.91.218:8000")
            .build()
    }

    //youtube
    @YoutubeRetrofit
    @Singleton
    @Provides
    fun provideYoutubeRetrofit(
        @Named("defaultYoutubeClient") okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .baseUrl("https://www.googleapis.com/youtube/v3/")
            .build()
    }

    // 서버 배포 주소 http://stepper-dev-env.eba-4gp3sbph.ap-northeast-2.elasticbeanstalk.com/
    // 로컬 주소 http://192.168.186.1:8080/ (집)
    @MainRetrofit
    @Singleton
    @Provides
    fun provideMainRetrofit(
        @Named("defaultFastApiClient") okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .baseUrl("http://stepper-dev-env.eba-4gp3sbph.ap-northeast-2.elasticbeanstalk.com/")
            .build()
    }
}
