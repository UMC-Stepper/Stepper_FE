package com.example.umc_stepper.domain.model.request.comment_controller

data class ReplyRequestDto (
    val postId: Int = 0,
    val parentCommentId: Int = 0,
    val content: String ="",
    val anonymous: Boolean = false
)