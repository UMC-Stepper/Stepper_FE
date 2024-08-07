package com.example.umc_stepper.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.umc_stepper.base.BaseResponse
import com.example.umc_stepper.domain.model.request.LogInDto
import com.example.umc_stepper.domain.model.request.UserDto
import com.example.umc_stepper.domain.model.response.UserResponse
import com.example.umc_stepper.domain.repository.MainApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val mainApiRepository: MainApiRepository
) : ViewModel() {

    private val _userData = MutableStateFlow<BaseResponse<UserResponse>>(BaseResponse())
    var userData: StateFlow<BaseResponse<UserResponse>> = _userData

    private val _loginData = MutableStateFlow<BaseResponse<String>>(BaseResponse())
    var loginData : StateFlow<BaseResponse<String>> = _loginData

    fun postSignUpInfo(userDto: UserDto) {
        viewModelScope.launch {
            try {
                mainApiRepository.postSignUpInfo(userDto).collect {
                    _userData.value = it
                }
            } catch (e: Exception) {
                Log.e("SignUp", "Error")
            }
        }
    }

    fun postUserLogInInfo(logInDto: LogInDto){
        viewModelScope.launch {
            try {
                mainApiRepository.postLogInInfo(logInDto).collect {
                    _loginData.value = it
                }
            }catch (e: Exception){
                Log.e("Login","Error")
            }
        }
    }

}