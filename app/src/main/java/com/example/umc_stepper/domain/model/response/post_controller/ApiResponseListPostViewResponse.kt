package com.example.umc_stepper.domain.model.response.post_controller

//get 게시클 목록 조회
class ApiResponseListPostViewResponse : ArrayList<ApiResponseListPostViewResponseItem>()

data class ApiResponseListPostViewResponseItem(
    val authorEmail: String,
    val body: String,
    val bodyPart: String,
    val commentsCount: Int,
    val createdAt: String,
    val id: Int,
    val imageUrl: String,
    val likes: Int,
    val scraps: Int,
    val subCategory: String,
    val title: String,
    val updatedAt: String,
    val weeklyMissionTitle: String
)