package me.dungngminh.verygoodblogapp.features.authentication.login

import io.reactivex.rxjava3.core.Observable
import me.dungngminh.verygoodblogapp.features.authentication.login.LoginContract.Interactor
import me.dungngminh.verygoodblogapp.features.authentication.login.LoginContract.StateChange
import me.dungngminh.verygoodblogapp.repositories.AuthenticationRepository
import javax.inject.Inject

interface LoginContract {
    enum class ValidationError {
        EMPTY_USERNAME,
        TOO_SHORT_USERNAME,
        EMPTY_PASSWORD,
    }

    data class ViewState(
        val usernameError: Set<ValidationError>,
        val passwordError: Set<ValidationError>,
        val username: String?,
        val password: String?,
        val isLoading: Boolean,
        val isUsernameChanged: Boolean,
        val isPasswordChanged: Boolean,
    ) {
        companion object {
            @JvmStatic
            fun initial() = ViewState(
                usernameError = emptySet(),
                passwordError = emptySet(),
                username = null,
                password = null,
                isLoading = false,
                isUsernameChanged = false,
                isPasswordChanged = false
            )
        }
    }

    sealed class ViewIntent {
        data class UsernameChanged(val username: String) : ViewIntent()
        data class PasswordChanged(val password: String) : ViewIntent()

        object LoginSubmitted : ViewIntent()

        object UsernameFirstChanged : ViewIntent()
        object PasswordFirstChanged : ViewIntent()
    }

    sealed class SingleEvent {
        object LoginSuccess : SingleEvent()
        object LoginFailed : SingleEvent()
    }


    sealed class StateChange {
        fun emit(state: ViewState): ViewState {
            return when (this) {
                UsernameChangedFirstTime -> state.copy(isUsernameChanged = true)
                is UsernameError -> state.copy(usernameError = error)
                Loading -> state.copy(isLoading = true)
                LoginFailed, LoginSuccess -> state.copy(isLoading = false)
                PasswordChangedFirstTime -> state.copy(isPasswordChanged = true)
                is PasswordError -> state.copy(passwordError = error)
                is UsernameChanged -> state.copy(username = username)
                is PasswordChanged -> state.copy(password = password)
            }
        }

        data class UsernameError(val error: Set<ValidationError>) : StateChange()
        data class PasswordError(val error: Set<ValidationError>) : StateChange()

        object Loading : StateChange()
        object LoginSuccess : StateChange()
        object LoginFailed : StateChange()

        object UsernameChangedFirstTime : StateChange()
        object PasswordChangedFirstTime : StateChange()

        data class UsernameChanged(val username: String) : StateChange()
        data class PasswordChanged(val password: String) : StateChange()
    }

    interface Interactor {
        fun login(username: String, password: String): Observable<StateChange>

    }
}

class LoginInteractor @Inject constructor(private val authenticationRepository: AuthenticationRepository) :
    Interactor {
    override fun login(username: String, password: String): Observable<StateChange> {
        return authenticationRepository.login(
            username = username,
            password = password
        )
            .toObservable()
            .map<StateChange> { StateChange.LoginSuccess }
            .startWithItem(StateChange.Loading)
            .onErrorReturnItem(StateChange.LoginFailed)
    }

}

