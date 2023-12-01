package me.dungngminh.verygoodblogapp.features.authentication.login

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch
import me.dungngminh.verygoodblogapp.core.BaseViewModel
import me.dungngminh.verygoodblogapp.repositories.AuthenticationRepository
import me.dungngminh.verygoodblogapp.utils.AppConstants
import me.dungngminh.verygoodblogapp.features.helpers.LoadingStatus
import me.dungngminh.verygoodblogapp.utils.isEmail
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
@Inject
constructor(
    private val authenticationRepository: AuthenticationRepository,
) :
    BaseViewModel() {
    private val _state = MutableStateFlow(LoginState.initial)

    val state = _state.asStateFlow()

    fun changeEmail(email: String) {
        if (!_state.value.isEmailFirstChanged) {
            _state.update {
                it.copy(
                    email = email,
                    isEmailFirstChanged = true,
                    emailValidationError = getEmailError(email),
                )
            }
            return
        }
        _state.update {
            it.copy(
                email = email,
                emailValidationError = getEmailError(email),
            )
        }
    }

    fun changePassword(password: String) {
        if (!_state.value.isPasswordFirstChanged) {
            _state.update {
                it.copy(
                    password = password,
                    isPasswordFirstChanged = true,
                    passwordValidationError = getPasswordError(password),
                )
            }
        }
        _state.update {
            it.copy(
                password = password,
                passwordValidationError = getPasswordError(password),
            )
        }
    }

    fun requestLogin() {
        val state = _state.updateAndGet { it.copy(loadingStatus = LoadingStatus.LOADING) }
        viewModelScope.launch {
            try {
                authenticationRepository.login(email = state.email, password = state.password)
                _state.update { it.copy(loadingStatus = LoadingStatus.DONE) }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        loadingStatus = LoadingStatus.ERROR,
                        error = e.cause,
                    )
                }
            }
        }
    }

    fun errorMessageShown() {
        _state.update { it.copy(loadingStatus = LoadingStatus.INITIAL) }
    }

    companion object {
        fun getEmailError(email: String): LoginState.ValidationError? =
            when {
                email.isEmpty() -> LoginState.ValidationError.EMPTY
                !email.isEmail() -> LoginState.ValidationError.INVALID
                else -> null
            }

        fun getPasswordError(password: String): LoginState.ValidationError? =
            when {
                password.isEmpty() -> LoginState.ValidationError.EMPTY
                password.length < AppConstants.MIN_PASSWORD_LENGTH -> LoginState.ValidationError.TOO_SHORT
                else -> null
            }
    }
}
