package me.dungngminh.verygoodblogapp.features.main

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.dungngminh.verygoodblogapp.core.BaseViewModel

class MainViewModel : BaseViewModel() {

    private val _state = MutableStateFlow(MainState.initial)

    val state = _state.asStateFlow()


    init {
        viewModelScope.launch {

        }
    }
}