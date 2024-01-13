package me.dungngminh.verygoodblogapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class Blog(
    val id: String,
    val title: String,
    val content: String,
    val imageUrl: String,
    val category: Category,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val creator: User,
) : Parcelable
