package com.example.umc_stepper.domain.model.response.post_controller

class CommunityMyCommentsResponse : ArrayList<CommunityMyCommentsResponseItem>()

data class CommunityMyCommentsResponseItem(
    val authorEmail: String ="",
    val body: String ="",
    val bodyPart: String ="",
    val commentsCount: Int =0,
    val createdAt: String ="",
    val id: Int =0 ,
    val imageUrl: String ="",
    val likes: Int=0,
    val scraps: Int=0,
    val subCategory: String="",
    val title: String="",
    val updatedAt: String="",
    val weeklyMissionTitle: String=""
)