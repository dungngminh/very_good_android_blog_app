package me.dungngminh.verygoodblogapp.data.remote.model.body

import com.squareup.moshi.Json

data class LoginUserBody(
    @Json(name = "email") val email: String,
    @Json(name = "password") val password: String,
)
