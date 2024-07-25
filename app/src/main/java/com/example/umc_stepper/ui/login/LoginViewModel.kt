package com.example.umc_stepper.ui.login

import androidx.lifecycle.ViewModel
import com.example.umc_stepper.domain.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class LoginViewModel @Inject constructor(

) : ViewModel(){

    private val _userData = MutableStateFlow(User())
    var userData : StateFlow<User> = _userData

    init {
        updateUser(User())
    } // 더미 데이터

    init {
        // 초기화 필요 시 여기에 코드 추가
    }

    fun updateUser(newUser: User) {
        _userData.value = newUser
    }  // 유저 데이터 추가
}