package io.jeiel85.dockmode.util

import io.jeiel85.dockmode.domain.model.CalendarEventSummary
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class CalendarFiltersTest {

    private val now = 1716_400_000_000L
    private val oneHour = 60L * 60L * 1000L

    private fun event(
        id: Long,
        offsetHours: Long,
        durationHours: Long = 1,
        allDay: Boolean = false,
        title: String = "Event $id",
    ): CalendarEventSummary {
        val start = now + offsetHours * oneHour
        return CalendarEventSummary(
            id = id,
            title = title,
            startsAtMillis = start,
            endsAtMillis = start + durationHours * oneHour,
            allDay = allDay,
        )
    }

    @Test
    fun `todayEvents drops events that already ended`() {
        val events = listOf(
            event(1, offsetHours = -3),
            event(2, offsetHours = 1),
            event(3, offsetHours = 4),
        )
        val result = CalendarFilters.todayEvents(events, now)
        assertEquals(listOf<Long>(2, 3), result.map { it.id })
    }

    @Test
    fun `todayEvents keeps all-day events regardless of time`() {
        val events = listOf(
            event(1, offsetHours = -6, allDay = true),
            event(2, offsetHours = 2),
        )
        val result = CalendarFilters.todayEvents(events, now)
        assertEquals(listOf<Long>(1, 2), result.map { it.id }.sorted())
    }

    @Test
    fun `nextEvent returns earliest upcoming non all-day event`() {
        val events = listOf(
            event(1, offsetHours = -1),
            event(2, offsetHours = 2),
            event(3, offsetHours = 5),
            event(4, offsetHours = 3, allDay = true),
        )
        val next = CalendarFilters.nextEvent(events, now)
        assertEquals(2L, next?.id)
    }

    @Test
    fun `nextEvent returns null when no upcoming events`() {
        val events = listOf(event(1, offsetHours = -1))
        assertNull(CalendarFilters.nextEvent(events, now))
    }
}
