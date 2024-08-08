package com.example.umc_stepper.domain.repository

import com.example.umc_stepper.base.BaseListResponse
import com.example.umc_stepper.base.BaseResponse
import com.example.umc_stepper.domain.model.Time
import com.example.umc_stepper.domain.model.response.TimeResponse
import kotlinx.coroutines.flow.Flow

interface StepperApiRepository {

    suspend fun postMoreExercise(time: Time): Flow<BaseResponse<TimeResponse>>
    suspend fun getMoreExercise(date : String): Flow<BaseListResponse<TimeResponse>>
}