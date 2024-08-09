package com.example.umc_stepper.domain.model.response

class BadgeResponse : ArrayList<BadgeResponseItem>()

data class BadgeResponseItem(
    val categoryName: String = "",
    val id: Int = 0,
    val list: List<Badge> = listOf()
)

data class Badge(
    val badgeName: String? = "",
    val explanation: String? = "",
    val id: Int? = 0
)