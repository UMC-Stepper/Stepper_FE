package com.example.umc_stepper.domain.model.request

data class UserDto(
    var name: String = "",
    var nickName: String = "",
    var email: String = "",
    var password: String = "",
    var profileImage: String = "",
    var height: Int = 0,
    var weight: Int = 0,
    var communityAlarm: Boolean = false,
    var exerciseAlarm: Boolean = false,
    var emailAgree: Boolean = false,
    var useAgree: Boolean = false,
    var perAgree: Boolean = false
)