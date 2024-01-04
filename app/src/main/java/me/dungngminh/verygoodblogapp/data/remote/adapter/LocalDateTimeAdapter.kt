package me.dungngminh.verygoodblogapp.data.remote.adapter

import android.os.Build
import androidx.annotation.RequiresApi
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.ISO_ZONED_DATE_TIME

@RequiresApi(Build.VERSION_CODES.O)
class LocalDateTimeAdapter(private var formatter: DateTimeFormatter = ISO_ZONED_DATE_TIME) {

    @FromJson
    fun fromJson(value: String): LocalDateTime? {
        return LocalDateTime.parse(value, formatter)
    }

    @ToJson
    fun toJson(dateTime: LocalDateTime): String {
        return dateTime.format(formatter)
    }
}