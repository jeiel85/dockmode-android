package io.jeiel85.dockmode.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView

private val LightSurfaceBg = Color(0xFFF8F9FA)
private val LightSurface = Color(0xFFFFFFFF)
private val LightSurfaceVariant = Color(0xFFF1F3F5)

private val DarkColorScheme = darkColorScheme(
    primary = SpaceBlue,
    secondary = SpaceIndigo,
    tertiary = CalmTeal,
    background = OLEDBlack,
    surface = GlassObsidian,
    onPrimary = OLEDBlack,
    onSecondary = StarlightIvory,
    onBackground = StarlightIvory,
    onSurface = StarlightIvory,
    onSurfaceVariant = NebulaGrey,
    surfaceVariant = CardSlate,
    outline = CharcoalDim,
)

private val LightColorScheme = lightColorScheme(
    primary = CalmTeal,
    secondary = SpaceIndigo,
    tertiary = SpaceBlue,
    background = LightSurfaceBg,
    surface = LightSurface,
    onPrimary = StarlightIvory,
    onSecondary = OLEDBlack,
    onBackground = OLEDBlack,
    onSurface = OLEDBlack,
    onSurfaceVariant = CharcoalDim,
    surfaceVariant = LightSurfaceVariant,
    outline = NebulaGrey,
)

@Composable
fun DockModeTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                window.statusBarColor = colorScheme.background.toArgb()
                window.navigationBarColor = colorScheme.background.toArgb()
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = DockModeTypography,
        content = content,
    )
}
