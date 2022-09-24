package me.dungngminh.verygoodblogapp.data.remote

import io.reactivex.rxjava3.core.Single
import me.dungngminh.verygoodblogapp.data.remote.body.LoginUserBody
import me.dungngminh.verygoodblogapp.data.remote.body.RegisterUserBody
import me.dungngminh.verygoodblogapp.data.remote.response.LoginUserResponse
import me.dungngminh.verygoodblogapp.data.remote.response.RegisterUserResponse
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST


interface ApiService {

    @Headers("@: NoAuth")
    @POST("auth/login")
    fun login(@Body loginUserBody: LoginUserBody): Single<LoginUserResponse>

    @Headers("@: NoAuth")
    @POST("auth/register")
    fun register(@Body registerUserBody: RegisterUserBody): Single<RegisterUserResponse>



    companion object Factory{
        operator fun invoke(retrofit: Retrofit): ApiService = retrofit.create()
    }
}