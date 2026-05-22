package io.jeiel85.dockmode.standby

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import io.jeiel85.dockmode.R
import io.jeiel85.dockmode.domain.model.CalendarEventSummary
import io.jeiel85.dockmode.domain.model.CalendarPermissionState
import io.jeiel85.dockmode.domain.model.ChargingState
import io.jeiel85.dockmode.domain.model.ClockStyle
import io.jeiel85.dockmode.util.BurnInOffset
import io.jeiel85.dockmode.util.Formatters
import java.util.Locale

@Composable
fun StandbyScreen(state: StandbyUiState) {
    val configuration = LocalConfiguration.current
    val locale: Locale = configuration.locales.get(0) ?: Locale.getDefault()
    val (dx, dy) = if (state.burnInGuard) {
        BurnInOffset.calculate(state.nowMillis)
    } else {
        0 to 0
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset { IntOffset(dx, dy) },
        ) {
            when (state.clockStyle) {
                ClockStyle.Minimal -> MinimalLayout(state, locale)
                ClockStyle.Digital -> DigitalLayout(state, locale)
                ClockStyle.CalendarFocus -> CalendarFocusLayout(state, locale)
            }
        }
    }
}

@Composable
private fun MinimalLayout(state: StandbyUiState, locale: Locale) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = Formatters.formatTime(state.nowMillis, locale),
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = Formatters.formatDate(state.nowMillis, locale),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(modifier = Modifier.height(24.dp))
        NextEventLine(state.nextEvent, state.showCalendar, state.calendarPermissionState, locale)
        Spacer(modifier = Modifier.height(16.dp))
        ChargingBadge(state.chargingState)
    }
}

@Composable
private fun DigitalLayout(state: StandbyUiState, locale: Locale) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(verticalArrangement = Arrangement.Center) {
                Text(
                    text = Formatters.formatTime(state.nowMillis, locale),
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                Text(
                    text = Formatters.formatDate(state.nowMillis, locale),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            ChargingBadge(state.chargingState)
        }
        NextEventLine(state.nextEvent, state.showCalendar, state.calendarPermissionState, locale)
    }
}

@Composable
private fun CalendarFocusLayout(state: StandbyUiState, locale: Locale) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalArrangement = Arrangement.spacedBy(32.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = Formatters.formatTime(state.nowMillis, locale),
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Text(
                text = Formatters.formatDate(state.nowMillis, locale),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Spacer(modifier = Modifier.height(16.dp))
            ChargingBadge(state.chargingState)
        }
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = stringResource(id = R.string.standby_today_label),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Spacer(modifier = Modifier.height(12.dp))
            TodayEventList(
                events = state.todayEvents,
                showCalendar = state.showCalendar,
                permissionState = state.calendarPermissionState,
                isLoading = state.isLoadingEvents,
                loadFailed = state.calendarLoadFailed,
                locale = locale,
            )
        }
    }
}

@Composable
private fun NextEventLine(
    nextEvent: CalendarEventSummary?,
    showCalendar: Boolean,
    permissionState: CalendarPermissionState,
    locale: Locale,
) {
    if (!showCalendar) return
    when (permissionState) {
        CalendarPermissionState.Granted -> {
            if (nextEvent == null) {
                Text(
                    text = stringResource(id = R.string.standby_no_events),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            } else {
                Text(
                    text = buildString {
                        append(stringResource(id = R.string.standby_next_event_label))
                        append("  ")
                        append(Formatters.formatTime(nextEvent.startsAtMillis, locale))
                        append("  ")
                        append(nextEvent.title.ifBlank { "—" })
                    },
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                )
            }
        }
        CalendarPermissionState.Denied,
        CalendarPermissionState.PermanentlyDenied,
        CalendarPermissionState.NotRequested,
        -> Text(
            text = stringResource(id = R.string.standby_calendar_permission_required),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        CalendarPermissionState.Unknown -> Text(
            text = stringResource(id = R.string.standby_calendar_permission_loading),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun TodayEventList(
    events: List<CalendarEventSummary>,
    showCalendar: Boolean,
    permissionState: CalendarPermissionState,
    isLoading: Boolean,
    loadFailed: Boolean,
    locale: Locale,
) {
    if (!showCalendar || permissionState != CalendarPermissionState.Granted) {
        Text(
            text = stringResource(id = R.string.standby_calendar_permission_required),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        return
    }
    if (loadFailed) {
        Text(
            text = stringResource(id = R.string.standby_calendar_load_error),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.tertiary,
        )
        return
    }
    if (isLoading) {
        Text(
            text = stringResource(id = R.string.standby_calendar_permission_loading),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        return
    }
    if (events.isEmpty()) {
        Text(
            text = stringResource(id = R.string.standby_no_events),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        return
    }
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        events.take(5).forEach { event ->
            Row {
                Text(
                    text = if (event.allDay) {
                        stringResource(id = R.string.standby_all_day_event)
                    } else {
                        Formatters.formatTime(event.startsAtMillis, locale)
                    },
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = event.title.ifBlank { "—" },
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
        }
    }
}

@Composable
private fun ChargingBadge(state: ChargingState) {
    val label = when (state) {
        ChargingState.Charging -> stringResource(id = R.string.standby_charging)
        ChargingState.Full -> stringResource(id = R.string.standby_full)
        ChargingState.Discharging -> stringResource(id = R.string.standby_discharging)
        ChargingState.Unknown -> stringResource(id = R.string.standby_unknown)
    }
    Text(
        text = label,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
}
