package com.example.umc_stepper.domain.model

data class UserDto(
    val community_alarm: String = "",
    val email: String = "",
    val email_agree: String = "",
    val exercise_alarm: String = "",
    var height: String = "",
    val nick_name: String = "",
    val password: String = "",
    val per_agree: String = "",
    var profile_image: String= "",
    val use_agree: String = "",
    var weight: String= ""
)