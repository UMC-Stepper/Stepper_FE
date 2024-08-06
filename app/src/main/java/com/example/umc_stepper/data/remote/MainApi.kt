package com.example.umc_stepper.data.remote

import com.example.umc_stepper.base.BaseResponse
import com.example.umc_stepper.domain.model.request.AiVideoDto
import com.example.umc_stepper.domain.model.request.LogInDto
import com.example.umc_stepper.domain.model.request.UserDto
import com.example.umc_stepper.domain.model.response.AiVideoInfo
import com.example.umc_stepper.domain.model.response.UserResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import java.util.Objects

interface MainApi {
    @POST("/api/members/signup")
    suspend fun postSignUpInfo(
        @Body userDto: UserDto
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


}