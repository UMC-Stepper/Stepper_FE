package com.example.umc_stepper.domain.model.request.post_controller

data class PostEditDto(
    val body: String = "",
    val bodyPart: String? = null ,
    val imageUrl: String? = null,
    val subCategory: String? = null,
    val title: String = "",
    val weeklyMissionId: Int? = null
)