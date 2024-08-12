package com.example.umc_stepper.ui.community

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.umc_stepper.base.BaseListResponse
import com.example.umc_stepper.base.BaseResponse
import com.example.umc_stepper.domain.model.response.post_controller.CommunityMyCommentsResponseItem
import com.example.umc_stepper.domain.repository.CommunityApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val communityApiRepository: CommunityApiRepository
) : ViewModel() {

    // 내가 작성한 댓글의 게시글 조회
    private val _communityMyCommentsResponseItem = MutableStateFlow<BaseListResponse<CommunityMyCommentsResponseItem>>(BaseListResponse())
    val communityMyCommentsResponseItem : StateFlow<BaseListResponse<CommunityMyCommentsResponseItem>> = _communityMyCommentsResponseItem


    // 내가 작성한 댓글의 게시글 조회
    fun getCommunityMyComments() {
        viewModelScope.launch {
            try{
                communityApiRepository.getCommunityMyComments().collect {
                    _communityMyCommentsResponseItem.value = it
                    Log.d("CommunityViewModel", "CommunityMyCommentsResponseItem : $it")
                }
            } catch (e:Exception) {
                Log.e("getCommunityMyComments is Error", e.message.toString())
            }
        }
    }

}