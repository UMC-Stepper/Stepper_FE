package com.example.umc_stepper.domain.model.response.exercise_card_controller

import com.example.umc_stepper.domain.model.response.my_exercise_controller.CheckExerciseResponse

data class ExerciseCardResponse(
    val id: Int,
    val date: String,
    val week: String,
    val hour: Int,
    val minute: Int,
    val second: Int,
    val bodyPart: String,
    val materials: String,
    val stepList: List<ExerciseStepResponse>,
)

data class ExerciseStepResponse(
    val stepId: Int = 0,
    val step: Int = 0,
    val step_status: Boolean = false,
    val myExercise: CheckExerciseResponse? = null
)