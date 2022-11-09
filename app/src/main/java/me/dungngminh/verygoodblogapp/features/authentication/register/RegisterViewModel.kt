package me.dungngminh.verygoodblogapp.features.authentication.register

import com.jakewharton.rxrelay3.PublishRelay
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.Observables
import io.reactivex.rxjava3.kotlin.ofType
import io.reactivex.rxjava3.subjects.BehaviorSubject
import me.dungngminh.verygoodblogapp.core.BaseViewModel
import me.dungngminh.verygoodblogapp.features.authentication.login.LoginContract
import me.dungngminh.verygoodblogapp.features.authentication.register.RegisterContract.*
import me.dungngminh.verygoodblogapp.utils.exhaustMap
import me.dungngminh.verygoodblogapp.utils.mapNotNull
import timber.log.Timber
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
        val firstnameObservable =
            intentS
                .ofType<ViewIntent.FirstnameChanged>()
                .map { it.firstname }
                .distinctUntilChanged()
                .map { it to getFirstnameErrors(it) }
                .share()

        val lastnameObservable =
            intentS
                .ofType<ViewIntent.LastnameChanged>()
                .map { it.lastname }
                .distinctUntilChanged()
                .map { it to getLastnameErrors(it) }
                .share()

        val usernameObservable =
            intentS
                .ofType<ViewIntent.UsernameChanged>()
                .map { it.username }
                .distinctUntilChanged()
                .map { it to getUsernameErrors(it) }
                .share()

        val bothPasswordsS =
            Observable.combineLatest(
                intentS
                    .ofType<ViewIntent.PasswordChanged>()
                    .map { it.password }
                    .distinctUntilChanged(),
                intentS
                    .ofType<ViewIntent.ConfirmationPasswordChanged>()
                    .map { it.confirmationPassword }
                    .distinctUntilChanged()
            ) { password, confirmationPassword ->
                Pair<String, String>(password,
                    confirmationPassword)
            }.share()

        val passwordObservable =
            bothPasswordsS
                .map { pair ->
                    val password = pair.first
                    password to getPasswordErrors(password)
                }
                .share()

        val confirmationPasswordObservable =
            bothPasswordsS
                .map { pair ->
                    val password = pair.first
                    val confirmationPass = pair.second

                    confirmationPass to
                            getConfirmationPasswordErrors(
                                password = password,
                                confirmationPassword = confirmationPass
                            )
                }
                .share()

        val isMatchPasswordS =
            bothPasswordsS
                .map { pair ->
                    val password = pair.first
                    val confirmationPassword = pair.second

                    password == confirmationPassword
                }
                .distinct()
                .startWithItem(false)

        val registerChanges =
            intentS
                .ofType<ViewIntent.RegisterSubmitted>()
                .withLatestFrom(
                    firstnameObservable,
                    lastnameObservable,
                    usernameObservable,
                    bothPasswordsS,
                ) { _, firstnamePair, lastnamePair, usernamePair, bothPasswordPair ->
                    (firstnamePair to lastnamePair) to
                            (usernamePair to bothPasswordPair)
                }.mapNotNull {
                    val firstnameAndLastnamePair = it.first
                    val usernameAndPasswordPair = it.second

                    val (firstname, firstnameError) = firstnameAndLastnamePair.first
                    val (lastname, lastnameError) = firstnameAndLastnamePair.second

                    val (username, usernameError) = usernameAndPasswordPair.first


                    if (firstnameError.isEmpty() && lastnameError.isEmpty() && usernameError.isEmpty()) {
                        firstname to lastname to username to usernameAndPasswordPair.second
                    } else {
                        null
                    }
                }.exhaustMap { it ->
                    val (firstname, lastname) = it.first.first
                    val username = it.first.second
                    val (password, confirmation) = it.second

                    interactor
                        .register(
                            firstname = firstname,
                            lastname = lastname,
                            username = username,
                            password = password,
                            confirmationPassword = confirmation
                        ).doOnNext {
                            Timber.d("StateChange = $it")
                            eventS.accept(when (it) {
                                is StateChange.ConfirmationPasswordChanged -> return@doOnNext
                                is StateChange.ConfirmationPasswordError -> return@doOnNext
                                StateChange.ConfirmationPasswordFirstChanged -> return@doOnNext
                                is StateChange.FirstnameChanged -> return@doOnNext
                                is StateChange.FirstnameError -> return@doOnNext
                                StateChange.FirstnameFirstChanged -> return@doOnNext
                                is StateChange.LastnameChanged -> return@doOnNext
                                is StateChange.LastnameError -> return@doOnNext
                                StateChange.LastnameFirstChanged -> return@doOnNext
                                StateChange.Loading -> return@doOnNext
                                is StateChange.PasswordChanged -> return@doOnNext
                                is StateChange.PasswordError -> return@doOnNext
                                StateChange.PasswordFirstChanged -> return@doOnNext
                                is StateChange.RegisterFailed -> SingleEvent.RegisterError(it.throwable)
                                StateChange.RegisterSuccess -> SingleEvent.RegisterSuccess
                                is StateChange.UsernameChanged -> return@doOnNext
                                is StateChange.UsernameError -> return@doOnNext
                                StateChange.UsernameFirstChanged -> return@doOnNext
                            })
                        }
                }
        Observable.mergeArray(
            intentS.ofType<ViewIntent.FirstnameFirstChanged>().doOnNext {
                Timber.d("EVENT = $it")
            }
                .map { StateChange.FirstnameFirstChanged },
            intentS.ofType<ViewIntent.LastnameFirstChanged>().doOnNext {
                Timber.d("EVENT = $it")
            }
                .map { StateChange.LastnameFirstChanged },
            intentS.ofType<ViewIntent.UsernameFirstChanged>().doOnNext {
                Timber.d("EVENT = $it")
            }
                .map { StateChange.UsernameFirstChanged },
            intentS.ofType<ViewIntent.PasswordFirstChanged>().doOnNext {
                Timber.d("EVENT = $it")
            }
                .map { StateChange.PasswordFirstChanged },
            intentS.ofType<ViewIntent.ConfirmationPasswordFirstChanged>().doOnNext {
                Timber.d("EVENT = $it")
            }
                .map { StateChange.ConfirmationPasswordFirstChanged },
            firstnameObservable.map { StateChange.FirstnameChanged(it.first) },
            firstnameObservable.map { StateChange.FirstnameError(it.second) },
            lastnameObservable.map { StateChange.LastnameChanged(it.first) },
            lastnameObservable.map { StateChange.LastnameError(it.second) },
            usernameObservable.map { StateChange.UsernameChanged(it.first) },
            passwordObservable.map { StateChange.PasswordChanged(it.first) },
            usernameObservable.map { StateChange.UsernameError(it.second) },
            passwordObservable.map { StateChange.PasswordError(it.second) },
            registerChanges,
        ).observeOn(AndroidSchedulers.mainThread())
            .scan(initialState) { state, change -> change.emit(state) }
            .subscribe(stateS)
    }

    private companion object {
        val emptyFirstname = setOf(ValidationError.EMPTY_FIRSTNAME)
        val emptyLastname = setOf(ValidationError.EMPTY_LASTNAME)

        val tooShortUsername = setOf(ValidationError.TOO_SHORT_USERNAME)
        val emptyUsername = setOf(ValidationError.EMPTY_USERNAME)
        val emptyPassword = setOf(ValidationError.EMPTY_PASSWORD)
        val notMatchPassword = setOf(ValidationError.NOT_MATCH)

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
                else -> emptySet()
            }
        }

        fun getFirstnameErrors(firstname: String): Set<ValidationError> {
            return when {
                firstname.isBlank() -> emptyFirstname
                else -> emptySet()
            }
        }

        fun getLastnameErrors(lastname: String): Set<ValidationError> {
            return when {
                lastname.isBlank() -> emptyLastname
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