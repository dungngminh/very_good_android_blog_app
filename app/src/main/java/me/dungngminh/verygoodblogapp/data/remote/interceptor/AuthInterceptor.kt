package me.dungngminh.verygoodblogapp.data.remote.interceptor

import kotlinx.coroutines.runBlocking
import me.dungngminh.verygoodblogapp.data.local.LocalUserDataSource
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import java.net.HttpURLConnection.HTTP_FORBIDDEN
import java.net.HttpURLConnection.HTTP_UNAUTHORIZED
import javax.inject.Inject

class AuthInterceptor
    @Inject
    constructor(private val userLocalSource: LocalUserDataSource) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()

            val customHeaders = request.headers.values("@")
            val newRequest =
                when {
                    "NoAuth" in customHeaders -> request
                    else -> {
                        when (
                            val token =
                                runBlocking { userLocalSource.jwt }.also { Timber.d("Current token: $it") }
                        ) {
                            else ->
                                request
                                    .newBuilder()
                                    .addHeader("Authorization", "Bearer $token")
                                    .build()
                        }
                    }
                }

            val response =
                newRequest.newBuilder()
                    .removeHeader("@")
                    .build()
                    .let(chain::proceed)

            if (response.code in arrayOf(HTTP_UNAUTHORIZED, HTTP_FORBIDDEN)) {
                runBlocking { userLocalSource.deleteAll() }
                Timber.d("Response code is 401 or 404. Removed token. Logout")
            }

            return response
        }
    }
