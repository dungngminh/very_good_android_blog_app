package me.dungngminh.verygoodblogapp.features.main.home

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.dungngminh.verygoodblogapp.core.BaseViewModel
import me.dungngminh.verygoodblogapp.features.helpers.LoadingStatus
import me.dungngminh.verygoodblogapp.features.main.home.ui_model.HomePageBlog
import me.dungngminh.verygoodblogapp.features.main.home.ui_model.toFilteredBySearchBlogs
import me.dungngminh.verygoodblogapp.models.Category
import me.dungngminh.verygoodblogapp.repositories.BlogRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
    @Inject
    constructor(private val blogRepository: BlogRepository) :
    BaseViewModel() {
        private val _state = MutableStateFlow(HomeState.initial)

        val state = _state.asStateFlow()

        init {
            loadBlogs()
        }

        private fun loadBlogs() {
            _state.update { it.copy(loadFirstPageStatus = LoadingStatus.LOADING) }
            viewModelScope.launch {
                try {
                    blogRepository
                        .getBlogs(page = _state.value.currentPage)
                        .also { blogs ->
                            val homePageBlogs = HomePageBlog.Other(blogs)
                            val popularBlogs = HomePageBlog.Popular(blogs.take(5))
                            _state.update {
                                it.copy(
                                    blogs = blogs,
                                    homePageBlog = listOf(popularBlogs, homePageBlogs),
                                    currentPage = _state.value.currentPage + 1,
                                    loadFirstPageStatus = LoadingStatus.DONE,
                                )
                            }
                        }
                } catch (e: Exception) {
                    _state.update { it.copy(loadFirstPageStatus = LoadingStatus.ERROR) }
                }
            }
        }

        fun selectCategory(category: Category) {
            _state.update { it.copy(selectedCategory = category) }
            // TODO: add Filter blogs by category in backend side
        }

        fun searchBlogs(term: String) {
            val currentBlogs = _state.value.blogs
            if (term.isEmpty()) {
                val homePageBlogs =
                    listOf(
                        HomePageBlog.Popular(currentBlogs.take(5)),
                        HomePageBlog.Other(currentBlogs),
                    )
                _state.update { it.copy(homePageBlog = homePageBlogs) }
                return
            }
            val filteredBlogs =
                currentBlogs.filter {
                    it.title.contains(term, ignoreCase = true) ||
                        it.content.contains(term, ignoreCase = true)
                }.toFilteredBySearchBlogs()
            _state.update { it.copy(homePageBlog = listOf(filteredBlogs)) }
        }
    }
