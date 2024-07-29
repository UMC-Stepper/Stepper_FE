package com.example.umc_stepper.domain.model.local

data class WeekCalendar(
    var date: String,
    val day: String,
    var isSelected: Boolean = false
)
