package com.example.umc_stepper.domain.model.request.rate_diary_controller

data class RateDiaryDto (
    val exerciseCardId : String,
    val conditionRate : String,
    val painRate : String,
    val painMemo : String,
    val painImage : String
)