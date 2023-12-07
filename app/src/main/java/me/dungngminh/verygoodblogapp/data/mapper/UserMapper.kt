package me.dungngminh.verygoodblogapp.data.mapper

import me.dungngminh.verygoodblogapp.data.remote.model.response.user.UserResponse
import me.dungngminh.verygoodblogapp.models.User

fun UserResponse.toDomainUser(): User {
    return User(
        id = id,
        fullName = fullName,
        email = email,
        following = following,
        follower = follower,
    )
}