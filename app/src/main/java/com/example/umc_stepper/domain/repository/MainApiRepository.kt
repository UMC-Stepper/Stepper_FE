package com.example.umc_stepper.domain.repository

import com.example.umc_stepper.base.BaseResponse
import com.example.umc_stepper.domain.model.request.AiVideoDto
import com.example.umc_stepper.domain.model.request.LogInDto
import com.example.umc_stepper.domain.model.request.MoreExerciseRequestDto
import com.example.umc_stepper.domain.model.request.RateDiaryDto
import com.example.umc_stepper.domain.model.request.UserDto
import com.example.umc_stepper.domain.model.response.AiVideoInfo
import com.example.umc_stepper.domain.model.response.MoreExerciseResponseDto
import com.example.umc_stepper.domain.model.response.RateDiaryResponse
import com.example.umc_stepper.domain.model.response.RateDiaryResult
import com.example.umc_stepper.domain.model.response.UserResponse
import kotlinx.coroutines.flow.Flow

interface MainApiRepository {
    suspend fun postLogInInfo(logInDto: LogInDto) : Flow<BaseResponse<String>>
    suspend fun postLogOutInfo() : Flow<BaseResponse<Any>>
    suspend fun postSignUpInfo(userDto: UserDto) : Flow<BaseResponse<UserResponse>>
    suspend fun getUserInfo() : Flow<BaseResponse<UserResponse>>
    suspend fun deleteExit() : Flow<BaseResponse<Any>>
    suspend fun postRateDiaryEdit(rateDiaryDto: RateDiaryDto) : Flow<BaseResponse<RateDiaryResult>>
    suspend fun getRateDiaryConfirm() : Flow<BaseResponse<List<RateDiaryResponse>>>
    suspend fun postMoreExercise(moreExerciseRequestDto: MoreExerciseRequestDto) : Flow<BaseResponse<MoreExerciseResponseDto>>   // 추가 운동 기록 API
}