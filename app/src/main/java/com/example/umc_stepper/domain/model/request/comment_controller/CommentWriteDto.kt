package com.example.umc_stepper.domain.model.request.comment_controller

data class CommentWriteDto(
    val anonymous: Boolean,
    val content: String,
    val postId: Int
)