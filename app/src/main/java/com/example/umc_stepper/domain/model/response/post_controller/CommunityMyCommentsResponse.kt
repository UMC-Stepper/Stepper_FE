package com.example.umc_stepper.domain.model.response.post_controller

class CommunityMyCommentsResponse : ArrayList<CommunityMyCommentsResponseItem>()

data class CommunityMyCommentsResponseItem(
    val id: Int =0 ,
    val imageUrl: String ="",
    val title: String="",
    val body: String ="",
    val authorEmail: String ="",
    val bodyPart: String ="",
    val likes: Int=0,
    val scraps: Int=0,
    val commentsCount: Int =0,
    val subCategory: String="",
    val weeklyMissionTitle: String="",
    val createdAt: String ="",
    val updatedAt: String=""
)