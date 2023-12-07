package me.dungngminh.verygoodblogapp.repositories

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import me.dungngminh.verygoodblogapp.data.local.LocalUserDataSource
import me.dungngminh.verygoodblogapp.data.mapper.toDomainUser
import me.dungngminh.verygoodblogapp.data.remote.ApiService
import me.dungngminh.verygoodblogapp.di.IoDispatcher
import me.dungngminh.verygoodblogapp.models.User
import timber.log.Timber
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiService: ApiService,
    private val localUserDataSource: LocalUserDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) {

    suspend fun getUserProfile(): User {
        val userId = localUserDataSource.userId ?: ""
        return getUserById(id = userId)
    }

    suspend fun getUserById(id: String): User {
        return withContext(ioDispatcher) {
            apiService
                .getUserById(id)
                .unwrap()
                .toDomainUser()
                .also { Timber.d("GetUserById(id=$id)") }
        }
    }
}