package me.dungngminh.verygoodblogapp.utils.extensions

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDateTime

class DateTimeExtensionKtTest {
    @Test
    fun dateTimeExtensionKt_getFormattedDate_ReturnsFormattedDate() {
        val date = LocalDateTime.of(2024, 1, 1, 0, 0, 0)
        assertEquals(date.toLong(), 1704042000000)
    }

    @Test
    fun dateTimeExtensionKt_getTimeAgo_ReturnsTimeAgo() {
        val date = LocalDateTime.now()
        assertEquals(date.toTimeAgo(), "just now")
    }
}
