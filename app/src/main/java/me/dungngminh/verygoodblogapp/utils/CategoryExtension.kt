package me.dungngminh.verygoodblogapp.utils

import androidx.annotation.StringRes
import me.dungngminh.verygoodblogapp.R
import me.dungngminh.verygoodblogapp.models.Category

fun Category.getLocalizedName(): Int {
    return when (this) {
        Category.ALL -> R.string.all
        Category.BUSINESS -> R.string.business
        Category.TECHNOLOGY -> R.string.technology
        Category.FASHION -> R.string.fashion
        Category.TRAVEL -> R.string.travel
        Category.FOOD -> R.string.food
        Category.EDUCATION -> R.string.education
    }
}