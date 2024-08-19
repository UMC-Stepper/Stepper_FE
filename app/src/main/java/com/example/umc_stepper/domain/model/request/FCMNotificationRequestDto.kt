package com.example.umc_stepper.domain.model.request

data class FCMNotificationRequestDto (
    val targetUserId: Int = 0,
    val title: String = "",
    val body: String = "",
)