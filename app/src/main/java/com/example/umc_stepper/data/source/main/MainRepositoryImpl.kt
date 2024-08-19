package com.example.umc_stepper.data.source.main

import com.example.umc_stepper.base.BaseListResponse
import com.example.umc_stepper.base.BaseResponse
import com.example.umc_stepper.domain.model.request.member_controller.LogInDto
import com.example.umc_stepper.domain.model.request.rate_diary_controller.RateDiaryDto
import com.example.umc_stepper.domain.model.request.member_controller.UserDto
import com.example.umc_stepper.domain.model.response.BadgeResponseItem
import com.example.umc_stepper.domain.model.response.rate_diary_controller.RateDiaryResponse
import com.example.umc_stepper.domain.model.response.rate_diary_controller.RateDiaryResult
import com.example.umc_stepper.domain.model.response.member_controller.UserResponse
import com.example.umc_stepper.domain.repository.MainApiRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val dataSource: MainApiDataSource
) : MainApiRepository {

    override suspend fun postLogInInfo(logInDto: LogInDto): Flow<BaseResponse<String>>
    = dataSource.postLogInInfo(logInDto)

    override suspend fun postLogOutInfo(): Flow<BaseResponse<Any>>
    = dataSource.postLogOutInfo()

    override suspend fun postSignUpInfo(userDto: RequestBody,profileImage : MultipartBody.Part): Flow<BaseResponse<UserResponse>>
    = dataSource.postSignUpInfo(userDto,profileImage)

    override suspend fun getUserInfo(): Flow<BaseResponse<UserResponse>>
    = dataSource.getUserInfo()

    override suspend fun deleteExit(): Flow<BaseResponse<Any>>
    = dataSource.deleteExit()

    override suspend fun postRateDiaryEdit(image : MultipartBody.Part,rateDiaryDto: RequestBody) : Flow<BaseResponse<RateDiaryResult>>
    = dataSource.postRateDiaryEdit(image, rateDiaryDto)
    override suspend fun getRateDiaryConfirm() : Flow<BaseResponse<List<RateDiaryResponse>>>
    = dataSource.getRateDiaryConfirm()

    override suspend fun getBadge(): Flow<BaseListResponse<BadgeResponseItem>>
    = dataSource.getBadge()

}