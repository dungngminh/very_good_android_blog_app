package me.dungngminh.verygoodblogapp.features.main

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.dungngminh.verygoodblogapp.core.BaseViewModel
import me.dungngminh.verygoodblogapp.features.main.MainState.AuthStatus
import me.dungngminh.verygoodblogapp.repositories.AuthenticationRepository
import me.dungngminh.verygoodblogapp.repositories.UserRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthenticationRepository,
) : BaseViewModel() {

    private val _state = MutableStateFlow(MainState.initial)

    val state = _state.asStateFlow()

    init {
        getLoggedInUserProfile()
    }

    private fun getLoggedInUserProfile() {
        _state.update { it.copy(authStatus = AuthStatus.LOADING) }
        viewModelScope.launch {
            try {
                userRepository
                    .getUserProfile()
                    .also { user ->
                        _state.update {
                            it.copy(
                                user = user,
                                authStatus = AuthStatus.LOGGED_IN
                            )
                        }
                    }
            } catch (e: Exception) {
                _state.update { it.copy(authStatus = AuthStatus.ERROR) }
            }
        }
    }

    fun requestLogout() {
        _state.update { it.copy(authStatus = AuthStatus.SIGNED_OUT) }
        authRepository.logout()
    }
}