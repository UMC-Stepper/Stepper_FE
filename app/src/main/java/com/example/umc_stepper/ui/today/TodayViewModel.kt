package com.example.umc_stepper.ui.today

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.umc_stepper.BuildConfig
import com.example.umc_stepper.base.BaseListResponse
import com.example.umc_stepper.domain.mapper.toExerciseStates
import com.example.umc_stepper.domain.model.local.ExerciseState
import com.example.umc_stepper.domain.model.request.AiVideoDto
import com.example.umc_stepper.domain.model.response.AiVideoInfo
import com.example.umc_stepper.domain.model.response.exercise_card_controller.ExerciseCardStatusResponseDto
import com.example.umc_stepper.domain.model.response.exercise_card_controller.ExerciseCardWeekResponseDto
import com.example.umc_stepper.domain.model.response.Ylist
import com.example.umc_stepper.domain.model.response.YouTubeVideo
import com.example.umc_stepper.domain.model.response.my_exercise_controller.CheckExerciseResponse
import com.example.umc_stepper.domain.repository.FastApiRepository
import com.example.umc_stepper.domain.repository.TodayApiRepository
import com.example.umc_stepper.domain.repository.YoutubeApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodayViewModel @Inject constructor(
    private val todayApiRepository: TodayApiRepository,
    private val youtubeApiRepository: YoutubeApiRepository,
    private val fastApiRepository: FastApiRepository
) : ViewModel() {
    private val _provideAiVideo = MutableStateFlow(AiVideoInfo())
    val provideAiVideo: StateFlow<AiVideoInfo> = _provideAiVideo

    private val _provideYoutubeLink = MutableStateFlow(YouTubeVideo())
    val provideYoutubeLink: StateFlow<YouTubeVideo> = _provideYoutubeLink

    private val _provideYoutubeLink2 = MutableStateFlow(YouTubeVideo())
    val provideYoutubeLink2: StateFlow<YouTubeVideo> = _provideYoutubeLink2

    private val _successYoutubeLink = MutableStateFlow(Ylist())
    val successYoutubeLink: StateFlow<Ylist> = _successYoutubeLink

    // 오늘의 운동 진행 상태 조회
    private val _exerciseState = MutableStateFlow<List<ExerciseState>?>(null)
    val exerciseState: StateFlow<List<ExerciseState>?> = _exerciseState

    // 운동 부위의 운동 카드 요일 조회
    private val _exerciseCardWeekResponseDto = MutableStateFlow<BaseListResponse<ExerciseCardWeekResponseDto>>(BaseListResponse())
    val exerciseCardWeekResponseDto : StateFlow<BaseListResponse<ExerciseCardWeekResponseDto>> = _exerciseCardWeekResponseDto

    // 월별 운동 카드 상태 조회
    private val _exerciseCardStatusResponseDto = MutableStateFlow<BaseListResponse<ExerciseCardStatusResponseDto>>(BaseListResponse())
    val exerciseCardStatusResponseDto : StateFlow<BaseListResponse<ExerciseCardStatusResponseDto>> = _exerciseCardStatusResponseDto

    // 나만의 운동 조회
    private val _checkExerciseResponseDTO = MutableStateFlow<BaseListResponse<CheckExerciseResponse>>(BaseListResponse())
    val checkExerciseResponseDTO: StateFlow<BaseListResponse<CheckExerciseResponse>> = _checkExerciseResponseDTO

    // 오늘의 운동 진행 상태 조회
    fun getTodayExerciseState(date: String) {
        viewModelScope.launch {
            try {
                todayApiRepository.getTodayExerciseState(date).collect { response ->
                    if (response != null) {
                        val exerciseStateList = response.toExerciseStates()
                        _exerciseState.value = exerciseStateList
                    }
                }
            } catch (e: Exception) {
                Log.e("TodayViewModel getTodayExerciseState Error", e.message.toString())
            }
        }
    }

    // 운동 부위의 운동 카드 요일 조회
    fun getExerciseCheckDate(bodyPart: String) {
        viewModelScope.launch {
            try {
                todayApiRepository.getExerciseCheckDate(bodyPart).collect {
                    _exerciseCardWeekResponseDto.value = it
                }
            } catch (e: Exception) {
                Log.e("getExerciseCheckDate is Error", e.message.toString())
            }

        }
    }

    // 월별 운동 카드 상태 조회
    fun getExerciseMonthCheck(month: Int) {
        viewModelScope.launch {
            try {
                todayApiRepository.getExerciseMonthCheck(month).collect {
                    _exerciseCardStatusResponseDto.value = it
                }
            } catch (e:Exception) {
                Log.e("getExerciseMonthCheck is Error", e.message.toString())
            }
        }
    }

    // 나만의 운동 조회
    fun getMyExercise(bodyPart: String) {
        viewModelScope.launch {
            try {
                todayApiRepository.getMyExercise(bodyPart).collect {
                    _checkExerciseResponseDTO.value = it
                    Log.d("TodayViewModel", "_checkExerciseResponseDTO : $it")
                }
            } catch (e:Exception) {
                Log.e("getMyExercise is Error", e.message.toString())
            }
        }
    }


    val youtubeKey = BuildConfig.YOUTUBE_KEY

    fun postAiVideoInfo(aiVideoDto: AiVideoDto) {
        viewModelScope.launch {
            try {
                fastApiRepository.postAiVideoInfo(aiVideoDto).collect {
                    _provideAiVideo.value = it
                }
            } catch (e: Exception) {
                Log.e("Post AiVideo is Error", e.message.toString())
            }
        }
    }

    fun getYoutubeVideoInfo(part: String, id: String, key: String) {
        viewModelScope.launch {
            try {
                youtubeApiRepository.getYoutubeDetail(part, id, key).collect { video ->
                    val updatedList = _successYoutubeLink.value.ylist.toMutableList()
                    updatedList.add(video)
                    _successYoutubeLink.value = _successYoutubeLink.value.copy(ylist = updatedList)
                    Log.d("TodayViewModel", "YouTube video received: ${video.items[0].snippet.title}")
                    Log.d("TodayViewModel", "Ylist size: ${_successYoutubeLink.value.ylist.size}")
                }
            } catch (e: Exception) {
                Log.e("Get YoutubeVideo is Error", e.message.toString())
            }
        }
    }


    fun clearList(){
        val updatedList = _successYoutubeLink.value.ylist.toMutableList()
        updatedList.clear()
        _successYoutubeLink.value = _successYoutubeLink.value.copy(ylist = updatedList)
    }

    fun getYoutubeVideoInfoSequentially(firstUrl: String, secondUrl: String) {
        viewModelScope.launch {
            getYoutubeVideoInfo("snippet", firstUrl, youtubeKey)
            getYoutubeVideoInfo("snippet", secondUrl, youtubeKey)
        }
    }
}
