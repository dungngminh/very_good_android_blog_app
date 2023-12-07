package me.dungngminh.verygoodblogapp.data.remote.model.response.blog

import com.squareup.moshi.Json
import java.time.LocalDateTime

import com.squareup.moshi.JsonClass
import me.dungngminh.verygoodblogapp.data.remote.model.response.user.UserResponse

@JsonClass(generateAdapter = true)
data class BlogResponse(
    @Json(name = "id") val id: String,
    @Json(name = "title") val title: String,
    @Json(name = "content") val content: String,
    @Json(name = "image_url") val imageUrl: String,
    @Json(name = "category") val category: CategoryResponse,
    @Json(name = "created_at") val createdAt: LocalDateTime,
    @Json(name = "updated_at") val updatedAt: LocalDateTime,
    @Json(name = "creator") val creator: UserResponse,
)
