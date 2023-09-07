package me.dungngminh.verygoodblogapp.data.remote.response.auth

import com.squareup.moshi.Json

data class BaseResponse<T : Any>(
    @Json(name = "success") val success: Boolean,
    @Json(name = "message") val message: String,
    @Json(name = "data") val data : T?
){
    fun unwrap(): T{
        return data!!
    }
    data class Pagination(
        @Json(name = "current_page") val currentPage: Int,
        @Json(name = "limit") val limit: Int,
        @Json(name = "total_count") val totalCount: Int,
    )
}


