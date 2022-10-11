package me.dungngminh.verygoodblogapp.features.authentication.register

import io.reactivex.rxjava3.core.Observable
import me.dungngminh.verygoodblogapp.features.authentication.register.RegisterContract.Interactor
import me.dungngminh.verygoodblogapp.features.authentication.register.RegisterContract.StateChange
import me.dungngminh.verygoodblogapp.repositories.AuthenticationRepository
import javax.inject.Inject

interface RegisterContract {

    enum class ValidationError {
        EMPTY_FIRSTNAME,
        EMPTY_LASTNAME,
        EMPTY_USERNAME,
        TOO_SHORT_USERNAME,
        EMPTY_PASSWORD,
        TOO_SHORT_PASSWORD,
        NOT_MATCH,
    }

    enum class RegisterError {
        DUPLICATE,
        SERVER_ERROR,
    }

    data class ViewState(
        val firstnameError: Set<ValidationError>,
        val lastnameError: Set<ValidationError>,
        val usernameError: Set<ValidationError>,
        val passwordError: Set<ValidationError>,
        val confirmationPasswordError: Set<ValidationError>,
        val firstname: String?,
        val lastname: String?,
        val username: String?,
        val password: String?,
        val confirmationPassword: String?,
        val isLoading: Boolean,
        val isFirstnameChanged: Boolean,
        val isLastnameChanged: Boolean,
        val isUsernameChanged: Boolean,
        val isPasswordChanged: Boolean,
        val isConfirmationPasswordChanged: Boolean,
    ) {
        companion object {
            @JvmStatic
            fun initial() = ViewState(
                firstnameError = emptySet(),
                lastnameError = emptySet(),
                usernameError = emptySet(),
                passwordError = emptySet(),
                confirmationPasswordError = emptySet(),
                firstname = null,
                lastname = null,
                username = null,
                password = null,
                confirmationPassword = null,
                isLoading = false,
                isFirstnameChanged = false,
                isLastnameChanged = false,
                isUsernameChanged = false,
                isPasswordChanged = false,
                isConfirmationPasswordChanged = false,
            )
        }
    }

    sealed class ViewIntent {
        data class FirstnameChanged(val firstname: String) : ViewIntent()
        data class LastnameChanged(val lastname: String) : ViewIntent()
        data class UsernameChanged(val username: String) : ViewIntent()
        data class PasswordChanged(val password: String) : ViewIntent()
        data class ConfirmationPasswordChanged(val confirmationPassword: String) : ViewIntent()

        object RegisterSubmitted : ViewIntent()

        object FirstnameFirstChanged : ViewIntent()
        object LastnameFirstChanged : ViewIntent()
        object UsernameFirstChanged : ViewIntent()
        object PasswordFirstChanged : ViewIntent()
        object ConfirmationPasswordFirstChanged : ViewIntent()
    }

    sealed class SingleEvent {
        object RegisterSuccess : SingleEvent()
        data class RegisterError(val error: Set<RegisterError>) : SingleEvent()
    }

    sealed class StateChange {

        fun emit(viewState: ViewState): ViewState {
            return when (this) {
                FirstnameFirstChanged -> viewState.copy(isFirstnameChanged = true)
                is ConfirmationPasswordChanged -> viewState.copy(confirmationPassword = confirmationPassword)
                ConfirmationPasswordFirstChanged -> viewState.copy(isConfirmationPasswordChanged = true)
                is FirstnameChanged -> viewState.copy(firstname = firstname)
                is LastnameChanged -> viewState.copy(lastname = lastname)
                LastnameFirstChanged -> viewState.copy(isFirstnameChanged = true)
                Loading -> viewState.copy(isLoading = true)
                is PasswordChanged -> viewState.copy(password = password)
                PasswordFirstChanged -> viewState.copy(isFirstnameChanged = true)
                is RegisterFailed, RegisterSuccess -> viewState.copy(isLoading = false)
                is UsernameChanged -> viewState.copy(username = username)
                UsernameFirstChanged -> viewState.copy(isUsernameChanged = true)
                is ConfirmationPasswordError -> viewState.copy(confirmationPasswordError = error)
                is FirstnameError -> viewState.copy(firstnameError = error)
                is LastnameError -> viewState.copy(lastnameError = error)
                is PasswordError -> viewState.copy(passwordError = error)
                is UsernameError -> viewState.copy(usernameError = error)
            }
        }

        data class UsernameError(val error: Set<ValidationError>) : StateChange()
        data class PasswordError(val error: Set<ValidationError>) : StateChange()
        data class ConfirmationPasswordError(val error: Set<ValidationError>) : StateChange()
        data class FirstnameError(val error: Set<ValidationError>) : StateChange()
        data class LastnameError(val error: Set<ValidationError>) : StateChange()

        data class UsernameChanged(val username: String) : StateChange()
        data class PasswordChanged(val password: String) : StateChange()
        data class ConfirmationPasswordChanged(val confirmationPassword: String) : StateChange()
        data class FirstnameChanged(val firstname: String) : StateChange()
        data class LastnameChanged(val lastname: String) : StateChange()

        object Loading : StateChange()
        object RegisterSuccess : StateChange()
        data class RegisterFailed(val throwable: Throwable) : StateChange()

        object FirstnameFirstChanged : StateChange()
        object LastnameFirstChanged : StateChange()
        object UsernameFirstChanged : StateChange()
        object PasswordFirstChanged : StateChange()
        object ConfirmationPasswordFirstChanged : StateChange()


    }

    interface Interactor {
        fun register(
            firstname: String,
            lastname: String,
            username: String,
            password: String,
            confirmationPassword: String,
        ): Observable<StateChange>
    }
}

class RegisterInteractor @Inject constructor(private val authenticationRepository: AuthenticationRepository) :
    Interactor {

    override fun register(
        firstname: String,
        lastname: String,
        username: String,
        password: String,
        confirmationPassword: String,
    ): Observable<StateChange> {
        return authenticationRepository
            .register(username,
                password,
                confirmationPassword,
                firstname,
                lastname)
            .toObservable()
            .map<StateChange> { StateChange.RegisterSuccess}
            .startWithItem(StateChange.Loading)
            .onErrorReturn { StateChange.RegisterFailed(it) }
    }

}