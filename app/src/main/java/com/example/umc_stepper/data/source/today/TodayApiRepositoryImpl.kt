package com.example.umc_stepper.data.source.today

import com.example.umc_stepper.base.BaseListResponse
import com.example.umc_stepper.base.BaseResponse
import com.example.umc_stepper.domain.model.request.exercise_card_controller.ExerciseCardRequestDto
import com.example.umc_stepper.domain.model.response.exercise_card_controller.ExerciseCardResponse
import com.example.umc_stepper.domain.model.response.exercise_card_controller.ExerciseCardStatusResponseDto
import com.example.umc_stepper.domain.model.response.exercise_card_controller.ExerciseCardWeekResponseDto
import com.example.umc_stepper.domain.model.response.exercise_card_controller.ToDayExerciseResponseDto
import com.example.umc_stepper.domain.model.response.my_exercise_controller.AddExerciseResponse
import com.example.umc_stepper.domain.model.response.my_exercise_controller.CheckExerciseResponse
import com.example.umc_stepper.domain.repository.TodayApiRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TodayApiRepositoryImpl @Inject constructor(
    private val dataSource: TodayApiDataSource
) : TodayApiRepository {

    override suspend fun postAddExerciseCard(exerciseCardRequestDto: ExerciseCardRequestDto): Flow<BaseResponse<ExerciseCardResponse>> =
        dataSource.postAddExerciseCard(exerciseCardRequestDto)

    override suspend fun postInquiryExerciseCard(exerciseId: Int): Flow<BaseResponse<ExerciseCardResponse>> =
        dataSource.postInquiryExerciseCard(exerciseId)

    override suspend fun getTodayExerciseState(date: String): Flow<BaseListResponse<ToDayExerciseResponseDto>> =
        dataSource.getTodayExerciseState(date)

    override suspend fun getMyExercise(bodyPart: String): Flow<BaseListResponse<CheckExerciseResponse>> =
        dataSource.getMyExercise(bodyPart)

    override suspend fun getExerciseMonthCheck(month: Int): Flow<BaseListResponse<ExerciseCardStatusResponseDto>> =
        dataSource.getExerciseMonthCheck(month)

    override suspend fun postAddMyExercise(addExerciseRequestDto: ExerciseCardRequestDto): Flow<BaseResponse<AddExerciseResponse>> =
        dataSource.postAddMyExercise(addExerciseRequestDto)

    override suspend fun getExerciseCheckDate(bodyPart: String): Flow<BaseListResponse<ExerciseCardWeekResponseDto>> =
        dataSource.getExerciseCheckDate(bodyPart)


}