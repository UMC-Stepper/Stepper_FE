package com.example.umc_stepper.ui.stepper

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.logging.Level

class StepperViewModel : ViewModel() {
    private val _levelItems = MutableLiveData<List<LevelListItem>>()
    val levelItems: LiveData<List<LevelListItem>> = _levelItems

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
}