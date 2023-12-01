package me.dungngminh.verygoodblogapp.features.splash

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.dungngminh.verygoodblogapp.core.BaseViewModel
import me.dungngminh.verygoodblogapp.repositories.AuthenticationRepository
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val authenticationRepository: AuthenticationRepository) :
    BaseViewModel() {

    private val _isLoggedInState = MutableStateFlow<Boolean>(false)

    val isLoggedInState = _isLoggedInState.asStateFlow()

    init {
        viewModelScope.launch {
            val isLoggedIn = authenticationRepository.checkAuth()
            _isLoggedInState.update { isLoggedIn }
        }
    }
}