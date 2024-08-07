package com.example.umc_stepper.domain.model.local

data class ExerciseState(
    val id: Int,
    val bodyPart: String?,
    val steps: List<ExerciseStep>,
    val isSuccess: Boolean = false,
    val code: String = "",
    val message: String = ""
)

data class ExerciseStep(
    val step: Int,
    val stepStatus: Boolean
)