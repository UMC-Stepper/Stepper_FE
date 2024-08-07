package com.example.umc_stepper.domain.model.response

data class RateDiaryResponse(
    val bodyPart: String = "",
    val conditionRate: Int = 0,
    val date: String = "",
    val exerciseCardId: Int = 0,
    val painImage: String = "",
    val painMemo: String = "",
    val painRate: Int = 0
)