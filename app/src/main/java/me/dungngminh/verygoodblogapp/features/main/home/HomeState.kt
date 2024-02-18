package me.dungngminh.verygoodblogapp.features.main.home

import me.dungngminh.verygoodblogapp.features.helpers.LoadingStatus
import me.dungngminh.verygoodblogapp.features.main.home.ui_model.HomePageBlog
import me.dungngminh.verygoodblogapp.models.Blog
import me.dungngminh.verygoodblogapp.models.Category
import me.dungngminh.verygoodblogapp.utils.AppConstants

data class HomeState(
    val blogs: List<Blog>,
    val homePageBlog: List<HomePageBlog>,
    val selectedCategory: Category,
    val loadFirstPageStatus: LoadingStatus,
    val loadMoreStatus: LoadingStatus,
    val currentPage: Int,
) {
    companion object {
        val initial =
            HomeState(
                homePageBlog = emptyList(),
                blogs = emptyList(),
                selectedCategory = Category.ALL,
                loadFirstPageStatus = LoadingStatus.INITIAL,
                loadMoreStatus = LoadingStatus.INITIAL,
                currentPage = AppConstants.START_BLOG_QUERY_PAGE,
            )
    }
}
