package com.example.umc_stepper.domain.mapper

import com.example.umc_stepper.base.BaseListResponse
import com.example.umc_stepper.domain.model.local.ExerciseState
import com.example.umc_stepper.domain.model.local.ExerciseStep
import com.example.umc_stepper.domain.model.response.ToDayExerciseResponseDto

fun BaseListResponse<ToDayExerciseResponseDto>.toExerciseStates(): List<ExerciseState> {
    return this.result?.map { dto ->
        val steps = dto.stepList?.map {
            ExerciseStep(step = it.step, stepStatus = it.step_status)
        } ?: emptyList()

        ExerciseState(
            id = dto.id,
            bodyPart = dto.bodyPart,
            steps = steps,
            isSuccess = this.isSuccess,
            code = this.code,
            message = this.message
        )
    } ?: emptyList()
}
