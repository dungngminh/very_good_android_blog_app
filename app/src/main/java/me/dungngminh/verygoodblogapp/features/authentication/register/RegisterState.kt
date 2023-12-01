package me.dungngminh.verygoodblogapp.features.authentication.register

import me.dungngminh.verygoodblogapp.features.helpers.LoadingStatus

data class RegisterState(
    val fullName: String,
    val isFullNameFirstChanged: Boolean,
    val email: String,
    val isEmailFirstChanged: Boolean,
    val password: String,
    val isPasswordFirstChanged: Boolean,
    val confirmationPassword: String,
    val isConfirmationPasswordFirstChanged: Boolean,
    val loadingStatus: LoadingStatus,
    val error: Throwable?,
    val fullNameValidationError: ValidationError?,
    val emailValidationError: ValidationError?,
    val passwordValidationError: ValidationError?,
    val confirmationPasswordValidationError: ValidationError?,
) {
    companion object {
        @JvmStatic
        val initial =
            RegisterState(
                fullName = "",
                isFullNameFirstChanged = false,
                email = "",
                isEmailFirstChanged = false,
                password = "",
                isPasswordFirstChanged = false,
                confirmationPassword = "",
                isConfirmationPasswordFirstChanged = false,
                loadingStatus = LoadingStatus.INITIAL,
                error = null,
                emailValidationError = null,
                passwordValidationError = null,
                fullNameValidationError = null,
                confirmationPasswordValidationError = null,
            )
    }

    enum class ValidationError {
        INVALID,
        EMPTY,
        TOO_SHORT,
        NOT_MATCH,
    }
}
