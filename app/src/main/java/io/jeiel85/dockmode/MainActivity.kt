package io.jeiel85.dockmode

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.jeiel85.dockmode.home.HomeScreen
import io.jeiel85.dockmode.settings.SettingsScreen
import io.jeiel85.dockmode.standby.StandbyActivity
import io.jeiel85.dockmode.ui.theme.DockModeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val appContainer = (application as DockModeApplication).container
        setContent {
            DockModeTheme {
                AppNavigation(
                    appContainer = appContainer,
                    onStartStandby = {
                        startActivity(Intent(this, StandbyActivity::class.java))
                    },
                )
            }
        }
    }
}

private const val ROUTE_HOME = "home"
private const val ROUTE_SETTINGS = "settings"

@Composable
fun AppNavigation(
    appContainer: AppContainer,
    onStartStandby: () -> Unit,
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = ROUTE_HOME,
        modifier = Modifier.fillMaxSize(),
    ) {
        composable(ROUTE_HOME) {
            HomeScreen(
                appContainer = appContainer,
                onStartStandby = onStartStandby,
                onOpenSettings = { navController.navigate(ROUTE_SETTINGS) },
            )
        }
        composable(ROUTE_SETTINGS) {
            SettingsScreen(
                appContainer = appContainer,
                onBack = { navController.popBackStack() },
            )
        }
    }
}
