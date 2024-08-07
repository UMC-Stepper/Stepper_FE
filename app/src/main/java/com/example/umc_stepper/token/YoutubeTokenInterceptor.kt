package com.example.umc_stepper.token

import android.util.Log
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class YoutubeTokenInterceptor @Inject constructor(
    private val tokenManager: TokenManager
) : Interceptor {
    companion object {
        const val HEADER_AUTHORIZATION = "Authorization"
        const val TOKEN_TYPE = "Bearer"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            tokenManager.getYoutubeToken().firstOrNull() // Flow에서 첫 번째 값을 가져옴
        }
        val requestBuilder = chain.request().newBuilder()
        if (token != null) {
            requestBuilder.addHeader(HEADER_AUTHORIZATION, "$TOKEN_TYPE $token")
            Log.d("토큰ㄴ","$TOKEN_TYPE $token")
        }
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}
