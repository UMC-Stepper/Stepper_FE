package com.example.umc_stepper.domain.model.request.exercise_card_controller

data class ExerciseCardRequestDto(
    val bodyPart: String,
    var date: String,
    val hour: Int,
    val materials: String,
    val minute: Int,
    val second: Int,
    val stepList: List<ExerciseStepRequestDto>,
    var week: String
)

data class ExerciseStepRequestDto(
    val myExerciseId: Int,
    val step: Int
)