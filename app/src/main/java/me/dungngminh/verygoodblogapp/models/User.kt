package me.dungngminh.verygoodblogapp.models

data class User(
    val id: String,
    val fullName: String,
    val email: String,
    val following: Int,
    val follower: Int,
)
