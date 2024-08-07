package com.example.umc_stepper.ui.stepper

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val youtubeApiRepository: YoutubeApiRepository
): ViewModel() {
    private val _levelItems = MutableLiveData<List<LevelListItem>>()
    val levelItems: LiveData<List<LevelListItem>> = _levelItems

    private val _successYoutubeLink = MutableStateFlow(YouTubeVideo())
    val provideYoutubeLink: StateFlow<YouTubeVideo> = _successYoutubeLink

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