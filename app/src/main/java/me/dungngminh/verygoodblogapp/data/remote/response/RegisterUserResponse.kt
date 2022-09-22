package me.dungngminh.verygoodblogapp.data.remote.response
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegisterUserResponse(
    @Json(name = "avatar")
    val avatar: String,
    @Json(name = "first_name")
    val firstName: String,
    @Json(name = "follower_count")
    val followerCount: Int,
    @Json(name = "following_count")
    val followingCount: Int,
    @Json(name = "_id")
    val id: String,
    @Json(name = "last_name")
    val lastName: String,
    @Json(name = "num_blog")
    val numBlog: Int,
    @Json(name = "username")
    val username: String
)