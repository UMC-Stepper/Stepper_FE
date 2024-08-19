package com.example.umc_stepper.domain.repository

import com.example.umc_stepper.base.BaseListResponse
import com.example.umc_stepper.base.BaseResponse
import com.example.umc_stepper.domain.model.request.member_controller.LogInDto
import com.example.umc_stepper.domain.model.request.rate_diary_controller.RateDiaryDto
import com.example.umc_stepper.domain.model.request.member_controller.UserDto
import com.example.umc_stepper.domain.model.response.BadgeResponseItem
import com.example.umc_stepper.domain.model.response.rate_diary_controller.RateDiaryResponse
import com.example.umc_stepper.domain.model.response.rate_diary_controller.RateDiaryResult
import com.example.umc_stepper.domain.model.response.member_controller.UserResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface MainApiRepository {
    suspend fun postLogInInfo(logInDto: LogInDto) : Flow<BaseResponse<String>>
    suspend fun postLogOutInfo() : Flow<BaseResponse<Any>>
    suspend fun postSignUpInfo(userDto: RequestBody,profileImage : MultipartBody.Part) : Flow<BaseResponse<UserResponse>>
    suspend fun getUserInfo() : Flow<BaseResponse<UserResponse>>
    suspend fun deleteExit() : Flow<BaseResponse<Any>>
    suspend fun postRateDiaryEdit(image : MultipartBody.Part,rateDiaryDto: RequestBody) : Flow<BaseResponse<RateDiaryResult>>
    suspend fun getRateDiaryConfirm() : Flow<BaseResponse<List<RateDiaryResponse>>>
    suspend fun getBadge(): Flow<BaseListResponse<BadgeResponseItem>>


}