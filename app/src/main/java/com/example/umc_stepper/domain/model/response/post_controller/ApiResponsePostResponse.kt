package com.example.umc_stepper.domain.model.response.post_controller
//사용자 게시글 작성 POST
data class ApiResponsePostResponse(
    val authorEmail: String = "",
    val body: String = "",
    val bodyPart: String = "",
    val createdAt: String = "",
    val id: Int = 0,
    val imageUrl: String = "",
    val subCategory: String = "",
    val title: String = "",
    val updatedAt: String = "",
    val weeklyMissionTitle: String = ""
)