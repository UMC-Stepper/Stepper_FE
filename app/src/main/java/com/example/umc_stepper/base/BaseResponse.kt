package com.example.umc_stepper.base

import com.example.umc_stepper.utils.enums.LoadState
import okhttp3.ResponseBody

data class BaseResponse<T> (
    val loadState: LoadState = LoadState.LOADING,
    val isSuccess: Boolean = false,
    val code: String = "",
    val message: String = "",
    val result: T? = null
)

data class BaseListResponse<T> (
    val isSuccess: Boolean = false,
    val code: String = "",
    val message: String = "",
    val result: List<T>? = null
)
