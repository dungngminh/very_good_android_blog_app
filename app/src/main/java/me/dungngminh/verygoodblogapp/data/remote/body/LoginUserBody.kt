package me.dungngminh.verygoodblogapp.data.remote.body

import com.squareup.moshi.Json

data class LoginUserBody(
    @Json(name = "username") val username: String,
    @Json(name = "password") val password: String,
)
