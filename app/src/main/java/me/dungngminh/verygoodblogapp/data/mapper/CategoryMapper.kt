package me.dungngminh.verygoodblogapp.data.mapper

import me.dungngminh.verygoodblogapp.data.remote.model.response.blog.CategoryResponse
import me.dungngminh.verygoodblogapp.models.Category

fun CategoryResponse.toDomainModel(): Category {
    return when(this){
        CategoryResponse.BUSINESS -> Category.BUSINESS
        CategoryResponse.TECHNOLOGY ->Category.TECHNOLOGY
        CategoryResponse.FASHION -> Category.FASHION
        CategoryResponse.TRAVEL ->Category.TRAVEL
        CategoryResponse.FOOD -> Category.FOOD
        CategoryResponse.EDUCATION -> Category.EDUCATION
    }
}