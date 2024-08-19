package com.example.umc_stepper.domain.model.response.comment_controller


data class CommentResponseItem(
    val postId: Int=0,
    val commentId: Int= 0,
    val profileImage: String="",
    val memberName: String="",
    val content: String="",
    val dateTime: String="",
    val replyList: List<ReplyResponse>
)
data class ReplyResponse(
    val postId: Int=0,
    val parentCommentId: Int=0,
    val content: String="",
    val anonymous: Boolean = false,
    val profileImage : String = "",
    val localDateTime: String="",
    val memberName: String=""
)
