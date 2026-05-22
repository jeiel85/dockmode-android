package io.jeiel85.dockmode.util

import io.jeiel85.dockmode.domain.model.CalendarEventSummary

object CalendarFilters {
    fun todayEvents(
        events: List<CalendarEventSummary>,
        nowMillis: Long,
    ): List<CalendarEventSummary> {
        return events
            .filter { it.endsAtMillis >= nowMillis || it.allDay }
            .sortedBy { it.startsAtMillis }
    }

    fun nextEvent(
        events: List<CalendarEventSummary>,
        nowMillis: Long,
    ): CalendarEventSummary? {
        return events
            .filter { !it.allDay }
            .filter { it.startsAtMillis >= nowMillis }
            .minByOrNull { it.startsAtMillis }
    }
}
