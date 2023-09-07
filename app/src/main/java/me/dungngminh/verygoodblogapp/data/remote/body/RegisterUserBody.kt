package me.dungngminh.verygoodblogapp.data.remote.body

import com.squareup.moshi.Json

data class RegisterUserBody(
    @Json(name = "email") val email: String,
    @Json(name = "password") val password: String,
    @Json(name = "confirmation_password") val confirmationPassword: String,
    @Json(name = "full_name") val fullName: String,
)