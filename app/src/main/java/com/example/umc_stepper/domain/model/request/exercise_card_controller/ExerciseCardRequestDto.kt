package com.example.umc_stepper.domain.model.request.exercise_card_controller

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExerciseCardRequestDto(
    var bodyPart: String,
    var date: String,
    val hour: Int,
    val materials: String,
    val minute: Int,
    val second: Int,
    val stepList: List<ExerciseStepRequestDto>,
    var week: String
) : Parcelable

@Parcelize
data class ExerciseStepRequestDto(
    val myExerciseId: Int,
    val step: Int
) : Parcelable