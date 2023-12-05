package me.dungngminh.verygoodblogapp.features.authentication.login

import me.dungngminh.verygoodblogapp.features.helpers.LoadingStatus

data class LoginState(
    val email: String,
    val isEmailFirstChanged: Boolean,
    val password: String,
    val isPasswordFirstChanged: Boolean,
    val loadingStatus: LoadingStatus,
    val error: Throwable?,
    val emailValidationError: ValidationError?,
    val passwordValidationError: ValidationError?,
) {
    companion object {
        @JvmStatic
        val initial =
            LoginState(
                email = "",
                isEmailFirstChanged = false,
                password = "",
                isPasswordFirstChanged = false,
                loadingStatus = LoadingStatus.INITIAL,
                error = null,
                emailValidationError = ValidationError.EMPTY,
                passwordValidationError = ValidationError.EMPTY,
            )
    }

    enum class ValidationError {
        INVALID,
        EMPTY,
        TOO_SHORT,
    }
}
