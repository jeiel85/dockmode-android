package io.jeiel85.dockmode.standby.presets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.jeiel85.dockmode.domain.model.StandbyThemePreset
import io.jeiel85.dockmode.standby.StandbyUiState
import io.jeiel85.dockmode.standby.widgets.BatteryCircleWidget
import io.jeiel85.dockmode.standby.widgets.BatteryWidget
import io.jeiel85.dockmode.standby.widgets.CalendarPreviewWidget
import io.jeiel85.dockmode.standby.widgets.DateWidget
import io.jeiel85.dockmode.standby.widgets.PhotoPlaceholderWidget
import io.jeiel85.dockmode.standby.widgets.TimeWidget
import java.util.Locale

@Composable
fun MinimalClockPreset(
    state: StandbyUiState,
    theme: StandbyThemePreset,
    locale: Locale,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TimeWidget(
            nowMillis = state.nowMillis,
            theme = theme,
            locale = locale,
            fontSize = 110.sp,
        )
        Spacer(modifier = Modifier.height(12.dp))
        DateWidget(
            nowMillis = state.nowMillis,
            theme = theme,
            locale = locale,
            fontSize = 18.sp,
        )
        Spacer(modifier = Modifier.height(28.dp))
        BatteryWidget(
            chargingState = state.chargingState,
            theme = theme,
        )
    }
}

@Composable
fun WarmBedsidePreset(
    state: StandbyUiState,
    theme: StandbyThemePreset,
    locale: Locale,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TimeWidget(
            nowMillis = state.nowMillis,
            theme = theme,
            locale = locale,
            fontSize = 100.sp,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Warm Bedside Light",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold,
                color = theme.secondaryTextColor,
            ),
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(24.dp))
        DateWidget(
            nowMillis = state.nowMillis,
            theme = theme,
            locale = locale,
            fontSize = 16.sp,
        )
    }
}

@Composable
fun OledNightClockPreset(
    state: StandbyUiState,
    theme: StandbyThemePreset,
    locale: Locale,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // OLED night preset uses smaller and very dim text
        TimeWidget(
            nowMillis = state.nowMillis,
            theme = theme,
            locale = locale,
            fontSize = 72.sp,
        )
        Spacer(modifier = Modifier.height(8.dp))
        DateWidget(
            nowMillis = state.nowMillis,
            theme = theme,
            locale = locale,
            fontSize = 14.sp,
        )
    }
}

@Composable
fun SplitDashboardPreset(
    state: StandbyUiState,
    theme: StandbyThemePreset,
    locale: Locale,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(32.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier
                .weight(1.1f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
        ) {
            TimeWidget(
                nowMillis = state.nowMillis,
                theme = theme,
                locale = locale,
                fontSize = 82.sp,
            )
            Spacer(modifier = Modifier.height(8.dp))
            DateWidget(
                nowMillis = state.nowMillis,
                theme = theme,
                locale = locale,
                fontSize = 20.sp,
            )
            Spacer(modifier = Modifier.height(16.dp))
            BatteryWidget(
                chargingState = state.chargingState,
                theme = theme,
            )
        }

        Column(
            modifier = Modifier
                .weight(0.9f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
        ) {
            CalendarPreviewWidget(
                todayEvents = state.todayEvents,
                nextEvent = state.nextEvent,
                showCalendar = state.showCalendar,
                permissionState = state.calendarPermissionState,
                locale = locale,
                theme = theme,
            )
        }
    }
}

@Composable
fun CalendarBoardPreset(
    state: StandbyUiState,
    theme: StandbyThemePreset,
    locale: Locale,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(36.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier
                .weight(0.8f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
        ) {
            TimeWidget(
                nowMillis = state.nowMillis,
                theme = theme,
                locale = locale,
                fontSize = 62.sp,
            )
            Spacer(modifier = Modifier.height(4.dp))
            DateWidget(
                nowMillis = state.nowMillis,
                theme = theme,
                locale = locale,
                fontSize = 16.sp,
            )
            Spacer(modifier = Modifier.height(14.dp))
            BatteryWidget(
                chargingState = state.chargingState,
                theme = theme,
            )
        }

        Column(
            modifier = Modifier
                .weight(1.2f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
        ) {
            CalendarPreviewWidget(
                todayEvents = state.todayEvents,
                nextEvent = state.nextEvent,
                showCalendar = state.showCalendar,
                permissionState = state.calendarPermissionState,
                locale = locale,
                theme = theme,
                maxEvents = 4,
            )
        }
    }
}

@Composable
fun BatteryDockPreset(
    state: StandbyUiState,
    theme: StandbyThemePreset,
    locale: Locale,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            TimeWidget(
                nowMillis = state.nowMillis,
                theme = theme,
                locale = locale,
                fontSize = 76.sp,
            )
            Spacer(modifier = Modifier.height(4.dp))
            DateWidget(
                nowMillis = state.nowMillis,
                theme = theme,
                locale = locale,
                fontSize = 16.sp,
            )
        }

        BatteryCircleWidget(
            chargingState = state.chargingState,
            theme = theme,
        )
    }
}

@Composable
fun PhotoFramePlaceholderPreset(
    state: StandbyUiState,
    theme: StandbyThemePreset,
    locale: Locale,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        PhotoPlaceholderWidget(theme = theme)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.BottomStart,
        ) {
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.4f))
                    .padding(horizontal = 12.dp, vertical = 8.dp),
            ) {
                TimeWidget(
                    nowMillis = state.nowMillis,
                    theme = theme,
                    locale = locale,
                    fontSize = 24.sp,
                )
                Text(
                    text = "Photo Frame Placeholder Active",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = theme.textColor.copy(alpha = 0.8f),
                )
            }
        }
    }
}
