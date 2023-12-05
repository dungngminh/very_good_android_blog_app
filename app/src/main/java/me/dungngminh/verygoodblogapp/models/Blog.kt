package me.dungngminh.verygoodblogapp.models

import java.time.LocalDateTime

data class Blog(
    val id: String,
    val title: String,
    val content: String,
    val imageUrl: String,
    val category: Category,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val creator: User,
)
