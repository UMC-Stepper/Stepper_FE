package com.example.umc_stepper.data.source.main

import android.util.Log
import com.example.umc_stepper.base.BaseListResponse
import com.example.umc_stepper.base.BaseResponse
import com.example.umc_stepper.data.remote.MainApi
import com.example.umc_stepper.domain.model.request.FCMNotificationRequestDto
import com.example.umc_stepper.domain.model.request.member_controller.LogInDto
import com.example.umc_stepper.domain.model.response.BadgeResponseItem
import com.example.umc_stepper.domain.model.response.member_controller.UserResponse
import com.example.umc_stepper.domain.model.response.rate_diary_controller.RateDiaryResponse
import com.example.umc_stepper.domain.model.response.rate_diary_controller.RateDiaryResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import javax.inject.Inject

class MainApiDataSource @Inject constructor(
    private val mainApi: MainApi
) {
    fun postSignUpInfo(
        userDto: RequestBody,
        profileImage: MultipartBody.Part
    ): Flow<BaseResponse<UserResponse>> = flow {
        try {
        val result = mainApi.postSignUpInfo(userDto, profileImage)
        emit(result)
    } catch (e: HttpException) {
            val errorResponse = e.response()?.let { it }
            Log.e("Get Community MyScraps Failure", "HTTP Error: ${errorResponse?.errorBody()?.string()}")

            emit(BaseResponse(
                isSuccess = errorResponse!!.isSuccessful,
                code = errorResponse.code().toString(),
                message = errorResponse.message().toString(),
                result =null)
            )
        }
    }



    fun postLogOutInfo(): Flow<BaseResponse<Any>> = flow {
        val result = mainApi.postLogOutInfo()
        emit(result)
    }.catch {
        Log.e("Post LogOut Failure", it.message.toString())
    }

    fun postLogInInfo(logInDto: LogInDto): Flow<BaseResponse<String>> = flow {
        val result = mainApi.postLogInInfo(logInDto)
        emit(result)
    }.catch {
        Log.e("Post Login Failure", it.message.toString())
    }

    fun getUserInfo(): Flow<BaseResponse<UserResponse>> = flow {
        val result = mainApi.getUserInfo()
        emit(result)
    }.catch {
        Log.e("Get User Failure", it.message.toString())
    }

    fun deleteExit(): Flow<BaseResponse<Any>> = flow {
        val result = mainApi.deleteExit()
        emit(result)
    }.catch {
        Log.e("Get User Failure", it.message.toString())
    }

    fun postRateDiaryEdit(
        image: MultipartBody.Part,
        rateDiaryDto: RequestBody
    ): Flow<BaseResponse<RateDiaryResult>> = flow {
        val result = mainApi.postRateDiaryEdit(image, rateDiaryDto)
        emit(result)
    }.catch {
        Log.e("Post RateDiary Edit Failure", it.message.toString())
    }

    fun getRateDiaryConfirm(): Flow<BaseResponse<List<RateDiaryResponse>>> = flow {
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

    fun getFcm(fCMNotificationRequestDto: FCMNotificationRequestDto): Flow<String> = flow {
        val result = mainApi.getFcm(fCMNotificationRequestDto)
        emit(result)
    }.catch {
        Log.e("Get Badge Failure", it.message.toString())
    }

}

