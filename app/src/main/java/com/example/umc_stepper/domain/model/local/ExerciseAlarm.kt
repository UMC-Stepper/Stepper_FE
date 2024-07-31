package com.example.umc_stepper.domain.model.local

data class ExerciseAlarm (
    val day: String,
    val time: String,
    val amPm: String,
    val materials: String? = null,
    var isEnabled: Boolean = false
)