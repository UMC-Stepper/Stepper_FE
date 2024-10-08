package com.example.umc_stepper.ui.stepper

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.umc_stepper.base.BaseListResponse
import com.example.umc_stepper.base.BaseResponse
import com.example.umc_stepper.domain.model.Time
import com.example.umc_stepper.domain.model.request.exercise_card_controller.ExerciseCardRequestDto
import com.example.umc_stepper.domain.model.request.rate_diary_controller.RateDiaryDto
import com.example.umc_stepper.domain.model.response.rate_diary_controller.RateDiaryResponse
import com.example.umc_stepper.domain.model.response.rate_diary_controller.RateDiaryResult
import com.example.umc_stepper.domain.model.response.more_exercise_controller.TimeResponse
import com.example.umc_stepper.domain.repository.MainApiRepository
import com.example.umc_stepper.domain.model.response.YouTubeVideo
import com.example.umc_stepper.domain.model.response.exercise_card_controller.ExerciseCardResponse
import com.example.umc_stepper.domain.repository.StepperApiRepository
import com.example.umc_stepper.domain.repository.YoutubeApiRepository
import com.example.umc_stepper.utils.enums.LoadState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class StepperViewModel @Inject constructor(
    private val mainApiRepository: MainApiRepository,
    private val youtubeApiRepository: YoutubeApiRepository,
    private val stepperApiRepository: StepperApiRepository
) : ViewModel() {

    private val _successYoutubeLink = MutableStateFlow(YouTubeVideo())
    val provideYoutubeLink: StateFlow<YouTubeVideo> = _successYoutubeLink

    private val _addTimeState = MutableStateFlow<BaseResponse<TimeResponse>>(BaseResponse())
    val addTimeState : StateFlow<BaseResponse<TimeResponse>> = _addTimeState

    private val TAG = "StepperViewModel"

    //평가 일지 작성 요청 후 응답
    private val _diaryItems = MutableStateFlow<BaseResponse<RateDiaryResult>>(BaseResponse())
    val diaryItems : StateFlow<BaseResponse<RateDiaryResult>> = _diaryItems

    private val _diaryList = MutableStateFlow<BaseResponse<List<RateDiaryResponse>>>(BaseResponse())
    val diaryList : StateFlow<BaseResponse<List<RateDiaryResponse>>> = _diaryList

    // 운동 카드 수정
    private val _exerciseCardResponse = MutableStateFlow<BaseListResponse<ExerciseCardResponse>>(BaseListResponse())
    val exerciseCardResponse : StateFlow<BaseListResponse<ExerciseCardResponse>> = _exerciseCardResponse

    // 운동 카드 단계별 상태 수정
    private val _exerciseCardStepResponse = MutableStateFlow<BaseResponse<Any>>(BaseResponse())
    val exerciseCardStepResponse : StateFlow<BaseResponse<Any>> = _exerciseCardStepResponse

    private val _moreExerciseList = MutableStateFlow<BaseListResponse<TimeResponse>>(BaseListResponse())
    val moreExerciseList : StateFlow<BaseListResponse<TimeResponse>> = _moreExerciseList

    fun moreExerciseAdd(date : String){
        viewModelScope.launch {
            try {
                stepperApiRepository.getMoreExercise(date).collect{
                    _moreExerciseList.value = it
                }
            }catch (e : Exception){
                Log.e("에러","추가운동")
            }
        }
    }

    // 운동 카드 수정
    fun putEditExerciseCard(exerciseId: Int, exerciseCardRequestDto: ExerciseCardRequestDto) {
        viewModelScope.launch {
            try {
                stepperApiRepository.putEditExerciseCard(exerciseId, exerciseCardRequestDto).collect {
                        _exerciseCardResponse.value = it
                }
            } catch (e : Exception){
                Log.e(TAG,"putEditExerciseCard Error")
            }
        }
    }

    // 운동 카드 단계별 상태 수정
    fun postEditExerciseStep(stepId: Int) {
        viewModelScope.launch {
            try {
                stepperApiRepository.postEditExerciseStep(stepId).collect {
                    _exerciseCardStepResponse.value = it
                }
            } catch (e : Exception){
                Log.e(TAG,"postEditExerciseStep Error")
            }
        }
    }

    fun postDiaryEdit(image : MultipartBody.Part, request: RequestBody){
        viewModelScope.launch {
            try{
                mainApiRepository.postRateDiaryEdit(image, request).collect{
                    _diaryItems.value = it
                }
            }catch (e : Exception){
                Log.e(TAG,"postDiaryEdit Error")
            }
        }
    }

    fun getDiaryConfirm(){
        viewModelScope.launch {
            _diaryList.value = BaseResponse(LoadState.LOADING)
            try{
                mainApiRepository.getRateDiaryConfirm().collect{
                    if (it.isSuccess && !it.result.isNullOrEmpty()) {
                        _diaryList.value = it.copy(loadState = LoadState.SUCCESS)
                    } else {
                        _diaryList.value = BaseResponse(LoadState.EMPTY)
                    }
                }
            }catch (e : Exception){
                _diaryList.value = BaseResponse(LoadState.ERROR)
                Log.e(TAG, "getDiaryConfirm Error")
            }
        }
    }

    fun getYoutubeVideoInfo(part: String, id: String, key: String) {
        viewModelScope.launch {
            try {
                Log.d("뷰모델", "Fetching YouTube video details for ID: $id with key: $key")
                youtubeApiRepository.getYoutubeDetail(part, id, key).collect { video ->
                    Log.d(TAG, "YouTube video details fetched: $video")
                    _successYoutubeLink.value = video
                }
            } catch (e: Exception) {
                Log.e("뷰모델", "Get YouTubeVideo is Error", e)
            }
        }
    }

    fun saveMoreExerciseTime(time : Time){
        viewModelScope.launch {
            try {
                stepperApiRepository.postMoreExercise(time).collect{
                    _addTimeState.value = it
                }
            }catch(e : Exception){
                Log.e(TAG, e.message.toString())
            }
        }
    }

}