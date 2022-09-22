package me.dungngminh.verygoodblogapp.repositories

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import me.dungngminh.verygoodblogapp.data.local.LocalUserDataSource
import me.dungngminh.verygoodblogapp.data.remote.ApiService
import me.dungngminh.verygoodblogapp.data.remote.body.LoginUserBody
import me.dungngminh.verygoodblogapp.data.remote.body.RegisterUserBody
import me.dungngminh.verygoodblogapp.data.remote.response.LoginUserResponse
import me.dungngminh.verygoodblogapp.data.remote.response.RegisterUserResponse
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationRepository @Inject constructor(
    private val apiService: ApiService,
    private val localDataSource: LocalUserDataSource,
) {

    fun login(username: String, password: String): Single<LoginUserResponse> =
        apiService.login(LoginUserBody(username, password)).doOnSuccess {
            Timber.tag(AuthenticationRepository.TAG)
            Timber.d("Login Successfully: $it")
        }.doOnError {
            Timber.tag(AuthenticationRepository.TAG)
            Timber.d("Login Error: $it")
        }.flatMap { response ->
            saveLocal(response).onErrorComplete().andThen(Single.just(response))
        }.subscribeOn(Schedulers.io())

    fun register(
        username: String,
        password: String,
        confirmationPassword: String,
        firstname: String,
        lastname: String,
    ): Single<RegisterUserResponse> = apiService.register(RegisterUserBody(username,
        password,
        confirmationPassword,
        firstname,
        lastname)).doOnSuccess {
        Timber.tag(AuthenticationRepository.TAG)
        Timber.d("Register Successfully: $it")
    }.doOnError {
        Timber.tag(AuthenticationRepository.TAG)
        Timber.d("Register Error: $it")
    }.subscribeOn(Schedulers.io())

    private companion object {
        private const val TAG = "AuthenticationRepo"
    }

    private fun saveLocal(response: LoginUserResponse): Completable = Completable.fromCallable {
        localDataSource.saveTokenAndId(response.jwt, response.id)
    }.subscribeOn(Schedulers.io())

}