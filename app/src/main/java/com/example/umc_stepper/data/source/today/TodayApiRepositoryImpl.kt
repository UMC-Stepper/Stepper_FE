package com.example.umc_stepper.data.source.today

import com.example.umc_stepper.base.BaseListResponse
import com.example.umc_stepper.base.BaseResponse
import com.example.umc_stepper.domain.model.response.CheckExerciseResponseDTO
import com.example.umc_stepper.domain.model.response.ExerciseCardStatusResponseDto
import com.example.umc_stepper.domain.model.response.ExerciseCardWeekResponseDto
import com.example.umc_stepper.domain.model.response.ToDayExerciseResponseDto
import com.example.umc_stepper.domain.repository.TodayApiRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TodayApiRepositoryImpl @Inject constructor(
    private val dataSource: TodayApiDataSource
) : TodayApiRepository {

    override suspend fun getTodayExerciseState(date: String): Flow<BaseListResponse<ToDayExerciseResponseDto>> =
        dataSource.getTodayExerciseState(date)

    override suspend fun getMyExercise(bodyPart: String): Flow<BaseListResponse<CheckExerciseResponseDTO>> =
        dataSource.getMyExercise(bodyPart)

    override suspend fun getExerciseMonthCheck(month: Int): Flow<BaseListResponse<ExerciseCardStatusResponseDto>> =
        dataSource.getExerciseMonthCheck(month)

    override suspend fun getExerciseCheckDate(bodyPart: String): Flow<BaseListResponse<ExerciseCardWeekResponseDto>> =
        dataSource.getExerciseCheckDate(bodyPart)


}