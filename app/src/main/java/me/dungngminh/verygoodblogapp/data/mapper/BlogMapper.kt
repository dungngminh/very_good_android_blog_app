package me.dungngminh.verygoodblogapp.data.mapper

import me.dungngminh.verygoodblogapp.data.remote.model.response.blog.BlogResponse
import me.dungngminh.verygoodblogapp.models.Blog
import me.dungngminh.verygoodblogapp.models.User

fun BlogResponse.toDomainModel(): Blog {
    return Blog(
        id = id,
        title = title,
        content = content,
        imageUrl = imageUrl,
        category = category.toDomainModel(),
        createdAt = createdAt,
        updatedAt = updatedAt,
        creator = User(
            id = creator.id,
            fullName = creator.fullName,
            email = creator.email,
            following = creator.following,
            follower = creator.follower
        )
    )
}
