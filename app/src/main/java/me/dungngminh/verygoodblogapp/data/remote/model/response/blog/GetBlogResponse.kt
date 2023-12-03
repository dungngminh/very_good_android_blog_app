package me.dungngminh.verygoodblogapp.data.remote.model.response.blog

import me.dungngminh.verygoodblogapp.models.BlogCategory
import me.dungngminh.verygoodblogapp.models.User
import java.time.LocalDateTime

data class GetBlogResponse(
    val id: String,
    val title: String,
    val content: String,
    val imageUrl: String,
    val category: BlogCategory,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val creator: User,
)
