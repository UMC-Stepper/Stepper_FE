package com.example.umc_stepper.domain.repository

import com.example.umc_stepper.base.BaseListResponse
import com.example.umc_stepper.base.BaseResponse
import com.example.umc_stepper.domain.model.Time
import com.example.umc_stepper.domain.model.request.exercise_card_controller.ExerciseCardRequestDto
import com.example.umc_stepper.domain.model.response.exercise_card_controller.ExerciseCardResponse
import com.example.umc_stepper.domain.model.response.more_exercise_controller.TimeResponse
import kotlinx.coroutines.flow.Flow

interface StepperApiRepository {

    suspend fun postMoreExercise(time: Time): Flow<BaseResponse<TimeResponse>>
    suspend fun getMoreExercise(date : String): Flow<BaseListResponse<TimeResponse>>
    suspend fun putEditExerciseCard(exerciseId: Int, exerciseCardRequestDto: ExerciseCardRequestDto): Flow<BaseListResponse<ExerciseCardResponse>>
    suspend fun postEditExerciseStep(stepId: Int): Flow<BaseResponse<Any>>
}