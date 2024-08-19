package com.example.umc_stepper.domain.model.response.post_controller

//get 게시글 목록 조회
class ApiResponseListPostViewResponse : ArrayList<ApiResponseListPostViewResponseItem>()

data class ApiResponseListPostViewResponseItem(
    val id: Int =0 ,
    val profileImageUrl: String ="",
    val title: String="",
    val body: String ="",
    val authorEmail: String ="",
    val bodyPart: String? ="",
    val likes: String="",
    val scraps: String="",
    val commentsCount: String ="",
    val subCategory: String?="",
    val weeklyMissionTitle: String="",
    val createdAt: String ="",
    val updatedAt: String="",
    val imageList : List<ImageCard> = listOf()
)