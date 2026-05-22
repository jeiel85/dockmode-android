package io.jeiel85.dockmode.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DockModeDarkColors = darkColorScheme(
    primary = StandbyAccent,
    onPrimary = StandbyBackground,
    background = StandbyBackground,
    onBackground = StandbyOnBackground,
    surface = StandbySurface,
    onSurface = StandbyOnBackground,
    onSurfaceVariant = StandbyOnSurfaceSubtle,
    tertiary = StandbyWarning,
)

@Composable
fun DockModeTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DockModeDarkColors,
        typography = DockModeTypography,
        content = content,
    )
}
