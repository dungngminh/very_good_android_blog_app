package me.dungngminh.verygoodblogapp.features.authentication.register

import com.jakewharton.rxrelay3.PublishRelay
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.ofType
import io.reactivex.rxjava3.subjects.BehaviorSubject
import me.dungngminh.verygoodblogapp.core.BaseViewModel
import me.dungngminh.verygoodblogapp.features.authentication.register.RegisterContract.*
import me.dungngminh.verygoodblogapp.utils.isEmail
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val interactor: Interactor,
) : BaseViewModel() {

    private val stateS = BehaviorSubject.create<ViewState>()
    private val eventS = PublishRelay.create<SingleEvent>()

    private val initialState = ViewState.initial()
    private val intentS = PublishRelay.create<ViewIntent>()

    val stateObservable: Observable<ViewState> = stateS.distinctUntilChanged()

    // For trigger navigation or show snack-bar ...
    val eventObservable: Observable<SingleEvent> = eventS.hide()

    // for bind view and restore when view created and maybe created again
    val state get() = stateS.value!!

    fun processIntents(intents: Observable<ViewIntent>): Disposable = intents.subscribe(intentS)

    init {
        val fullNameObservable =
            intentS
                .ofType<ViewIntent.FullnameChanged>()
                .map { it.fullname }
                .distinctUntilChanged()
                .map { it to getFullNameErrors(it) }
                .share()

        val emailObservable =
            intentS.ofType<ViewIntent.EmailChanged>()
                .map { it.email }
                .distinctUntilChanged()
                .map { it to getEmailErrors(it) }
                .share()

        val passwordObservable =
            intentS.ofType<ViewIntent.PasswordChanged>()
                .map { it.password }
                .distinctUntilChanged()
                .share()

        val confirmationPasswordObservable =
            intentS.ofType<ViewIntent.ConfirmationPasswordChanged>()
                .map { it.confirmationPassword }
                .distinctUntilChanged()
                .share()

        val bothPasswordObservable =
            Observable.combineLatest(
                passwordObservable,
                confirmationPasswordObservable,
            ) { password, confirmationPassword ->
                getPasswordErrors(password) to getConfirmationPasswordErrors(
                    password,
                    confirmationPassword
                )
            }.share()

        val validationObservale =
            Observable.combineLatest(
                fullNameObservable,
                emailObservable,
                passwordObservable,
                confirmationPasswordObservable,
                bothPasswordObservable,
            ) { fullNamePair, emailPair, password, confirmationPassword, passwordPair ->
                val fullName = fullNamePair.first
                val fullnameError = fullNamePair.second

                val email = emailPair.first
                val emailError = emailPair.second

                val passwordError = passwordPair.first
                val confirmationPasswordError = passwordPair.second

                fullName.isNotBlank()
                        && email.isNotBlank()
                        && password.isNotBlank()
                        && confirmationPassword.isNotBlank()
                        && fullnameError.isEmpty()
                        && emailError.isEmpty()
                        && passwordError.isEmpty()
                        && confirmationPasswordError.isEmpty()
            }.share()

        Observable.mergeArray(
            intentS
                .ofType<ViewIntent.FullnameFirstChanged>()
                .map { StateChange.FullnameFirstChanged },
            intentS
                .ofType<ViewIntent.EmailFirstChanged>()
                .map { StateChange.EmailFirstChanged },
            intentS
                .ofType<ViewIntent.PasswordFirstChanged>()
                .map { StateChange.PasswordFirstChanged },
            intentS
                .ofType<ViewIntent.ConfirmationPasswordFirstChanged>()
                .map { StateChange.ConfirmationPasswordFirstChanged },
            fullNameObservable.map { StateChange.FullnameChanged(it.first) },
            fullNameObservable.map { StateChange.FullnameError(it.second) },
            emailObservable.map { StateChange.EmailChanged(it.first) },
            emailObservable.map { StateChange.EmailError(it.second) },
            passwordObservable.map { StateChange.PasswordChanged(it) },
            confirmationPasswordObservable.map { StateChange.ConfirmationPasswordChanged(it) },
            bothPasswordObservable.map { StateChange.PasswordError(it.first) },
            bothPasswordObservable.map { StateChange.ConfirmationPasswordError(it.second) },
            validationObservale.map { StateChange.FormValidationChanged(it) }
        ).observeOn(AndroidSchedulers.mainThread())
            .scan(initialState) { state, change -> change.emit(state) }.subscribe(stateS)
    }

    private companion object {

        val emptyFullName = setOf(ValidationError.EMPTY_FULLNAME)
        val emailInvalid = setOf(ValidationError.EMAIL_INVALID)
        val emptyEmail = setOf(ValidationError.EMPTY_EMAIL)
        val emptyPassword = setOf(ValidationError.EMPTY_PASSWORD)
        val notMatchPassword = setOf(ValidationError.NOT_MATCH)

        fun getEmailErrors(email: String): Set<ValidationError> {
            return when {
                email.isBlank() -> emptyEmail
                !email.isEmail() -> emailInvalid
                else -> emptySet()
            }
        }

        fun getPasswordErrors(password: String): Set<ValidationError> {
            return when {
                password.isBlank() -> emptyPassword
                else -> emptySet()
            }
        }


        fun getFullNameErrors(fullname: String): Set<ValidationError> {
            return when {
                fullname.isBlank() -> emptyFullName
                else -> emptySet()
            }
        }

        fun getConfirmationPasswordErrors(
            confirmationPassword: String,
            password: String,
        ): Set<ValidationError> {
            return when {
                confirmationPassword != password -> notMatchPassword
                else -> emptySet()
            }
        }
    }
}