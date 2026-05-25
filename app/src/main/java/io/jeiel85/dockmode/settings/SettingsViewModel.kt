package io.jeiel85.dockmode.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import io.jeiel85.dockmode.AppContainer
import io.jeiel85.dockmode.data.settings.SettingsRepository
import io.jeiel85.dockmode.domain.model.ClockStyle
import io.jeiel85.dockmode.domain.model.DockModeSettings
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val repository: SettingsRepository,
) : ViewModel() {

    val settings: StateFlow<DockModeSettings> = repository.settings
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000L), DockModeSettings())

    fun setClockStyle(style: ClockStyle) = viewModelScope.launch { repository.setClockStyle(style) }

    fun setThemeId(themeId: String) = viewModelScope.launch { repository.setSelectedThemeId(themeId) }

    fun setShowCalendar(enabled: Boolean) = viewModelScope.launch { repository.setShowCalendar(enabled) }

    fun setNightMode(enabled: Boolean) = viewModelScope.launch { repository.setNightMode(enabled) }

    fun setBurnInGuard(enabled: Boolean) = viewModelScope.launch { repository.setBurnInGuard(enabled) }

    fun setKeepScreenOn(enabled: Boolean) = viewModelScope.launch { repository.setKeepScreenOn(enabled) }

    class Factory(private val container: AppContainer) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
                "Unknown ViewModel class: $modelClass"
            }
            return SettingsViewModel(container.settingsRepository) as T
        }
    }
}
