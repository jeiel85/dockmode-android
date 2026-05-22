package io.jeiel85.dockmode

import android.app.Application
import io.jeiel85.dockmode.data.battery.BatteryStateRepository
import io.jeiel85.dockmode.data.calendar.CalendarRepository
import io.jeiel85.dockmode.data.settings.SettingsRepository

class DockModeApplication : Application() {
    val container: AppContainer by lazy { DefaultAppContainer(this) }
}

interface AppContainer {
    val batteryStateRepository: BatteryStateRepository
    val calendarRepository: CalendarRepository
    val settingsRepository: SettingsRepository
}

private class DefaultAppContainer(application: Application) : AppContainer {
    override val batteryStateRepository: BatteryStateRepository =
        BatteryStateRepository(application.applicationContext)
    override val calendarRepository: CalendarRepository =
        CalendarRepository(application.applicationContext)
    override val settingsRepository: SettingsRepository =
        SettingsRepository(application.applicationContext)
}
