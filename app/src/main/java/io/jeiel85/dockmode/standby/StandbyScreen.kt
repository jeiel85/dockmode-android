package io.jeiel85.dockmode.standby

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import io.jeiel85.dockmode.R
import io.jeiel85.dockmode.domain.model.ClockStyle
import io.jeiel85.dockmode.domain.model.StandbyThemePreset
import io.jeiel85.dockmode.standby.presets.BatteryDockPreset
import io.jeiel85.dockmode.standby.presets.CalendarBoardPreset
import io.jeiel85.dockmode.standby.presets.MinimalClockPreset
import io.jeiel85.dockmode.standby.presets.OledNightClockPreset
import io.jeiel85.dockmode.standby.presets.PhotoFramePlaceholderPreset
import io.jeiel85.dockmode.standby.presets.SplitDashboardPreset
import io.jeiel85.dockmode.standby.presets.WarmBedsidePreset
import io.jeiel85.dockmode.standby.theme.StandbyThemeRegistry
import io.jeiel85.dockmode.util.BurnInOffset
import java.util.Locale

@Composable
fun StandbyScreen(
    state: StandbyUiState,
    onClockStyleChanged: (ClockStyle) -> Unit = {},
    @Suppress("UNUSED_PARAMETER") onThemeChanged: (String) -> Unit = {},
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val locale: Locale = configuration.locales.get(0) ?: Locale.getDefault()

    // 1. Fetch current theme preset
    val theme = StandbyThemeRegistry.findById(state.selectedThemeId)

    // 2. Calculate Burn-in Protection offsets
    val (dx, dy) = if (state.burnInGuard || theme.id == "oled_pure_black" || state.clockStyle == ClockStyle.OledNight) {
        BurnInOffset.calculate(state.nowMillis)
    } else {
        0 to 0
    }

    // 3. Dynamic Background rendering (Gradients for Midnight/Aurora)
    val backgroundModifier = when (theme.id) {
        "midnight_glass" -> Modifier.background(
            Brush.radialGradient(
                colors = listOf(Color(0xFF1E1B4B), theme.backgroundColor),
                center = Offset(400f, 300f),
                radius = 1200f,
            ),
        )
        "aurora_gradient" -> Modifier.background(
            Brush.linearGradient(
                colors = listOf(Color(0xFF03001E), Color(0xFF7303C0), Color(0xFFEC38BC)),
                start = Offset(0f, 0f),
                end = Offset(1000f, 800f),
            ),
        )
        else -> Modifier.background(theme.backgroundColor)
    }

    // 4. Gesture Detection (Swipe to change preset, Tap to finish)
    var dragAccumulator by remember { mutableFloatStateOf(0f) }
    val gestureModifier = Modifier.pointerInput(Unit) {
        detectDragGestures(
            onDragStart = { dragAccumulator = 0f },
            onDrag = { change, dragAmount ->
                change.consume()
                dragAccumulator += dragAmount.x
            },
            onDragEnd = {
                val swipeThreshold = 100f
                val styles = ClockStyle.values()
                val currentIndex = styles.indexOf(state.clockStyle)
                if (dragAccumulator > swipeThreshold) {
                    // Swipe Right -> Previous style
                    val prevIndex = if (currentIndex - 1 < 0) styles.size - 1 else currentIndex - 1
                    onClockStyleChanged(styles[prevIndex])
                } else if (dragAccumulator < -swipeThreshold) {
                    // Swipe Left -> Next style
                    val nextIndex = (currentIndex + 1) % styles.size
                    onClockStyleChanged(styles[nextIndex])
                }
            },
        )
    }.pointerInput(Unit) {
        detectTapGestures(
            onTap = {
                (context as? Activity)?.finish()
            },
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .then(backgroundModifier)
            .then(gestureModifier)
            .testTag("standby_screen_root"),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset { IntOffset(dx, dy) }
                .padding(24.dp),
        ) {
            RenderPreset(
                clockStyle = state.clockStyle,
                state = state,
                theme = theme,
                locale = locale,
            )
        }

        // Close Button overlay
        Box(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.TopEnd),
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.08f))
                    .clickable { (context as? Activity)?.finish() },
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = stringResource(id = R.string.action_close_standby),
                    tint = Color.White.copy(alpha = 0.4f),
                    modifier = Modifier.size(18.dp),
                )
            }
        }
    }
}

@Composable
private fun RenderPreset(
    clockStyle: ClockStyle,
    state: StandbyUiState,
    theme: StandbyThemePreset,
    locale: Locale,
) {
    when (clockStyle) {
        ClockStyle.Minimal -> MinimalClockPreset(state, theme, locale)
        ClockStyle.WarmBedside -> WarmBedsidePreset(state, theme, locale)
        ClockStyle.OledNight -> OledNightClockPreset(state, theme, locale)
        ClockStyle.SplitDashboard -> SplitDashboardPreset(state, theme, locale)
        ClockStyle.CalendarFocus -> CalendarBoardPreset(state, theme, locale)
        ClockStyle.BatteryDock -> BatteryDockPreset(state, theme, locale)
        ClockStyle.PhotoFrame -> PhotoFramePlaceholderPreset(state, theme, locale)
        ClockStyle.Digital -> SplitDashboardPreset(state, theme, locale)
    }
}
