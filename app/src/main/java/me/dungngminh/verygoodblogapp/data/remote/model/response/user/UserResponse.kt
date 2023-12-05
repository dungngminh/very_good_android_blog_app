package me.dungngminh.verygoodblogapp.data.remote.model.response.user

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserResponse(
    @Json(name = "id") val id: String,
    @Json(name = "full_name") val fullName: String,
    @Json(name = "email") val email: String,
    @Json(name = "avatar_url") val avatarUrl: String? = null,
    @Json(name = "following") val following: Int,
    @Json(name = "follower") val follower: Int,
)
