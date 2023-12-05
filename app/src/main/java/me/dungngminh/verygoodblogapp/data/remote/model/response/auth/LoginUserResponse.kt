package me.dungngminh.verygoodblogapp.data.remote.model.response.auth

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginUserResponse(
    @Json(name = "id") val id: String,
    @Json(name = "token") val token: String,
)
