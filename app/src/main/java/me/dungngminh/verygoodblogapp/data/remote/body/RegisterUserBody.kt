package me.dungngminh.verygoodblogapp.data.remote.body

import com.squareup.moshi.Json

data class RegisterUserBody(
    @Json(name = "username") val username: String,
    @Json(name = "password") val password: String,
    @Json(name = "confirmation_password") val confirmationPassword: String,
    @Json(name = "first_name") val firstname: String,
    @Json(name = "last_name") val lastname: String,
)