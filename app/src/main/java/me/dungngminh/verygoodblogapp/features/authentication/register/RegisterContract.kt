package me.dungngminh.verygoodblogapp.features.authentication.register

import io.reactivex.rxjava3.core.Observable
import me.dungngminh.verygoodblogapp.features.authentication.register.RegisterContract.Interactor
import me.dungngminh.verygoodblogapp.features.authentication.register.RegisterContract.StateChange
import me.dungngminh.verygoodblogapp.repositories.AuthenticationRepository
import javax.inject.Inject

interface RegisterContract {

    enum class ValidationError {
        EMPTY_FULLNAME,
        EMAIL_INVALID,
        EMPTY_EMAIL,
        EMPTY_PASSWORD,
        TOO_SHORT_PASSWORD,
        NOT_MATCH,
    }

    enum class RegisterError {
        DUPLICATE,
        SERVER_ERROR,
    }

    data class ViewState(
        val fullNameError: Set<ValidationError>,
        val emailError: Set<ValidationError>,
        val passwordError: Set<ValidationError>,
        val confirmationPasswordError: Set<ValidationError>,
        val fullName: String?,
        val email: String?,
        val password: String?,
        val confirmationPassword: String?,
        val isLoading: Boolean,
        val isFirstnameChanged: Boolean,
        val isLastnameChanged: Boolean,
        val isEmailFirstChanged: Boolean,
        val isPasswordChanged: Boolean,
        val isConfirmationPasswordChanged: Boolean,
        val isValid: Boolean
    ) {
        companion object {
            @JvmStatic
            fun initial() = ViewState(
                fullNameError = emptySet(),
                emailError = emptySet(),
                passwordError = emptySet(),
                confirmationPasswordError = emptySet(),
                fullName = null,
                email = null,
                password = null,
                confirmationPassword = null,
                isLoading = false,
                isFirstnameChanged = false,
                isLastnameChanged = false,
                isEmailFirstChanged = false,
                isPasswordChanged = false,
                isConfirmationPasswordChanged = false,
                isValid = false,
            )
        }
    }

    sealed class ViewIntent {
        data class FullnameChanged(val fullname: String) : ViewIntent()
        data class EmailChanged(val email: String) : ViewIntent()
        data class PasswordChanged(val password: String) : ViewIntent()
        data class ConfirmationPasswordChanged(val confirmationPassword: String) : ViewIntent()

        object RegisterSubmitted : ViewIntent()

        object FullnameFirstChanged : ViewIntent()
        object EmailFirstChanged : ViewIntent()
        object PasswordFirstChanged : ViewIntent()
        object ConfirmationPasswordFirstChanged : ViewIntent()
    }

    sealed class SingleEvent {
        object RegisterSuccess : SingleEvent()
        data class RegisterError(val throwable: Throwable) : SingleEvent()
    }

    sealed class StateChange {

        fun emit(viewState: ViewState): ViewState {
            return when (this) {
                is ConfirmationPasswordChanged -> viewState.copy(confirmationPassword = confirmationPassword)
                ConfirmationPasswordFirstChanged -> viewState.copy(isConfirmationPasswordChanged = true)
                is FullnameChanged -> viewState.copy(fullName = fullname)
                FullnameFirstChanged -> viewState.copy(isLastnameChanged = true)
                Loading -> viewState.copy(isLoading = true)
                is PasswordChanged -> viewState.copy(password = password)
                PasswordFirstChanged -> viewState.copy(isPasswordChanged = true)
                is RegisterFailed, RegisterSuccess -> viewState.copy(isLoading = false)
                is EmailChanged -> viewState.copy(email = email)
                EmailFirstChanged -> viewState.copy(isEmailFirstChanged = true)
                is ConfirmationPasswordError -> viewState.copy(confirmationPasswordError = error)
                is FullnameError -> viewState.copy(fullNameError = error)
                is PasswordError -> viewState.copy(passwordError = error)
                is EmailError -> viewState.copy(emailError = error)
                is FormValidationChanged -> viewState.copy(isValid = isValid)
            }
        }

        data class EmailError(val error: Set<ValidationError>) : StateChange()
        data class PasswordError(val error: Set<ValidationError>) : StateChange()
        data class ConfirmationPasswordError(val error: Set<ValidationError>) : StateChange()
        data class FullnameError(val error: Set<ValidationError>) : StateChange()
        data class EmailChanged(val email: String) : StateChange()
        data class PasswordChanged(val password: String) : StateChange()
        data class ConfirmationPasswordChanged(val confirmationPassword: String) : StateChange()
        data class FullnameChanged(val fullname: String) : StateChange()
        object Loading : StateChange()
        object RegisterSuccess : StateChange()
        data class RegisterFailed(val throwable: Throwable) : StateChange()
        object FullnameFirstChanged : StateChange()
        object EmailFirstChanged : StateChange()
        object PasswordFirstChanged : StateChange()
        object ConfirmationPasswordFirstChanged : StateChange()
        data class FormValidationChanged(val isValid: Boolean) : StateChange()
    }

    interface Interactor {
        fun register(
            fullName: String,
            email: String,
            password: String,
            confirmationPassword: String,
        ): Observable<StateChange>
    }
}

class RegisterInteractor
@Inject constructor(private val authenticationRepository: AuthenticationRepository) :
    Interactor {
    override fun register(
        fullName: String,
        email: String,
        password: String,
        confirmationPassword: String,
    ): Observable<StateChange>
        = authenticationRepository
            .register(fullName = fullName, email = email, password = password, confirmationPassword = confirmationPassword)
            .toObservable()
            .map<StateChange> { StateChange.RegisterSuccess}
            .startWithItem(StateChange.Loading)
            .onErrorReturn { StateChange.RegisterFailed(it) }
}