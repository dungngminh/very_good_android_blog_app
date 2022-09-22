package me.dungngminh.verygoodblogapp.features.authentication.login

import android.view.View
import androidx.lifecycle.Lifecycle.Event
import androidx.lifecycle.ViewModel
import com.jakewharton.rxrelay3.PublishRelay
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.ofType
import io.reactivex.rxjava3.subjects.BehaviorSubject
import me.dungngminh.verygoodblogapp.core.BaseViewModel
import javax.inject.Inject
import me.dungngminh.verygoodblogapp.features.authentication.login.LoginContract.*
import me.dungngminh.verygoodblogapp.utils.asObservable
import me.dungngminh.verygoodblogapp.utils.exhaustMap
import me.dungngminh.verygoodblogapp.utils.mapNotNull

@HiltViewModel
class LoginViewModel @Inject constructor(private val interactor: LoginContract.Interactor) :
    BaseViewModel() {

    private val stateS = BehaviorSubject.create<ViewState> { initialState }
    private val eventS = PublishRelay.create<SingleEvent>()
    private val stateDistinctS = stateS.distinctUntilChanged()

    private val initialState = ViewState.initial()
    private val intentS = PublishRelay.create<ViewIntent>()

    val state get() = stateDistinctS
    val event get() = eventS.asObservable()

    fun processIntents(intents: Observable<ViewIntent>): Disposable = intents.subscribe(intentS)

    init {
        val usernameObservable = intentS.ofType<ViewIntent.UsernameChanged>()
            .map { it.username }
            .distinctUntilChanged()
            .map { it to getUsernameErrors(it) }
            .share()

        val passwordObservable = intentS.ofType<ViewIntent.PasswordChanged>()
            .map { it.password }
            .distinctUntilChanged()
            .map { it to getPasswordErrors(it) }
            .share()

        val loginChanges = intentS.ofType<ViewIntent.LoginSubmitted>().withLatestFrom(
            usernameObservable,
            passwordObservable) { _, usernamePair, passwordPair -> usernamePair to passwordPair }
            .mapNotNull { it ->

                val (username, usernameErrors) = it.first
                val (password, passwordErrors) = it.second

                if (usernameErrors.isEmpty() && passwordErrors.isEmpty()) {
                    username to password
                } else {
                    null
                }

            }.exhaustMap { (username, password) ->
                interactor.login(username, password).doOnNext {
                    eventS.accept(when (it) {
                        StateChange.Loading -> return@doOnNext
                        StateChange.LoginFailed -> SingleEvent.LoginFailed
                        StateChange.LoginSuccess -> SingleEvent.LoginSuccess
                        is StateChange.PasswordChanged -> return@doOnNext
                        StateChange.PasswordChangedFirstTime -> return@doOnNext
                        is StateChange.PasswordError -> return@doOnNext
                        is StateChange.UsernameChanged -> return@doOnNext
                        StateChange.UsernameChangedFirstTime -> return@doOnNext
                        is StateChange.UsernameError -> return@doOnNext
                    })
                }
            }

        Observable.mergeArray(
            usernameObservable.map { StateChange.UsernameChanged(it.first) },
            passwordObservable.map { StateChange.PasswordChanged(it.first) },
            usernameObservable.map { StateChange.UsernameError(it.second) },
            passwordObservable.map {StateChange.PasswordError(it.second)},
            intentS.ofType<StateChange.UsernameChangedFirstTime>().map{StateChange.UsernameChangedFirstTime},
                        )


    }

    private companion object {
        val tooShortPassword = setOf(ValidationError.TOO_SHORT_PASSWORD)
        val tooShortUsername = setOf(ValidationError.TOO_SHORT_USERNAME)
        val emptyUsername = setOf(ValidationError.EMPTY_USERNAME)
        val emptyPassword = setOf(ValidationError.EMPTY_PASSWORD)

        fun getUsernameErrors(username: String): Set<ValidationError> {
            return when {
                username.isBlank() -> emptyUsername
                username.length <= 3 -> tooShortUsername
                else -> emptySet()
            }
        }

        fun getPasswordErrors(password: String): Set<ValidationError> {
            return when {
                password.isBlank() -> emptyPassword
                password.length <= 3 -> tooShortPassword
                else -> emptySet()
            }
        }
    }

}