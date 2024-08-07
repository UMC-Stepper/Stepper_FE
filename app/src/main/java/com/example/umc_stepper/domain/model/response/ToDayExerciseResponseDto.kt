package com.example.umc_stepper.domain.model.response

import com.example.umc_stepper.utils.enum.bodyPartType

// 오늘의 운동 진행상태 조회
data class ToDayExerciseResponseDto(
    val id: Int = 0,
    val bodyPart: bodyPartType,
    val stepList: ExerciseStepResponseDto
)

data class ExerciseStepResponseDto(
    val stepId: Int = 0,
    val step: Int = 0,
    val step_status: Boolean,
    val myExercise: CheckExerciseResponseDTO
)

data class 	CheckExerciseResponseDTO(
    val exerciseId: Int = 0,
    val url: String = "",
    val video_title: String = "",
    val video_image: String = "",
    val channel_name: String = "",
)