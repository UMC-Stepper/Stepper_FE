package com.example.umc_stepper.domain.model.response.member_controller

data class UserResponse(
    var id: Int = 0,
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
    var perAgree: Boolean = false,
    var createdAt: String = "",
    var updatedAt: String = ""
)



