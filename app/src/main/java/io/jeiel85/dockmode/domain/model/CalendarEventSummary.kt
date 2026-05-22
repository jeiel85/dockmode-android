package io.jeiel85.dockmode.domain.model

data class CalendarEventSummary(
    val id: Long,
    val title: String,
    val startsAtMillis: Long,
    val endsAtMillis: Long,
    val allDay: Boolean,
)
