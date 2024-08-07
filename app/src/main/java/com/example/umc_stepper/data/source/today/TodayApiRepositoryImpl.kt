package com.example.umc_stepper.data.source.today

import com.example.umc_stepper.base.BaseListResponse
import com.example.umc_stepper.base.BaseResponse
import com.example.umc_stepper.domain.model.response.CheckExerciseResponseDTO
import com.example.umc_stepper.domain.model.response.ToDayExerciseResponseDto
import com.example.umc_stepper.domain.repository.TodayApiRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TodayApiRepositoryImpl @Inject constructor(
    private val dataSource: TodayApiDataSource
) : TodayApiRepository {

    override suspend fun getTodayExerciseState(date: String): Flow<BaseListResponse<ToDayExerciseResponseDto>> =
        dataSource.getTodayExerciseState(date)

    override suspend fun getMyExercise(bodyPart: String): Flow<BaseResponse<CheckExerciseResponseDTO>> =
        dataSource.getMyExercise(bodyPart)
}