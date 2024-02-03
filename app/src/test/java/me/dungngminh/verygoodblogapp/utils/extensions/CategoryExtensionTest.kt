package me.dungngminh.verygoodblogapp.utils.extensions

import me.dungngminh.verygoodblogapp.R
import me.dungngminh.verygoodblogapp.models.Category
import org.junit.Assert.assertEquals
import org.junit.Test

class CategoryExtensionTest {
    @Test
    fun categoryExtension_getLocalizedName_ReturnsAll() {
        val category = Category.ALL
        assertEquals(category.getLocalizedName(), R.string.all)
    }

    @Test
    fun categoryExtension_getLocalizedName_ReturnsBusiness() {
        val category = Category.BUSINESS
        assertEquals(category.getLocalizedName(), R.string.business)
    }

    @Test
    fun categoryExtension_getLocalizedName_ReturnsFashion() {
        val category = Category.FASHION
        assertEquals(category.getLocalizedName(), R.string.fashion)
    }

    @Test
    fun categoryExtension_getLocalizedName_ReturnsTravel() {
        val category = Category.TRAVEL
        assertEquals(category.getLocalizedName(), R.string.travel)
    }

    @Test
    fun categoryExtension_getLocalizedName_ReturnsEducation() {
        val category = Category.EDUCATION
        assertEquals(category.getLocalizedName(), R.string.education)
    }

    @Test
    fun categoryExtension_getLocalizedName_ReturnsTechnology() {
        val category = Category.TECHNOLOGY
        assertEquals(category.getLocalizedName(), R.string.technology)
    }

    @Test
    fun categoryExtension_getLocalizedName_ReturnsFood() {
        val category = Category.FOOD
        assertEquals(category.getLocalizedName(), R.string.food)
    }
}
