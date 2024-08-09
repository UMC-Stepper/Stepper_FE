package com.example.umc_stepper.data.source.main

import android.util.Log
import com.example.umc_stepper.base.BaseListResponse
import com.example.umc_stepper.base.BaseResponse
import com.example.umc_stepper.data.remote.MainApi
import com.example.umc_stepper.domain.model.request.member_controller.LogInDto
import com.example.umc_stepper.domain.model.request.rate_diary_controller.RateDiaryDto
import com.example.umc_stepper.domain.model.request.member_controller.UserDto
import com.example.umc_stepper.domain.model.response.BadgeResponseItem
import com.example.umc_stepper.domain.model.response.rate_diary_controller.RateDiaryResponse
import com.example.umc_stepper.domain.model.response.rate_diary_controller.RateDiaryResult
import com.example.umc_stepper.domain.model.response.member_controller.UserResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MainApiDataSource @Inject constructor(
    private val mainApi: MainApi
) {
    fun postSignUpInfo(userDto: UserDto) : Flow<BaseResponse<UserResponse>> = flow{
        val result = mainApi.postSignUpInfo(userDto)
        emit(result)
    }.catch {
        Log.e("Post SignUp Failure", it.message.toString())
    }

    fun postLogOutInfo() : Flow<BaseResponse<Any>> = flow{
        val result = mainApi.postLogOutInfo()
        emit(result)
    }.catch {
        Log.e("Post LogOut Failure", it.message.toString())
    }

    fun postLogInInfo(logInDto: LogInDto) : Flow<BaseResponse<String>> = flow{
        val result = mainApi.postLogInInfo(logInDto)
        emit(result)
    }.catch {
        Log.e("Post Login Failure", it.message.toString())
    }

    fun getUserInfo() : Flow<BaseResponse<UserResponse>> = flow{
        val result = mainApi.getUserInfo()
        emit(result)
    }.catch {
        Log.e("Get User Failure", it.message.toString())
    }

    fun deleteExit() : Flow<BaseResponse<Any>> = flow{
        val result = mainApi.deleteExit()
        emit(result)
    }.catch {
        Log.e("Get User Failure", it.message.toString())
    }

    fun postRateDiaryEdit(rateDiaryDto: RateDiaryDto) : Flow<BaseResponse<RateDiaryResult>> = flow{
        val result = mainApi.postRateDiaryEdit(rateDiaryDto)
        emit(result)
    }.catch {
        Log.e("Post RateDiary Edit Failure", it.message.toString())
    }

    fun getRateDiaryConfirm() : Flow<BaseResponse<List<RateDiaryResponse>>> = flow {
        val result = mainApi.getRateDiaryConfirm()
        emit(result)
    }.catch {
        Log.e("Get RateDiary Confirm Failure", it.message.toString())
    }

    fun getBadge(): Flow<BaseListResponse<BadgeResponseItem>> = flow {
        val result = mainApi.getBadge()
        emit(result)
    }.catch {
        Log.e("Get Badge Failure", it.message.toString())
    }

}

