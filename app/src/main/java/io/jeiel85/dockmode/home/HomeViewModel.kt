package io.jeiel85.dockmode.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import io.jeiel85.dockmode.AppContainer
import io.jeiel85.dockmode.data.battery.BatteryStateRepository
import io.jeiel85.dockmode.data.calendar.CalendarRepository
import io.jeiel85.dockmode.domain.model.CalendarPermissionState
import io.jeiel85.dockmode.domain.model.ChargingState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

data class HomeUiState(
    val chargingState: ChargingState = ChargingState.Unknown,
    val calendarPermissionState: CalendarPermissionState = CalendarPermissionState.Unknown,
)

class HomeViewModel(
    private val batteryStateRepository: BatteryStateRepository,
    private val calendarRepository: CalendarRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        batteryStateRepository.observeChargingState()
            .onEach { state -> _uiState.value = _uiState.value.copy(chargingState = state) }
            .launchIn(viewModelScope)
        refreshPermissionState()
    }

    fun refreshPermissionState() {
        _uiState.value = _uiState.value.copy(
            calendarPermissionState = if (calendarRepository.hasReadPermission()) {
                CalendarPermissionState.Granted
            } else {
                CalendarPermissionState.NotRequested
            },
        )
    }

    fun onPermissionResult(granted: Boolean, shouldShowRationale: Boolean) {
        _uiState.value = _uiState.value.copy(
            calendarPermissionState = when {
                granted -> CalendarPermissionState.Granted
                shouldShowRationale -> CalendarPermissionState.Denied
                else -> CalendarPermissionState.PermanentlyDenied
            },
        )
    }

    class Factory(private val container: AppContainer) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                "Unknown ViewModel class: $modelClass"
            }
            return HomeViewModel(
                container.batteryStateRepository,
                container.calendarRepository,
            ) as T
        }
    }
}
