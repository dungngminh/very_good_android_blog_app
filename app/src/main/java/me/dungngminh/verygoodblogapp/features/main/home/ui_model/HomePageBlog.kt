package me.dungngminh.verygoodblogapp.features.main.home.ui_model

import me.dungngminh.verygoodblogapp.models.Blog
import me.dungngminh.verygoodblogapp.models.Category

sealed class HomePageBlog {
    data class Popular(val blogs: List<Blog>) : HomePageBlog()

    data class Other(val blogs: List<Blog>) : HomePageBlog()

    data class FilteredBySearch(val blogs: List<Blog>) : HomePageBlog()

    data class FilteredByCategory(val blogs: List<Blog>, val category: Category) : HomePageBlog()
}

fun List<Blog>.toOtherBlogs(): HomePageBlog.Other = HomePageBlog.Other(this)

fun List<Blog>.toPopularBlogs(): HomePageBlog.Popular = HomePageBlog.Popular(this)

fun List<Blog>.toFilteredBySearchBlogs(): HomePageBlog.FilteredBySearch = HomePageBlog.FilteredBySearch(this)

fun List<Blog>.toFilteredByCategoryBlogs(category: Category): HomePageBlog.FilteredByCategory =
    HomePageBlog.FilteredByCategory(this, category)
