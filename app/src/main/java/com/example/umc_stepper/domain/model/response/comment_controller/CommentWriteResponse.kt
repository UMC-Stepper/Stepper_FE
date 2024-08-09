package com.example.umc_stepper.domain.model.response.comment_controller

data class CommentWriteResponse(
    val commentId: Int= 0,
    val content: String="",
    val dateTime: String="",
    val memberName: String="",
    val postId: Int=0,
    val profileImage: String=""
)