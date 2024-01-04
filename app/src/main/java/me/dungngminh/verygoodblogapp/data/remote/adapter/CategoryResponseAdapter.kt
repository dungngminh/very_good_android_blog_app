package me.dungngminh.verygoodblogapp.data.remote.adapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import me.dungngminh.verygoodblogapp.data.remote.model.response.blog.CategoryResponse

class CategoryResponseAdapter {
    @ToJson
    fun toJson(type: CategoryResponse): String = type.categoryName

    @FromJson
    fun fromJson(value: String): CategoryResponse =
        CategoryResponse.entries.firstOrNull { it.categoryName == value }
            ?: CategoryResponse.BUSINESS
}