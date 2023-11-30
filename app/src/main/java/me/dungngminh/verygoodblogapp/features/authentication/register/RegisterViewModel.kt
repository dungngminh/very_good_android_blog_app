package me.dungngminh.verygoodblogapp.features.authentication.register

import dagger.hilt.android.lifecycle.HiltViewModel
import me.dungngminh.verygoodblogapp.core.BaseViewModel
import me.dungngminh.verygoodblogapp.repositories.AuthenticationRepository
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
) : BaseViewModel() {

//    private val initialState = RegisterState()
//    private val registerStateSubject = BehaviorSubject.create<RegisterState>()
//
//    private val intentS = PublishRelay.create<ViewIntent>()
//
//    val stateObservable = registerStateSubject.distinctUntilChanged()
//
//
//    init {
//        val fullNameObservable =
//            intentS
//                .ofType<ViewIntent.FullNameChanged>()
//                .map { it.fullName }
//                .distinctUntilChanged()
//                .map { getFullNameError(it) to it }
//                .share()
//
//        val emailObservable =
//            intentS
//                .ofType<ViewIntent.EmailChanged>()
//                .map { it.email }
//                .distinctUntilChanged()
//                .map { getEmailError(it) to it }
//                .share()
//
//        val passwordObservable =
//            intentS
//                .ofType<ViewIntent.PasswordChanged>()
//                .map { it.password }
//                .distinctUntilChanged()
//                .map { getPasswordError(it) to it }
//                .share()
//
//        val confirmationPasswordInputObservable =
//            intentS
//                .ofType<ViewIntent.ConfirmPasswordChanged>()
//                .map { it.confirmationPassword }
//                .distinctUntilChanged()
//                .share()
//
//        val confirmationPasswordAndError = Observable.combineLatest(
//            passwordObservable,
//            confirmationPasswordInputObservable
//        ) { passwordPair, confirmationPassword ->
//            getConfirmationPasswordError(
//                confirmationPassword,
//                passwordPair.second
//            ) to confirmationPassword
//        }.share()
//
//        val errorsAndFormData = Observable.combineLatest(
//            fullNameObservable,
//            emailObservable,
//            passwordObservable,
//            confirmationPasswordAndError,
//        ) { fullName, email, password, confirmationPassword ->
//            val errors =
//                listOf(fullName.first, email.first, password.first, confirmationPassword.first)
//            val formData = FormData(
//                fullName = fullName.second,
//                email = email.second,
//                password = password.second,
//                confirmationPassword = confirmationPassword.second
//            )
//            errors to formData
//        }.distinctUntilChanged()
//            .share()
//
//        val requestRegisterChange =
//            intentS
//                .ofType<ViewIntent.RequestRegister>()
//                .withLatestFrom(errorsAndFormData) { _, pair -> pair }
//                .filter { (errors, _) -> errors.isEmpty() }
//                .map { (_, data) -> data }
//                .exhaustMap { data ->
//                    authenticationRepository.register(
//                        fullName = data.fullName,
//                        email = data.email,
//                        password = data.password,
//                        confirmationPassword = data.confirmationPassword
//                    )
//                }
//    }
//
//
//    private companion object {
//        fun getEmailError(email: String): ValidationError? {
//            return when {
//                email.isBlank() -> ValidationError.EMPTY
//                !email.isEmail() -> ValidationError.INVALID
//                else -> null
//            }
//        }
//
//        fun getPasswordError(password: String): ValidationError? {
//            return when {
//                password.isBlank() -> ValidationError.EMPTY
//                password.length < AppConstants.MIN_PASSWORD_LENGTH -> ValidationError.TOO_SHORT
//                else -> null
//            }
//        }
//
//
//        fun getFullNameError(fullName: String): ValidationError? {
//            return when {
//                fullName.isBlank() -> ValidationError.EMPTY
//                else -> null
//            }
//        }
//
//        fun getConfirmationPasswordError(
//            confirmationPassword: String,
//            password: String,
//        ): ValidationError? {
//            return when {
//                confirmationPassword != password -> ValidationError.NOT_MATCH
//                else -> null
//            }
//        }
//    }
}