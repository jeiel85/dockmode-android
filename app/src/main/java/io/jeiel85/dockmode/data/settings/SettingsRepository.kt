package io.jeiel85.dockmode.data.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import io.jeiel85.dockmode.domain.model.ClockStyle
import io.jeiel85.dockmode.domain.model.DockModeSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dockModeDataStore: DataStore<Preferences> by preferencesDataStore(name = "dockmode_settings")

class SettingsRepository(private val context: Context) {
    private object Keys {
        val ClockStyleKey = stringPreferencesKey("clock_style")
        val ShowCalendar = booleanPreferencesKey("show_calendar")
        val NightMode = booleanPreferencesKey("night_mode")
        val BurnInGuard = booleanPreferencesKey("burn_in_guard")
        val KeepScreenOn = booleanPreferencesKey("keep_screen_on")
    }

    val settings: Flow<DockModeSettings> = context.dockModeDataStore.data.map { prefs ->
        DockModeSettings(
            clockStyle = prefs[Keys.ClockStyleKey]?.toClockStyle() ?: ClockStyle.Minimal,
            showCalendar = prefs[Keys.ShowCalendar] ?: true,
            nightMode = prefs[Keys.NightMode] ?: true,
            burnInGuard = prefs[Keys.BurnInGuard] ?: true,
            keepScreenOn = prefs[Keys.KeepScreenOn] ?: true,
        )
    }

    suspend fun setClockStyle(style: ClockStyle) {
        context.dockModeDataStore.edit { it[Keys.ClockStyleKey] = style.name }
    }

    suspend fun setShowCalendar(enabled: Boolean) {
        context.dockModeDataStore.edit { it[Keys.ShowCalendar] = enabled }
    }

    suspend fun setNightMode(enabled: Boolean) {
        context.dockModeDataStore.edit { it[Keys.NightMode] = enabled }
    }

    suspend fun setBurnInGuard(enabled: Boolean) {
        context.dockModeDataStore.edit { it[Keys.BurnInGuard] = enabled }
    }

    suspend fun setKeepScreenOn(enabled: Boolean) {
        context.dockModeDataStore.edit { it[Keys.KeepScreenOn] = enabled }
    }

    private fun String.toClockStyle(): ClockStyle =
        runCatching { ClockStyle.valueOf(this) }.getOrDefault(ClockStyle.Minimal)
}
