package com.example.umc_stepper.domain.model.request.post_controller

data class PostEditDto(
    val body: String = "",
    val bodyPart: String = "",
    val imageUrl: String = "",
    val subCategory: String = "",
    val title: String = "",
    val weeklyMissionId: Int = 0
)