package com.example.umc_stepper.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.umc_stepper.base.BaseListResponse
import com.example.umc_stepper.domain.model.response.BadgeResponseItem
import com.example.umc_stepper.domain.repository.MainApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

import android.content.SharedPreferences
import com.example.umc_stepper.domain.model.request.fcm.FCMNotificationRequestDto
import com.example.umc_stepper.domain.model.request.fcm.ScheduleNotificationRequestDto

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainApiRepository: MainApiRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _getBadge = MutableStateFlow<BaseListResponse<BadgeResponseItem>>(BaseListResponse())
    val getBadge: StateFlow<BaseListResponse<BadgeResponseItem>> = _getBadge

    private val _getFcm = MutableStateFlow<String>("")
    val getFcm: StateFlow<String> = _getFcm

    private val _postFcmTime = MutableStateFlow<String>("")
    val postFcmTime: StateFlow<String> = _postFcmTime

    var badgeList = mutableListOf(
        BadgeCheck("첫 운동 설정 완료", false),
        BadgeCheck("첫 오늘의 운동 완료", false),
        BadgeCheck("첫 추가 운동 완료", false),
        BadgeCheck("첫 게시글 작성 완료", false)
    )

    init {
        loadBadgeStates()
    }

    fun getFcm(fCMNotificationRequestDto: FCMNotificationRequestDto) {
        viewModelScope.launch {
            try {
                mainApiRepository.getFcm(fCMNotificationRequestDto).collect {
                    _getFcm.value = it
                }
            } catch (e: Exception) {
                Log.e("MainViewModel getFcm Error", e.message.toString())
            }

        }
    }

    fun postFcmTime(scheduleNotificationRequestDto: ScheduleNotificationRequestDto) {
        viewModelScope.launch {
            try {
                mainApiRepository.postFcmTime(scheduleNotificationRequestDto).collect {
                    _getFcm.value = it
                }
            } catch (e: Exception) {
                Log.e("MainViewModel postFcmTime Error", e.message.toString())
            }

        }
    }

    fun getBadge() {
        viewModelScope.launch {
            try {
                mainApiRepository.getBadge().collect {
                    _getBadge.value = it
                    // Log.e("mainViewModel", "it : $it")
                }
            } catch (e: Exception) {
                Log.e("mainViewModel getBadge", e.message.toString())
            }
        }
    }

    private val TAG = "MainViewModel"

    private fun loadBadgeStates() {
        badgeList.forEachIndexed { index, badgeCheck ->
            badgeCheck.hasBadge = sharedPreferences.getBoolean("badge_$index", false)
        }
    }

    fun updateBadgeState(index: Int, hasBadge: Boolean) {
        badgeList[index].hasBadge = hasBadge
        with(sharedPreferences.edit()) {
            putBoolean("badge_$index", hasBadge)
            apply()
        }
    }

    // 모든 배지 상태를 false로 변경하는 함수
    fun resetAllBadges() {
        badgeList.forEach { badge ->
            badge.hasBadge = false
        }
        // SharedPreferences에 저장된 배지 상태도 업데이트
        badgeList.forEachIndexed { index, _ ->
            with(sharedPreferences.edit()) {
                putBoolean("badge_$index", false)
                apply()
            }
        }
    }

}

data class BadgeCheck(val badgeName: String, var hasBadge: Boolean)
