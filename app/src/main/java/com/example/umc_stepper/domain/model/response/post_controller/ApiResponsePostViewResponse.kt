package com.example.umc_stepper.domain.model.response.post_controller

import com.example.umc_stepper.domain.model.response.ImageResponse

//게시글 상세 조회 GET
data class ApiResponsePostViewResponse(
    val id: Int = 0,
    val profileImageUrl : String,
    val title: String = "",
    val body: String = "",
    val authorEmail: String = "",
    val bodyPart: String? = "",
    val likes: Int = 0,
    val scraps: Int = 0,
    val commentsCount: Int = 0,
    val subCategory: String? = "",
    val weeklyMissionTitle: String = "",
    val createdAt: String = "",
    val updatedAt: String = "",
    val imageList: List<ImageResponse>
)