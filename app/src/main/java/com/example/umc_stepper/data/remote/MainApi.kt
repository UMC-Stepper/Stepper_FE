package com.example.umc_stepper.data.remote

import com.example.umc_stepper.base.BaseListResponse
import com.example.umc_stepper.base.BaseResponse
import com.example.umc_stepper.domain.model.request.member_controller.LogInDto
import com.example.umc_stepper.domain.model.request.rate_diary_controller.RateDiaryDto
import com.example.umc_stepper.domain.model.request.member_controller.UserDto
import com.example.umc_stepper.domain.model.response.BadgeResponse
import com.example.umc_stepper.domain.model.response.BadgeResponseItem
import com.example.umc_stepper.domain.model.response.rate_diary_controller.RateDiaryResponse
import com.example.umc_stepper.domain.model.response.rate_diary_controller.RateDiaryResult
import com.example.umc_stepper.domain.model.response.member_controller.UserResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface MainApi {
    @Multipart
    @POST("/api/members/signup")
    suspend fun postSignUpInfo(
        @Part("data") userDto: RequestBody,
        @Part profileImage: MultipartBody.Part,
    ): BaseResponse<UserResponse>

    @POST("/api/members/logout")
    suspend fun postLogOutInfo(
    ): BaseResponse<Any>

    @POST("/api/members/login")
    suspend fun postLogInInfo(
        @Body logInDto: LogInDto
    ): BaseResponse<String>

    @GET("/api/members/info")
    suspend fun getUserInfo(
    ): BaseResponse<UserResponse>

    @DELETE("/api/members/delete")
    suspend fun deleteExit(
    ): BaseResponse<Any>

    @Multipart
    @POST("/api/RateDiary/write")
    suspend fun postRateDiaryEdit(
        @Part image: MultipartBody.Part,
        @Part("request") rateDiaryDto: RequestBody
    ): BaseResponse<RateDiaryResult>

    @GET("/api/RateDiary/check")
    suspend fun getRateDiaryConfirm(
    ): BaseResponse<List<RateDiaryResponse>>

    //뱃지 조회
    @GET("/api/badge")
    suspend fun getBadge(
    ): BaseListResponse<BadgeResponseItem>

}