package me.dungngminh.verygoodblogapp.data.remote

import me.dungngminh.verygoodblogapp.data.remote.model.body.LoginUserBody
import me.dungngminh.verygoodblogapp.data.remote.model.body.RegisterUserBody
import me.dungngminh.verygoodblogapp.data.remote.model.response.BaseResponse
import me.dungngminh.verygoodblogapp.data.remote.model.response.auth.LoginUserResponse
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
    suspend fun register(
        @Body body: RegisterUserBody,
    ): BaseResponse<Any>

    companion object Factory {
        operator fun invoke(retrofit: Retrofit): ApiService = retrofit.create()
    }
}
