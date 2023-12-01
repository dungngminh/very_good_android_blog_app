package me.dungngminh.verygoodblogapp.data.remote.response.auth

import com.squareup.moshi.Json

data class LoginUserResponse(
    @Json(name = "id") val id: String,
    @Json(name = "token") val token: String,
)
