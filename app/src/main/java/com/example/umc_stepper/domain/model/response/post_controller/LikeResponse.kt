package com.example.umc_stepper.domain.model.response.post_controller
//게시글 좋아요 등록 POST
data class LikeResponse(
    val id: Int = 0,
    val memberId: Int = 0,
    val postId: Int = 0
)