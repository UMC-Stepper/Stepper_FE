package com.example.umc_stepper.domain.model.response

data class BadgeResponseItem(
    val categoryName: String = "",
    val id: Int = 0,
    val list: List<Badge> = listOf()
)