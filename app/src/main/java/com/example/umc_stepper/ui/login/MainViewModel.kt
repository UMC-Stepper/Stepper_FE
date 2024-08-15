package com.example.umc_stepper.ui.login

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.umc_stepper.R
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
    //뱃지
    private val _getBadge = MutableStateFlow<BaseListResponse<BadgeResponseItem>>(BaseListResponse())
    val getBadge : StateFlow<BaseListResponse<BadgeResponseItem>> = _getBadge

    var badgeList = mutableListOf(
        BadgeCheck("첫 운동 설정 완료",false),
        BadgeCheck("첫 오늘의 운동 완료", false),
        BadgeCheck("첫 추가 운동 완료",false),
        BadgeCheck("첫 게시글 작성 완료", false)
        )

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
data class BadgeCheck(val badgeName: String, var hasBadge: Boolean)