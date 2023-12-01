package me.dungngminh.verygoodblogapp.features.authentication.register

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch
import me.dungngminh.verygoodblogapp.core.BaseViewModel
import me.dungngminh.verygoodblogapp.features.authentication.login.LoginState
import me.dungngminh.verygoodblogapp.features.authentication.login.LoginViewModel
import me.dungngminh.verygoodblogapp.features.authentication.register.RegisterState.*
import me.dungngminh.verygoodblogapp.features.helpers.LoadingStatus
import me.dungngminh.verygoodblogapp.repositories.AuthenticationRepository
import me.dungngminh.verygoodblogapp.utils.AppConstants
import me.dungngminh.verygoodblogapp.utils.isEmail
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel
@Inject
constructor(
    private val authenticationRepository: AuthenticationRepository,
) : BaseViewModel() {

    private val _state = MutableStateFlow(RegisterState.initial)

    val state = _state.asStateFlow()

    fun changeFullName(fullName: String) {
        if (!state.value.isFullNameFirstChanged) {
            _state.update {
                it.copy(
                    fullName = fullName,
                    isFullNameFirstChanged = true,
                    fullNameValidationError = getFullNameError(fullName),
                )
            }
            return
        }
        _state.update {
            it.copy(
                fullName = fullName,
                fullNameValidationError = getFullNameError(fullName),
            )
        }
    }

    fun changeEmail(email: String) {
        if (!state.value.isEmailFirstChanged) {
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
        if (!state.value.isPasswordFirstChanged) {
            _state.update {
                it.copy(
                    password = password,
                    isPasswordFirstChanged = true,
                    passwordValidationError = getPasswordError(password),
                    confirmationPasswordValidationError = getConfirmationPasswordError(
                        confirmationPassword = state.value.confirmationPassword,
                        password = password
                    ),
                )
            }
        }
        _state.update {
            it.copy(
                password = password,
                passwordValidationError = getPasswordError(password),
                confirmationPasswordValidationError = getConfirmationPasswordError(
                    confirmationPassword = state.value.confirmationPassword,
                    password = password
                ),
            )
        }
    }

    fun changeConfirmationPassword(confirmationPassword: String) {
        if (!state.value.isConfirmationPasswordFirstChanged) {
            _state.update {
                it.copy(
                    confirmationPassword = confirmationPassword,
                    isConfirmationPasswordFirstChanged = true,
                    confirmationPasswordValidationError = getConfirmationPasswordError(
                        confirmationPassword = confirmationPassword,
                        password = state.value.password
                    ),
                )
            }
        }
        _state.update {
            it.copy(
                confirmationPassword = confirmationPassword,
                confirmationPasswordValidationError = getConfirmationPasswordError(
                    confirmationPassword = confirmationPassword,
                    password = state.value.password
                ),
            )
        }
    }

    fun requestRegister() {
        val state = _state.updateAndGet { it.copy(loadingStatus = LoadingStatus.LOADING) }
        viewModelScope.launch {
            try {
                authenticationRepository.register(
                    fullName = state.fullName,
                    email = state.email,
                    password = state.password,
                    confirmationPassword = state.confirmationPassword
                )
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

    private companion object {
        fun getEmailError(email: String): ValidationError? {
            return when {
                email.isBlank() -> ValidationError.EMPTY
                !email.isEmail() -> ValidationError.INVALID
                else -> null
            }
        }

        fun getPasswordError(password: String): ValidationError? {
            return when {
                password.isBlank() -> ValidationError.EMPTY
                password.length < AppConstants.MIN_PASSWORD_LENGTH -> ValidationError.TOO_SHORT
                else -> null
            }
        }


        fun getFullNameError(fullName: String): ValidationError? {
            return when {
                fullName.isBlank() -> ValidationError.EMPTY
                else -> null
            }
        }

        fun getConfirmationPasswordError(
            confirmationPassword: String,
            password: String,
        ): ValidationError? {
            return when {
                confirmationPassword != password -> ValidationError.NOT_MATCH
                else -> null
            }
        }
    }
}
