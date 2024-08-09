package com.example.umc_stepper.domain.model.request.my_exercise_controller

data class AddExerciseRequestDto(
    val url: String,
    val body_part: String,
    val video_title: String,
    val channel_name: String,
    val video_image: String,
)
