package me.dungngminh.verygoodblogapp.data.remote

import me.dungngminh.verygoodblogapp.data.remote.model.body.auth.LoginUserBody
import me.dungngminh.verygoodblogapp.data.remote.model.body.auth.RegisterUserBody
import me.dungngminh.verygoodblogapp.data.remote.model.response.BaseResponse
import me.dungngminh.verygoodblogapp.data.remote.model.response.auth.LoginUserResponse
import me.dungngminh.verygoodblogapp.data.remote.model.response.blog.BlogResponse
import me.dungngminh.verygoodblogapp.data.remote.model.response.user.UserResponse
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

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

    @GET("api/blogs")
    suspend fun getBlogs(
        @Query("limit") limit: Int,
        @Query("page") page: Int,
    ): BaseResponse<List<BlogResponse>>

    @GET("api/users/{id}/profiles")
    suspend fun getUserById(@Path("id") id: String): BaseResponse<UserResponse>

    companion object Factory {
        operator fun invoke(retrofit: Retrofit): ApiService = retrofit.create()
    }
}
