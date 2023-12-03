package me.dungngminh.verygoodblogapp.data.remote.model


enum class CategoryModel(
    val categoryName: String,
){
    BUSINESS("business"),
    TECH("tech"),
    FASHION("fashion"),
    TRAVEL("travel"),
    FOOD("food"),
    EDUCATION("education");
}