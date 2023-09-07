package me.dungngminh.verygoodblogapp.repositories

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import me.dungngminh.verygoodblogapp.data.local.LocalUserDataSource
import me.dungngminh.verygoodblogapp.data.remote.ApiService
import me.dungngminh.verygoodblogapp.data.remote.body.LoginUserBody
import me.dungngminh.verygoodblogapp.data.remote.body.RegisterUserBody
import me.dungngminh.verygoodblogapp.data.remote.response.auth.LoginUserResponse
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationRepository @Inject constructor(
    private val apiService: ApiService,
    private val localDataSource: LocalUserDataSource,
) {

    fun login(username: String, password: String): Completable =
        Completable.fromCallable {
            apiService
                .login(loginUserBody = LoginUserBody(username = username, password = password))
                .doOnSuccess {
                    Timber.d("Login successfully, $it")
                }.doOnError {
                    Timber.d("Login Error, $it")
                }
                .flatMap { response ->
                    val loginResponse = response.unwrap()
                    saveLocal(loginResponse)
                        .onErrorComplete()
                        .andThen(Single.just(loginResponse))
                }
        }.subscribeOn(Schedulers.io())


    fun register(
        fullName: String,
        email: String,
        password: String,
        confirmationPassword: String
    ): Single<Any> =
        apiService
            .register(
                RegisterUserBody(
                    email = email,
                    password = password,
                    confirmationPassword = confirmationPassword,
                    fullName = fullName
                )
            )
            .flatMap { Single.just(it.unwrap()) }
            .subscribeOn(Schedulers.io())

    private companion object {
        private const val TAG = "AuthenticationRepo"
    }

    private fun saveLocal(response: LoginUserResponse): Completable = Completable.fromCallable {
        localDataSource.saveTokenAndId(response.token, response.id)
    }.subscribeOn(Schedulers.io())

}