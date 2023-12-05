package me.dungngminh.verygoodblogapp.repositories

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import me.dungngminh.verygoodblogapp.data.mapper.toDomainModel
import me.dungngminh.verygoodblogapp.data.remote.ApiService
import me.dungngminh.verygoodblogapp.di.IoDispatcher
import me.dungngminh.verygoodblogapp.models.Blog
import me.dungngminh.verygoodblogapp.utils.AppConstants
import timber.log.Timber
import javax.inject.Inject

class BlogRepository @Inject constructor(
    private val apiService: ApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {
    suspend fun getBlogs(
        limit: Int = AppConstants.BLOGS_QUERY_LIMIT,
        page: Int,
    ): List<Blog> {
        return withContext(ioDispatcher) {
            apiService
                .getBlogs(limit = limit, page = page)
                .unwrap()
                .map { it.toDomainModel() }
                .also { Timber.d("GetBlogs { page = $page, limit = $limit } ") }
        }
    }

}