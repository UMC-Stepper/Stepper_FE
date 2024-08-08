package com.example.umc_stepper.domain.model.response

import com.example.umc_stepper.utils.enums.bodyPartType

data class ExerciseCardStatusResponseDto(
    val date:String ="",
    val status: Boolean = false,
    val bodyPartType: bodyPartType
)
