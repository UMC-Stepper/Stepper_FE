package com.example.umc_stepper.data.source.stepper

import com.example.umc_stepper.base.BaseListResponse
import com.example.umc_stepper.base.BaseResponse
import com.example.umc_stepper.domain.model.Time
import com.example.umc_stepper.domain.model.request.exercise_card_controller.ExerciseCardRequestDto
import com.example.umc_stepper.domain.model.response.exercise_card_controller.ExerciseCardResponse
import com.example.umc_stepper.domain.model.response.more_exercise_controller.TimeResponse
import com.example.umc_stepper.domain.repository.StepperApiRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StepperApiRepositoryImpl @Inject constructor(
    private val dataSource: StepperApiDataSource
) : StepperApiRepository {
    override suspend fun postMoreExercise(time: Time): Flow<BaseResponse<TimeResponse>>
    = dataSource.postMoreExercise(time)
    override suspend fun getMoreExercise(date: String): Flow<BaseListResponse<TimeResponse>>
    = dataSource.getMoreExercise(date)

    override suspend fun putEditExerciseCard(
        exerciseId: Int,
        exerciseCardRequestDto: ExerciseCardRequestDto
    ): Flow<BaseListResponse<ExerciseCardResponse>> = dataSource.putEditExerciseCard(exerciseId, exerciseCardRequestDto)

    override suspend fun postEditExerciseStep(stepId: Int): Flow<BaseResponse<Any>> =
        dataSource.postEditExerciseStep(stepId)

}