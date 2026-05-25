package io.jeiel85.dockmode.domain.model

data class DockModeSettings(
    val clockStyle: ClockStyle = ClockStyle.Minimal,
    val selectedThemeId: String = "midnight_glass",
    val showCalendar: Boolean = true,
    val nightMode: Boolean = true,
    val burnInGuard: Boolean = true,
    val keepScreenOn: Boolean = true,
    val autoNightModeByLightSensor: Boolean = false,
    val lightSensorSensitivityLux: Int = 10,
)
