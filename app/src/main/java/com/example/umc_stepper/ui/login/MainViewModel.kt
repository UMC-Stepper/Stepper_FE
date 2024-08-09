package com.example.umc_stepper.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.umc_stepper.base.BaseListResponse
import com.example.umc_stepper.base.BaseResponse
import com.example.umc_stepper.domain.model.response.BadgeResponseItem
import com.example.umc_stepper.domain.repository.MainApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainApiRepository: MainApiRepository
) : ViewModel(){

    private val _getBadge = MutableStateFlow<BaseListResponse<BadgeResponseItem>>(BaseListResponse())
    val getBadge : StateFlow<BaseListResponse<BadgeResponseItem>> = _getBadge

    fun getBadge(){
        viewModelScope.launch {
            try{
                mainApiRepository.getBadge().collect{
                    _getBadge.value = it
                    //Log.e("mainViewModel ","it : $it")
                }
            }catch(e:Exception){
                Log.e("mainViewModel getBadge",e.message.toString())
            }

        }
    }

    private val TAG = "MainViewModel"

}
