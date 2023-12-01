package me.dungngminh.verygoodblogapp.data.remote

import me.dungngminh.verygoodblogapp.data.remote.body.LoginUserBody
import me.dungngminh.verygoodblogapp.data.remote.body.RegisterUserBody
import me.dungngminh.verygoodblogapp.data.remote.response.auth.BaseResponse
import me.dungngminh.verygoodblogapp.data.remote.response.auth.LoginUserResponse
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @Headers("@: NoAuth")
    @POST("api/auth/login")
    suspend fun login(
        @Body body: LoginUserBody,
    ): BaseResponse<LoginUserResponse>

    @Headers("@: NoAuth")
    @POST("api/auth/register")
    fun register(
        @Body body: RegisterUserBody,
    ): BaseResponse<Any>

    companion object Factory {
        operator fun invoke(retrofit: Retrofit): ApiService = retrofit.create()
    }
}
