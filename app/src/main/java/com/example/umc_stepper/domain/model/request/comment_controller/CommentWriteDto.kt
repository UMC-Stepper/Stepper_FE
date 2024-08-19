package com.example.umc_stepper.domain.model.request.comment_controller

data class CommentWriteDto(
    val postId: Int,
    val content: String,
    val anonymous: Boolean
)