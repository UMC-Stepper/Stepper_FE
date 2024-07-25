package com.example.umc_stepper.base

import okhttp3.ResponseBody

data class BaseResponse<T>(
    val status : String,
    val message : String,
    val data : T
)
