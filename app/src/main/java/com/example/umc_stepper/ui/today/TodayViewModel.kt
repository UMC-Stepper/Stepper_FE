package com.example.umc_stepper.ui.today

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.umc_stepper.BuildConfig
import com.example.umc_stepper.base.BaseListResponse
import com.example.umc_stepper.base.BaseResponse
import com.example.umc_stepper.domain.mapper.toExerciseStates
import com.example.umc_stepper.domain.model.local.ExerciseState
import com.example.umc_stepper.domain.model.local.ExerciseStep
import com.example.umc_stepper.domain.model.request.AiVideoDto
import com.example.umc_stepper.domain.model.request.exercise_card_controller.ExerciseCardRequestDto
import com.example.umc_stepper.domain.model.request.exercise_card_controller.ExerciseStepRequestDto
import com.example.umc_stepper.domain.model.request.my_exercise_controller.AddExerciseRequestDto
import com.example.umc_stepper.domain.model.response.AiVideoInfo
import com.example.umc_stepper.domain.model.response.exercise_card_controller.ExerciseCardStatusResponseDto
import com.example.umc_stepper.domain.model.response.exercise_card_controller.ExerciseCardWeekResponseDto
import com.example.umc_stepper.domain.model.response.Ylist
import com.example.umc_stepper.domain.model.response.YouTubeVideo
import com.example.umc_stepper.domain.model.response.exercise_card_controller.ExerciseCardResponse
import com.example.umc_stepper.domain.model.response.exercise_card_controller.ExerciseStepResponse
import com.example.umc_stepper.domain.model.response.exercise_card_controller.ToDayExerciseResponseDto
import com.example.umc_stepper.domain.model.response.my_exercise_controller.AddExerciseResponse
import com.example.umc_stepper.domain.model.response.my_exercise_controller.CheckExerciseResponse
import com.example.umc_stepper.domain.repository.FastApiRepository
import com.example.umc_stepper.domain.repository.TodayApiRepository
import com.example.umc_stepper.domain.repository.YoutubeApiRepository
import com.example.umc_stepper.utils.enums.LoadState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodayViewModel @Inject constructor(
    private val todayApiRepository: TodayApiRepository,
    private val youtubeApiRepository: YoutubeApiRepository,
    private val fastApiRepository: FastApiRepository
) : ViewModel() {

    private val _setExerciseStep = MutableStateFlow<List<CheckExerciseResponse>>(
        listOf()
    )
    val setExerciseStep: StateFlow<List<CheckExerciseResponse>> = _setExerciseStep

    private val _provideAiVideo = MutableStateFlow(AiVideoInfo())
    val provideAiVideo: StateFlow<AiVideoInfo> = _provideAiVideo

    private val _provideYoutubeLink = MutableStateFlow(YouTubeVideo())
    val provideYoutubeLink: StateFlow<YouTubeVideo> = _provideYoutubeLink

    private val _provideYoutubeLink2 = MutableStateFlow(YouTubeVideo())
    val provideYoutubeLink2: StateFlow<YouTubeVideo> = _provideYoutubeLink2

    private val _successYoutubeLink = MutableStateFlow(Ylist())
    val successYoutubeLink: StateFlow<Ylist> = _successYoutubeLink

    // 운동 카드 추가
    private val _addExerciseCardResponse =
        MutableStateFlow<BaseResponse<ExerciseCardResponse>>(BaseResponse())
    val addExerciseCardResponse: StateFlow<BaseResponse<ExerciseCardResponse>> =
        _addExerciseCardResponse

    // 운동 카드 상세 조회
    private val _inquiryExerciseCardResponse =
        MutableStateFlow<BaseResponse<ExerciseCardResponse>>(BaseResponse())
    val inquiryExerciseCardResponse: StateFlow<BaseResponse<ExerciseCardResponse>> =
        _inquiryExerciseCardResponse

    // 오늘의 운동 진행 상태 조회
    private val _exerciseState = MutableStateFlow<List<ExerciseState>?>(null)
    val exerciseState: StateFlow<List<ExerciseState>?> = _exerciseState

    // 운동 부위의 운동 카드 요일 조회
    private val _exerciseCardWeekResponseDto =
        MutableStateFlow<BaseListResponse<ExerciseCardWeekResponseDto>>(BaseListResponse())
    val exerciseCardWeekResponseDto: StateFlow<BaseListResponse<ExerciseCardWeekResponseDto>> =
        _exerciseCardWeekResponseDto

    // 월별 운동 카드 상태 조회
    private val _exerciseCardStatusResponseDto =
        MutableStateFlow<BaseListResponse<ExerciseCardStatusResponseDto>>(BaseListResponse())
    val exerciseCardStatusResponseDto: StateFlow<BaseListResponse<ExerciseCardStatusResponseDto>> =
        _exerciseCardStatusResponseDto

    // 나만의 운동 추가
    private val _addExerciseResponse =
        MutableStateFlow<BaseResponse<AddExerciseResponse>>(BaseResponse())
    val addExerciseResponse: StateFlow<BaseResponse<AddExerciseResponse>> = _addExerciseResponse

    // 나만의 운동 조회
    private val _checkExerciseResponseDTO =
        MutableStateFlow<BaseListResponse<CheckExerciseResponse>>(BaseListResponse())
    val checkExerciseResponseDTO: StateFlow<BaseListResponse<CheckExerciseResponse>> =
        _checkExerciseResponseDTO

    private val _todayExerciseResponseDto =
        MutableStateFlow<BaseListResponse<ToDayExerciseResponseDto>>(BaseListResponse())
    val todayExerciseResponseDto: StateFlow<BaseListResponse<ToDayExerciseResponseDto>> =
        _todayExerciseResponseDto

    private val _bodyPart = MutableStateFlow("")
    val bodyPart: StateFlow<String> = _bodyPart

    private val _exerciseIdList = MutableStateFlow(listOf<Int>())
    val exerciseIdList: StateFlow<List<Int>> = _exerciseIdList

    private val _dataLoadState = MutableStateFlow(LoadState.SUCCESS)
    val dataLoadState: StateFlow<LoadState> get() = _dataLoadState

    fun loadEvaluationLogData() {
        viewModelScope.launch {
            val loadState = LoadState.LOADING
            _dataLoadState.value = loadState
        }
    }

    fun successEvaluationLogData() {
        viewModelScope.launch {
            val loadState = LoadState.SUCCESS
            _dataLoadState.value = loadState
        }
    }

    fun addExerciseList(eid: Int) {
        val updatedList = _exerciseIdList.value.toMutableList()
        updatedList.add(eid)
        _exerciseIdList.value = updatedList
    }

    fun updateExerciseList(eid: Int, pos: Int) {
        val editList = _exerciseIdList.value.toMutableList()
        editList[pos] = eid
        _exerciseIdList.value = editList
    }

    fun clearExerciseList() {
        val emptyList = emptyList<Int>()
        _exerciseIdList.value = emptyList
    }

    fun getExerciseList(): List<Int> {
        val list: ArrayList<Int> = arrayListOf()
        for (i in 0..<_exerciseIdList.value.size) {
            list.add(_exerciseIdList.value[i])
        }
        return list.toList()
    }

    fun getExerciseListSize(): Int = _exerciseIdList.value.size

    fun setBodyPart(s: String) {
        _bodyPart.value = s
    }

    fun addStep(stepCard: CheckExerciseResponse) {
        val currentList = _setExerciseStep.value.toMutableList()
        currentList.add(stepCard)
        _setExerciseStep.value = currentList
        Log.d("ViewModel", "Step added. New size: ${currentList.size}")
    }

    fun updateStep(stepCard: CheckExerciseResponse, pos: Int) {
        val currentList = _setExerciseStep.value.toMutableList()
        currentList[pos - 1] = stepCard
        _setExerciseStep.value = currentList
        Log.d("ViewModel", "Step added. New size: ${currentList.size}")
    }

    fun clearStep() {
        _setExerciseStep.value = emptyList()  // 빈 리스트를 명확히 할당하여 상태 변경을 강제합니다.
    }


    // 운동 카드 추가
    fun postAddExerciseCard(exerciseCardRequestDto: ExerciseCardRequestDto) {
        viewModelScope.launch {
            try {
                todayApiRepository.postAddExerciseCard(exerciseCardRequestDto).collect {
                    _addExerciseCardResponse.value = it
                }
            } catch (e: Exception) {
                Log.e("TodayViewModel AddExerciseCard Error", e.message.toString())
            }
        }
    }

    // 운동 카드 상세 조회
    fun postInquiryExerciseCard(exerciseId: Int) {
        viewModelScope.launch {
            try {
                todayApiRepository.postInquiryExerciseCard(exerciseId).collect {
                    _inquiryExerciseCardResponse.value = it
                }
            } catch (e: Exception) {
                Log.e("TodayViewModel InquiryExerciseCard Error", e.message.toString())
            }
        }
    }

    fun getStepperExerciseState(date: String) {
        viewModelScope.launch {
            try {
                todayApiRepository.getTodayExerciseState(date).collect {
                    _todayExerciseResponseDto.value = it
                }
            } catch (e: Exception) {
                Log.e("TodayViewModel", e.message.toString())
            }
        }
    }

    // 오늘의 운동 진행 상태 조회
    fun getTodayExerciseState(date: String) {
        viewModelScope.launch {
            try {
                todayApiRepository.getTodayExerciseState(date).collect { response ->
                    val exerciseStateList = response.toExerciseStates()
                    _exerciseState.value = exerciseStateList
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
            } catch (e: Exception) {
                Log.e("getExerciseMonthCheck is Error", e.message.toString())
            }
        }
    }

    // 나만의 운동 추가 addExerciseResponse
    fun postAddMyExercise(addExerciseRequestDto: AddExerciseRequestDto) {
        viewModelScope.launch {
            try {
                todayApiRepository.postAddMyExercise(addExerciseRequestDto).collect {
                    _addExerciseResponse.value = it
                }
            } catch (e: Exception) {
                Log.e("postAddMyExercise is Error", e.message.toString())
            }
        }
    }

    // 나만의 운동 조회
    fun getMyExercise(bodyPart: String) {
        viewModelScope.launch {
            _checkExerciseResponseDTO.value = BaseListResponse(LoadState.LOADING)
            try {
                todayApiRepository.getMyExercise(bodyPart).collect {
                    if (it.isSuccess && !it.result.isNullOrEmpty()) {
                        _checkExerciseResponseDTO.value = it.copy(loadState = LoadState.SUCCESS)
                        Log.d("TodayViewModel", "_checkExerciseResponseDTO : $it")
                    } else {
                        _checkExerciseResponseDTO.value = BaseListResponse(LoadState.EMPTY)
                    }

                }
            } catch (e: Exception) {
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
                    Log.d(
                        "TodayViewModel",
                        "YouTube video received: ${video.items[0].snippet.title}"
                    )
                    Log.d("TodayViewModel", "Ylist size: ${_successYoutubeLink.value.ylist.size}")
                }
            } catch (e: Exception) {
                Log.e("Get YoutubeVideo is Error", e.message.toString())
            }
        }
    }


    fun clearList() {
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
