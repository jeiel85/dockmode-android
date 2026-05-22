package io.jeiel85.dockmode.standby

import android.app.Activity
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.EventNote
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.jeiel85.dockmode.R
import io.jeiel85.dockmode.domain.model.CalendarEventSummary
import io.jeiel85.dockmode.domain.model.CalendarPermissionState
import io.jeiel85.dockmode.domain.model.ChargingState
import io.jeiel85.dockmode.domain.model.ClockStyle
import io.jeiel85.dockmode.util.BurnInOffset
import io.jeiel85.dockmode.util.Formatters
import java.util.Locale

private val NightAmberPrimary = Color(0xFFFBBF24)
private val NightAmberText = Color(0xFFFBBF24)
private val ChargingBlue = Color(0xFF60A5FA)
private val FullGreen = Color(0xFF10B981)
private val PanelObsidian = Color(0xFF0C0C14)

@Composable
fun StandbyScreen(state: StandbyUiState) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val locale: Locale = configuration.locales.get(0) ?: Locale.getDefault()

    val (dx, dy) = if (state.burnInGuard) {
        BurnInOffset.calculate(state.nowMillis)
    } else {
        0 to 0
    }

    val isMinimal = state.clockStyle == ClockStyle.Minimal
    val regularPrimary = MaterialTheme.colorScheme.primary
    val regularText = MaterialTheme.colorScheme.onBackground

    val primaryColor by animateColorAsState(
        targetValue = if (isMinimal) NightAmberPrimary else regularPrimary,
        label = "standby_primary",
    )
    val textColor by animateColorAsState(
        targetValue = if (isMinimal) NightAmberText else regularText,
        label = "standby_text",
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .clickable { (context as? Activity)?.finish() }
            .testTag("standby_screen_root"),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset { IntOffset(dx, dy) }
                .padding(24.dp),
        ) {
            when (state.clockStyle) {
                ClockStyle.Minimal -> MinimalLayout(
                    state = state,
                    locale = locale,
                    primaryColor = primaryColor,
                    textColor = textColor,
                )
                ClockStyle.Digital -> DigitalLayout(
                    state = state,
                    locale = locale,
                    primaryColor = regularPrimary,
                    textColor = regularText,
                )
                ClockStyle.CalendarFocus -> CalendarFocusLayout(
                    state = state,
                    locale = locale,
                    primaryColor = regularPrimary,
                    textColor = regularText,
                )
            }
        }

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
private fun MinimalLayout(
    state: StandbyUiState,
    locale: Locale,
    primaryColor: Color,
    textColor: Color,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = Formatters.formatTime(state.nowMillis, locale),
            style = MaterialTheme.typography.displayLarge.copy(
                fontSize = 110.sp,
                fontWeight = FontWeight.Bold,
                color = primaryColor,
                letterSpacing = (-3).sp,
            ),
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = Formatters.formatDate(state.nowMillis, locale),
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold,
            ),
            color = textColor.copy(alpha = 0.8f),
        )

        Spacer(modifier = Modifier.height(28.dp))

        NextEventLine(
            nextEvent = state.nextEvent,
            showCalendar = state.showCalendar,
            permissionState = state.calendarPermissionState,
            locale = locale,
            tint = textColor.copy(alpha = 0.6f),
        )

        Spacer(modifier = Modifier.height(18.dp))

        ChargingBadge(state.chargingState, textColor.copy(alpha = 0.5f))
    }
}

@Composable
private fun DigitalLayout(
    state: StandbyUiState,
    locale: Locale,
    primaryColor: Color,
    textColor: Color,
) {
    Row(
        modifier = Modifier.fillMaxSize(),
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
            Text(
                text = Formatters.formatTime(state.nowMillis, locale),
                style = MaterialTheme.typography.displayLarge.copy(
                    fontSize = 82.sp,
                    fontWeight = FontWeight.Bold,
                    color = primaryColor,
                ),
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = Formatters.formatDate(state.nowMillis, locale),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                ),
                color = textColor,
            )
            Spacer(modifier = Modifier.height(16.dp))
            ChargingBadge(state.chargingState, textColor.copy(alpha = 0.5f))
        }

        Column(
            modifier = Modifier
                .weight(0.9f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = PanelObsidian),
                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.05f)),
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.EventNote,
                            contentDescription = null,
                            tint = primaryColor,
                            modifier = Modifier.size(18.dp),
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = stringResource(id = R.string.standby_next_event_label),
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Bold,
                            ),
                            color = primaryColor,
                        )
                    }

                    Box(modifier = Modifier.fillMaxWidth()) {
                        NextEventPanel(
                            nextEvent = state.nextEvent,
                            showCalendar = state.showCalendar,
                            permissionState = state.calendarPermissionState,
                            locale = locale,
                            textColor = textColor,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CalendarFocusLayout(
    state: StandbyUiState,
    locale: Locale,
    primaryColor: Color,
    textColor: Color,
) {
    Row(
        modifier = Modifier.fillMaxSize(),
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
            Text(
                text = Formatters.formatTime(state.nowMillis, locale),
                style = MaterialTheme.typography.displayMedium.copy(
                    fontSize = 62.sp,
                    fontWeight = FontWeight.Bold,
                    color = primaryColor,
                ),
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = Formatters.formatDate(state.nowMillis, locale),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                ),
                color = textColor,
            )
            Spacer(modifier = Modifier.height(14.dp))
            ChargingBadge(state.chargingState, textColor.copy(alpha = 0.5f))
        }

        Column(
            modifier = Modifier
                .weight(1.2f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = stringResource(id = R.string.standby_today_label),
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp,
                ),
                color = primaryColor,
            )

            Spacer(modifier = Modifier.height(12.dp))

            TodayEventScrollList(
                events = state.todayEvents,
                showCalendar = state.showCalendar,
                permissionState = state.calendarPermissionState,
                isLoading = state.isLoadingEvents,
                hasFailed = state.calendarLoadFailed,
                locale = locale,
                textColor = textColor,
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
    tint: Color,
) {
    if (!showCalendar) return

    val text = when (permissionState) {
        CalendarPermissionState.Granted -> {
            if (nextEvent == null) {
                stringResource(id = R.string.standby_no_events)
            } else {
                "${Formatters.formatTime(nextEvent.startsAtMillis, locale)} ${nextEvent.title}"
            }
        }
        CalendarPermissionState.Unknown -> stringResource(
            id = R.string.standby_calendar_permission_loading,
        )
        else -> stringResource(id = R.string.standby_calendar_permission_required)
    }

    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        color = tint,
        textAlign = TextAlign.Center,
    )
}

@Composable
private fun NextEventPanel(
    nextEvent: CalendarEventSummary?,
    showCalendar: Boolean,
    permissionState: CalendarPermissionState,
    locale: Locale,
    textColor: Color,
) {
    if (!showCalendar) {
        Text(
            text = stringResource(id = R.string.standby_calendar_display_off),
            style = MaterialTheme.typography.bodyMedium,
            color = textColor.copy(alpha = 0.5f),
        )
        return
    }

    when (permissionState) {
        CalendarPermissionState.Granted -> {
            if (nextEvent == null) {
                Text(
                    text = stringResource(id = R.string.standby_no_events),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Medium,
                    ),
                    color = textColor.copy(alpha = 0.6f),
                )
            } else {
                Column {
                    Text(
                        text = nextEvent.title.ifBlank { "—" },
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                        ),
                        color = textColor,
                        maxLines = 1,
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = if (nextEvent.allDay) {
                            stringResource(id = R.string.standby_all_day_event)
                        } else {
                            Formatters.formatEventTimeRange(
                                nextEvent.startsAtMillis,
                                nextEvent.endsAtMillis,
                                false,
                                locale,
                            )
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        color = textColor.copy(alpha = 0.6f),
                    )
                }
            }
        }
        CalendarPermissionState.Unknown -> {
            Text(
                text = stringResource(id = R.string.standby_calendar_permission_loading),
                style = MaterialTheme.typography.bodyMedium,
                color = textColor.copy(alpha = 0.5f),
            )
        }
        else -> {
            Text(
                text = stringResource(id = R.string.standby_calendar_permission_required),
                style = MaterialTheme.typography.bodyMedium,
                color = textColor.copy(alpha = 0.5f),
            )
        }
    }
}

@Composable
private fun TodayEventScrollList(
    events: List<CalendarEventSummary>,
    showCalendar: Boolean,
    permissionState: CalendarPermissionState,
    isLoading: Boolean,
    hasFailed: Boolean,
    locale: Locale,
    textColor: Color,
) {
    if (!showCalendar || permissionState != CalendarPermissionState.Granted) {
        Text(
            text = stringResource(id = R.string.standby_calendar_permission_required),
            style = MaterialTheme.typography.bodyMedium,
            color = textColor.copy(alpha = 0.5f),
        )
        return
    }

    if (hasFailed) {
        Text(
            text = stringResource(id = R.string.standby_calendar_load_error),
            style = MaterialTheme.typography.bodyMedium,
            color = textColor.copy(alpha = 0.5f),
        )
        return
    }

    if (isLoading) {
        Text(
            text = stringResource(id = R.string.standby_calendar_permission_loading),
            style = MaterialTheme.typography.bodyMedium,
            color = textColor.copy(alpha = 0.5f),
        )
        return
    }

    if (events.isEmpty()) {
        Text(
            text = stringResource(id = R.string.standby_no_events),
            style = MaterialTheme.typography.bodyMedium,
            color = textColor.copy(alpha = 0.5f),
        )
        return
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        events.take(3).forEach { event ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White.copy(alpha = 0.03f))
                    .border(
                        1.dp,
                        Color.White.copy(alpha = 0.03f),
                        RoundedCornerShape(10.dp),
                    )
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = event.title.ifBlank { "—" },
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                    ),
                    color = textColor,
                    modifier = Modifier.weight(1.2f),
                    maxLines = 1,
                )
                Text(
                    text = if (event.allDay) {
                        stringResource(id = R.string.standby_all_day_event)
                    } else {
                        Formatters.formatTime(event.startsAtMillis, locale)
                    },
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontFamily = FontFamily.Monospace,
                        color = textColor.copy(alpha = 0.6f),
                    ),
                    modifier = Modifier.weight(0.8f),
                    textAlign = TextAlign.End,
                )
            }
        }
    }
}

@Composable
private fun ChargingBadge(state: ChargingState, tint: Color) {
    val (labelRes, iconTint) = when (state) {
        ChargingState.Charging -> R.string.standby_charging to ChargingBlue
        ChargingState.Full -> R.string.standby_full to FullGreen
        ChargingState.Discharging -> R.string.standby_discharging to tint
        ChargingState.Unknown -> R.string.standby_unknown to tint
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Icon(
            imageVector = Icons.Filled.Bolt,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(16.dp),
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = stringResource(id = labelRes),
            style = MaterialTheme.typography.labelMedium,
            color = tint,
        )
    }
}
