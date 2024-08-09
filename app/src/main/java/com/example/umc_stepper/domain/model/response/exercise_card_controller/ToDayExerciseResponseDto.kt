package com.example.umc_stepper.domain.model.response.exercise_card_controller

import com.example.umc_stepper.domain.model.response.my_exercise_controller.CheckExerciseResponse

// 오늘의 운동 진행상태 조회
data class ToDayExerciseResponseDto(
    val id: Int = 0,
    val bodyPart: String = "",
    var stepList: List<ExerciseStepResponse> = listOf()
)