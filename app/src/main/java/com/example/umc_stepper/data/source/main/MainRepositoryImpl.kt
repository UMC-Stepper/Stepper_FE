package com.example.umc_stepper.data.source.main

import com.example.umc_stepper.base.BaseResponse
import com.example.umc_stepper.domain.model.request.AiVideoDto
import com.example.umc_stepper.domain.model.request.LogInDto
import com.example.umc_stepper.domain.model.request.UserDto
import com.example.umc_stepper.domain.model.response.AiVideoInfo
import com.example.umc_stepper.domain.model.response.UserResponse
import com.example.umc_stepper.domain.repository.FastApiRepository
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

}