package com.example.umc_stepper.domain.model.response

data class UserResponse(
    var id: Int = 0,
    var name: String = "",
    var nickName: String = "",
    var email: String = "",
    var password: String = "",
    var profileImage: String = "",
    var height: Int = 0,
    var weight: Int = 0,
    var communityAlarm: Boolean = true,
    var exerciseAlarm: Boolean = true,
    var emailAgree: Boolean = true,
    var useAgree: Boolean = true,
    var perAgree: Boolean = true,
    var createdAt: String = "",
    var updatedAt: String = ""
)



