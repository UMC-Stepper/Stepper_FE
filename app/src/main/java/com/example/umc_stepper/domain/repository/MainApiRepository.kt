package com.example.umc_stepper.domain.repository

import com.example.umc_stepper.base.BaseResponse
import com.example.umc_stepper.domain.model.request.member_controller.LogInDto
import com.example.umc_stepper.domain.model.request.rate_diary_controller.RateDiaryDto
import com.example.umc_stepper.domain.model.request.member_controller.UserDto
import com.example.umc_stepper.domain.model.response.rate_diary_controller.RateDiaryResponse
import com.example.umc_stepper.domain.model.response.rate_diary_controller.RateDiaryResult
import com.example.umc_stepper.domain.model.response.member_controller.UserResponse
import kotlinx.coroutines.flow.Flow

interface MainApiRepository {
    suspend fun postLogInInfo(logInDto: LogInDto) : Flow<BaseResponse<String>>
    suspend fun postLogOutInfo() : Flow<BaseResponse<Any>>
    suspend fun postSignUpInfo(userDto: UserDto) : Flow<BaseResponse<UserResponse>>
    suspend fun getUserInfo() : Flow<BaseResponse<UserResponse>>
    suspend fun deleteExit() : Flow<BaseResponse<Any>>
    suspend fun postRateDiaryEdit(rateDiaryDto: RateDiaryDto) : Flow<BaseResponse<RateDiaryResult>>
    suspend fun getRateDiaryConfirm() : Flow<BaseResponse<List<RateDiaryResponse>>>


}