package me.dungngminh.verygoodblogapp.data.remote.model.response

import com.squareup.moshi.Json

data class BaseResponse<T : Any>(
    @Json(name = "success") val success: Boolean,
    @Json(name = "message") val message: String,
    @Json(name = "result") val data: T?,
) {
    fun unwrap(): T {
        return data!!
    }
}