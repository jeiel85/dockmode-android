package io.jeiel85.dockmode.domain.model

data class DockModeSettings(
    val clockStyle: ClockStyle = ClockStyle.Minimal,
    val showCalendar: Boolean = true,
    val nightMode: Boolean = true,
    val burnInGuard: Boolean = true,
    val keepScreenOn: Boolean = true,
)
