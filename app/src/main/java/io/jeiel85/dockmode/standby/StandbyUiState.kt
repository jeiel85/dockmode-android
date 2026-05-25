package io.jeiel85.dockmode.standby

import io.jeiel85.dockmode.domain.model.CalendarEventSummary
import io.jeiel85.dockmode.domain.model.CalendarPermissionState
import io.jeiel85.dockmode.domain.model.ChargingState
import io.jeiel85.dockmode.domain.model.ClockStyle

data class StandbyUiState(
    val nowMillis: Long = System.currentTimeMillis(),
    val chargingState: ChargingState = ChargingState.Unknown,
    val clockStyle: ClockStyle = ClockStyle.Minimal,
    val selectedThemeId: String = "midnight_glass",
    val showCalendar: Boolean = true,
    val burnInGuard: Boolean = true,
    val calendarPermissionState: CalendarPermissionState = CalendarPermissionState.Unknown,
    val nextEvent: CalendarEventSummary? = null,
    val todayEvents: List<CalendarEventSummary> = emptyList(),
    val calendarLoadFailed: Boolean = false,
    val isLoadingEvents: Boolean = false,
    val localGalleryImages: List<String> = emptyList(),
    val galleryPermissionState: Boolean = false,
    val nightModeActiveBySensor: Boolean = false,
)

enum class StandbyLaunchMode {
    Activity,
    Dream,
}
