package io.jeiel85.dockmode.standby

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import io.jeiel85.dockmode.AppContainer
import io.jeiel85.dockmode.data.battery.BatteryStateRepository
import io.jeiel85.dockmode.data.calendar.CalendarRepository
import io.jeiel85.dockmode.data.settings.SettingsRepository
import io.jeiel85.dockmode.domain.model.CalendarPermissionState
import io.jeiel85.dockmode.domain.model.ClockStyle
import io.jeiel85.dockmode.util.CalendarFilters
import io.jeiel85.dockmode.util.TickerFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class StandbyViewModel(
    private val batteryStateRepository: BatteryStateRepository,
    private val calendarRepository: CalendarRepository,
    private val settingsRepository: SettingsRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(StandbyUiState())
    val uiState: StateFlow<StandbyUiState> = _uiState.asStateFlow()

    init {
        combine(
            TickerFlow.seconds(),
            batteryStateRepository.observeChargingState(),
            settingsRepository.settings,
        ) { now, charging, settings ->
            Triple(now, charging, settings)
        }.onEach { (now, charging, settings) ->
            _uiState.value = _uiState.value.copy(
                nowMillis = now,
                chargingState = charging,
                clockStyle = settings.clockStyle,
                selectedThemeId = settings.selectedThemeId,
                showCalendar = settings.showCalendar,
                burnInGuard = settings.burnInGuard,
            )
        }.launchIn(viewModelScope)

        refreshCalendar()
    }

    fun setClockStyle(style: ClockStyle) {
        viewModelScope.launch {
            settingsRepository.setClockStyle(style)
        }
    }

    fun setSelectedThemeId(themeId: String) {
        viewModelScope.launch {
            settingsRepository.setSelectedThemeId(themeId)
        }
    }

    fun refreshCalendar() {
        viewModelScope.launch {
            if (!calendarRepository.hasReadPermission()) {
                _uiState.value = _uiState.value.copy(
                    calendarPermissionState = CalendarPermissionState.NotRequested,
                    todayEvents = emptyList(),
                    nextEvent = null,
                    calendarLoadFailed = false,
                    isLoadingEvents = false,
                )
                return@launch
            }
            _uiState.value = _uiState.value.copy(isLoadingEvents = true)
            val result = calendarRepository.loadTodayEvents()
            val now = System.currentTimeMillis()
            result.onSuccess { events ->
                val today = CalendarFilters.todayEvents(events, now)
                val next = CalendarFilters.nextEvent(events, now)
                _uiState.value = _uiState.value.copy(
                    calendarPermissionState = CalendarPermissionState.Granted,
                    todayEvents = today,
                    nextEvent = next,
                    calendarLoadFailed = false,
                    isLoadingEvents = false,
                )
            }.onFailure {
                _uiState.value = _uiState.value.copy(
                    calendarPermissionState = CalendarPermissionState.Granted,
                    calendarLoadFailed = true,
                    isLoadingEvents = false,
                )
            }
        }
    }

    class Factory(private val container: AppContainer) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass.isAssignableFrom(StandbyViewModel::class.java)) {
                "Unknown ViewModel class: $modelClass"
            }
            return StandbyViewModel(
                container.batteryStateRepository,
                container.calendarRepository,
                container.settingsRepository,
            ) as T
        }
    }
}
