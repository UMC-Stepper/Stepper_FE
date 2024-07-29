package com.example.umc_stepper.domain.model.response

import java.time.LocalDateTime

data class User(
    val memberId: Long = 0L,
    val name: String = "",
    val nickName: String = "",
    val email: String = "",
    val profileImage: String = "",
    val height: Long = 0L,
    val weight: Long = 0L,
    val communityAlarm: Boolean = false,
    val exerciseAlarm: Boolean = false,
    val emailAgree: Boolean = false,
    val useAgree: Boolean = false,
    val perAgree: Boolean = false,
    val createdAt: String = "",
    val token: String = ""// 임시
)



