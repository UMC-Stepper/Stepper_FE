package com.example.umc_stepper.domain.model.response.my_exercise_controller

data class CheckExerciseResponse(
    val exerciseId: Int = 0,
    val url: String = "",
    val video_title: String = "",
    val video_image: String = "",
    val channel_name: String = "",
)