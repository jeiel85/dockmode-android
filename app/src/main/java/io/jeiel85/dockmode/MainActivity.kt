package io.jeiel85.dockmode

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import io.jeiel85.dockmode.home.HomeScreen
import io.jeiel85.dockmode.settings.SettingsScreen
import io.jeiel85.dockmode.standby.StandbyActivity
import io.jeiel85.dockmode.ui.theme.DockModeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val container = (application as DockModeApplication).container
        setContent {
            DockModeTheme {
                var showSettings by remember { mutableStateOf(false) }
                if (showSettings) {
                    SettingsScreen(
                        appContainer = container,
                        onBack = { showSettings = false },
                    )
                } else {
                    HomeScreen(
                        appContainer = container,
                        onStartStandby = {
                            startActivity(Intent(this, StandbyActivity::class.java))
                        },
                        onOpenSettings = { showSettings = true },
                    )
                }
            }
        }
    }
}
