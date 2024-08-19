package com.example.umc_stepper.domain.model.response.post_controller

class CommunityMyCommentsResponse : ArrayList<CommunityMyCommentsResponseItem>()

data class CommunityMyCommentsResponseItem(
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

data class ImageCard(
    val id : Int = 0,
    val imageUrl: String = ""
)