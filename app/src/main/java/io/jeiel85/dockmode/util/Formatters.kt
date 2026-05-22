package io.jeiel85.dockmode.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object Formatters {
    fun formatTime(
        millis: Long,
        locale: Locale = Locale.getDefault(),
        is24Hour: Boolean = true,
        timeZone: TimeZone = TimeZone.getDefault(),
    ): String {
        val pattern = if (is24Hour) "HH:mm" else "hh:mm a"
        return SimpleDateFormat(pattern, locale).apply { this.timeZone = timeZone }.format(Date(millis))
    }

    fun formatDate(
        millis: Long,
        locale: Locale = Locale.getDefault(),
        timeZone: TimeZone = TimeZone.getDefault(),
    ): String {
        val pattern = when (locale.language) {
            "ko" -> "M월 d일 EEEE"
            "ja" -> "M月d日 EEEE"
            "zh" -> "M月d日 EEEE"
            else -> "EEE, MMM d"
        }
        return SimpleDateFormat(pattern, locale).apply { this.timeZone = timeZone }.format(Date(millis))
    }

    fun formatEventTimeRange(
        startMillis: Long,
        endMillis: Long,
        allDay: Boolean,
        locale: Locale = Locale.getDefault(),
        is24Hour: Boolean = true,
        timeZone: TimeZone = TimeZone.getDefault(),
    ): String {
        if (allDay) {
            return ""
        }
        val start = formatTime(startMillis, locale, is24Hour, timeZone)
        if (endMillis <= startMillis) return start
        val end = formatTime(endMillis, locale, is24Hour, timeZone)
        return "$start – $end"
    }
}
