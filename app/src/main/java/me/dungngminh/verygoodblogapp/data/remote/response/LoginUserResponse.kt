package me.dungngminh.verygoodblogapp.data.remote.response

import com.squareup.moshi.Json

data class LoginUserResponse(
    @Json(name = "id") val id: String,
    @Json(name = "jwt") val jwt: String,
    @Json(name = "message") val message: String,
)