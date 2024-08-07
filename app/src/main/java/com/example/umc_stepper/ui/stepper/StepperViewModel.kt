package com.example.umc_stepper.ui.stepper

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.umc_stepper.base.BaseResponse
import com.example.umc_stepper.domain.model.request.RateDiaryDto
import com.example.umc_stepper.domain.model.response.RateDiaryResponse
import com.example.umc_stepper.domain.model.response.RateDiaryResult
import com.example.umc_stepper.domain.model.response.UserResponse
import com.example.umc_stepper.domain.repository.MainApiRepository
import com.example.umc_stepper.domain.model.response.YouTubeVideo
import com.example.umc_stepper.domain.repository.YoutubeApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.logging.Level
import javax.inject.Inject

@HiltViewModel
class StepperViewModel @Inject constructor(
    private val mainApiRepository: MainApiRepository
) : ViewModel() {
    private val _levelItems = MutableLiveData<List<LevelListItem>>()
    val levelItems: LiveData<List<LevelListItem>> = _levelItems

    private val _successYoutubeLink = MutableStateFlow(YouTubeVideo())
    val provideYoutubeLink: StateFlow<YouTubeVideo> = _successYoutubeLink

    private val TAG = "StepperViewModel"
    //평가 일지 작성 요청 후 응답
    private val _diaryItems = MutableStateFlow<BaseResponse<RateDiaryResult>>(BaseResponse())
    val diaryItems : StateFlow<BaseResponse<RateDiaryResult>> = _diaryItems

    private val _diaryList = MutableStateFlow<BaseResponse<List<RateDiaryResponse>>>(BaseResponse())
    val diaryList : StateFlow<BaseResponse<List<RateDiaryResponse>>> = _diaryList

    fun postDiaryEdit(rateDiaryDto: RateDiaryDto){
        viewModelScope.launch {
            try{
                mainApiRepository.postRateDiaryEdit(rateDiaryDto).collect{
                    _diaryItems.value = it
                }
            }catch (e : Exception){
                Log.e(TAG,"postDiaryEdit Error")
            }
        }
    }

    fun getDiaryConfirm(){
        viewModelScope.launch {
            try{
                mainApiRepository.getRateDiaryConfirm().collect{
                    _diaryList.value = it
                }
            }catch (e : Exception){
                Log.e(TAG, "getDiaryConfirm Error")
            }
        }
    }
    init {
        loadLevelItems()
    }

    private fun loadLevelItems() {
        val items = listOf(
            LevelListItem(
                listOf(
                    LevelItem("image1.jpg", "Level 1"),
                    LevelItem("image2.jpg", "Level 2"),
                    LevelItem("image3.jpg", "Level 3")
                )
            ),
            LevelListItem(
                listOf(
                    LevelItem("image4.jpg", "Level 1"),
                    LevelItem("image5.jpg", "Level 2"),
                    LevelItem("image6.jpg", "Level 3")
                )
            ),
            LevelListItem(
                listOf(
                    LevelItem("image4.jpg", "Level 1"),
                    LevelItem("image5.jpg", "Level 2"),
                    LevelItem("image6.jpg", "Level 3")
                )
            )
        )
        _levelItems.value = items
    }

    fun getYoutubeVideoInfo(part: String, id: String, key: String) {
        viewModelScope.launch {
            try {
                youtubeApiRepository.getYoutubeDetail(part, id, key).collect { video ->
                    _successYoutubeLink.value=video

                }
            } catch (e: Exception) {
                Log.e("Get YoutubeVideo is Error", e.message.toString())
            }
        }
    }
}