package com.example.umc_stepper.domain.model.request.fcm

data class ScheduleNotificationRequestDto (
    val memberId: Int = 0,
    val notificationTime: String? = ""
)