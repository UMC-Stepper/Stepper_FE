package com.example.umc_stepper.domain.model.response.exercise_card_controller

import com.example.umc_stepper.utils.enums.DayOfWeek

data class ExerciseCardWeekResponseDto (
    val bodyPart: String? = "",
    val weeks: List<DayOfWeek>? = null
)