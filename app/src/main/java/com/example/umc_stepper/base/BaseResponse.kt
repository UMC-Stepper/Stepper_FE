package com.example.umc_stepper.base

import okhttp3.ResponseBody

data class BaseResponse<T> (
    val isSuccess: Boolean = false,
    val code: String = "",
    val message: String = "",
    val result: T? = null
)
