package com.example.umc_stepper.domain.model.local

data class WeekCalendar(
    val date: String,
    val day: String,
    var firstConnect: String? = "",
    var isSelected: Boolean = false
)
