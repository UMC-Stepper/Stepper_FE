package com.example.umc_stepper.data.source.main

import com.example.umc_stepper.base.BaseResponse
import com.example.umc_stepper.domain.model.request.member_controller.LogInDto
import com.example.umc_stepper.domain.model.request.rate_diary_controller.RateDiaryDto
import com.example.umc_stepper.domain.model.request.member_controller.UserDto
import com.example.umc_stepper.domain.model.response.rate_diary_controller.RateDiaryResponse
import com.example.umc_stepper.domain.model.response.rate_diary_controller.RateDiaryResult
import com.example.umc_stepper.domain.model.response.member_controller.UserResponse
import com.example.umc_stepper.domain.repository.MainApiRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val dataSource: MainApiDataSource
) : MainApiRepository {

    override suspend fun postLogInInfo(logInDto: LogInDto): Flow<BaseResponse<String>>
    = dataSource.postLogInInfo(logInDto)

    override suspend fun postLogOutInfo(): Flow<BaseResponse<Any>>
    = dataSource.postLogOutInfo()

    override suspend fun postSignUpInfo(userDto: UserDto): Flow<BaseResponse<UserResponse>>
    = dataSource.postSignUpInfo(userDto)

    override suspend fun getUserInfo(): Flow<BaseResponse<UserResponse>>
    = dataSource.getUserInfo()

    override suspend fun deleteExit(): Flow<BaseResponse<Any>>
    = dataSource.deleteExit()

    override suspend fun postRateDiaryEdit(rateDiaryDto: RateDiaryDto) : Flow<BaseResponse<RateDiaryResult>>
    = dataSource.postRateDiaryEdit(rateDiaryDto)
    override suspend fun getRateDiaryConfirm() : Flow<BaseResponse<List<RateDiaryResponse>>>
    = dataSource.getRateDiaryConfirm()

}