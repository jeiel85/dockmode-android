package io.jeiel85.dockmode.util

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Locale
import java.util.TimeZone

class FormattersTest {

    private val utc = TimeZone.getTimeZone("UTC")
    private val sample = 1716_372_000_000L

    @Test
    fun `formatTime renders 24 hour clock`() {
        assertEquals(
            "10:00",
            Formatters.formatTime(sample, locale = Locale.US, is24Hour = true, timeZone = utc),
        )
    }

    @Test
    fun `formatDate uses english pattern for english locale`() {
        val formatted = Formatters.formatDate(sample, locale = Locale.US, timeZone = utc)
        assertEquals("Wed, May 22", formatted)
    }

    @Test
    fun `formatEventTimeRange returns empty for all day`() {
        val result = Formatters.formatEventTimeRange(
            startMillis = sample,
            endMillis = sample + 3600_000L,
            allDay = true,
            locale = Locale.US,
            is24Hour = true,
            timeZone = utc,
        )
        assertEquals("", result)
    }

    @Test
    fun `formatEventTimeRange shows range when end is after start`() {
        val result = Formatters.formatEventTimeRange(
            startMillis = sample,
            endMillis = sample + 3600_000L,
            allDay = false,
            locale = Locale.US,
            is24Hour = true,
            timeZone = utc,
        )
        assertEquals("10:00 – 11:00", result)
    }

    @Test
    fun `formatEventTimeRange shows only start when end is missing`() {
        val result = Formatters.formatEventTimeRange(
            startMillis = sample,
            endMillis = 0L,
            allDay = false,
            locale = Locale.US,
            is24Hour = true,
            timeZone = utc,
        )
        assertEquals("10:00", result)
    }
}
