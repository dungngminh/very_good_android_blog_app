package me.dungngminh.verygoodblogapp.data.remote.model.response.blog

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonClass
import com.squareup.moshi.ToJson

enum class CategoryResponse(val categoryName: String) {
    BUSINESS("business"),
    TECHNOLOGY("technology"),
    FASHION("fashion"),
    TRAVEL("travel"),
    FOOD("food"),
    EDUCATION("education");
}