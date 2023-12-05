package me.dungngminh.verygoodblogapp.data.remote.model.body.auth

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginUserBody(
    @Json(name = "email") val email: String,
    @Json(name = "password") val password: String,
)
