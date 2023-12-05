package me.dungngminh.verygoodblogapp.data.remote.model.body.auth

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegisterUserBody(
    @Json(name = "email") val email: String,
    @Json(name = "password") val password: String,
    @Json(name = "confirmation_password") val confirmationPassword: String,
    @Json(name = "full_name") val fullName: String,
)
