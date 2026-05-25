package io.jeiel85.dockmode.standby.widgets

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.jeiel85.dockmode.R
import io.jeiel85.dockmode.domain.model.CalendarEventSummary
import io.jeiel85.dockmode.domain.model.CalendarPermissionState
import io.jeiel85.dockmode.domain.model.ChargingState
import io.jeiel85.dockmode.domain.model.StandbyThemePreset
import io.jeiel85.dockmode.util.Formatters
import java.util.Locale

@Composable
fun TimeWidget(
    nowMillis: Long,
    theme: StandbyThemePreset,
    locale: Locale,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 96.sp,
    showSeconds: Boolean = false,
) {
    val formatter = if (showSeconds) {
        java.text.SimpleDateFormat("HH:mm:ss", locale)
    } else {
        java.text.SimpleDateFormat("HH:mm", locale)
    }
    val timeText = formatter.format(java.util.Date(nowMillis))

    Text(
        text = timeText,
        style = MaterialTheme.typography.displayLarge.copy(
            fontSize = fontSize,
            fontWeight = FontWeight.Bold,
            color = theme.textColor,
            letterSpacing = (-2).sp,
        ),
        textAlign = TextAlign.Center,
        modifier = modifier,
    )
}

@Composable
fun DateWidget(
    nowMillis: Long,
    theme: StandbyThemePreset,
    locale: Locale,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 18.sp,
) {
    Text(
        text = Formatters.formatDate(nowMillis, locale),
        style = MaterialTheme.typography.titleMedium.copy(
            fontSize = fontSize,
            fontWeight = FontWeight.SemiBold,
            color = theme.secondaryTextColor,
        ),
        textAlign = TextAlign.Center,
        modifier = modifier,
    )
}

@Composable
fun BatteryWidget(
    chargingState: ChargingState,
    theme: StandbyThemePreset,
    modifier: Modifier = Modifier,
) {
    val (labelRes, iconColor) = when (chargingState) {
        ChargingState.Charging -> R.string.standby_charging to Color(0xFF60A5FA)
        ChargingState.Full -> R.string.standby_full to Color(0xFF10B981)
        ChargingState.Discharging -> R.string.standby_discharging to theme.secondaryTextColor
        ChargingState.Unknown -> R.string.standby_unknown to theme.secondaryTextColor
    }

    val infiniteTransition = rememberInfiniteTransition(label = "battery_pulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "pulse",
    )

    val activeAlpha = if (chargingState == ChargingState.Charging) pulseAlpha else 1.0f

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .alpha(activeAlpha)
            .clip(RoundedCornerShape(12.dp))
            .background(theme.cardColor)
            .border(1.dp, theme.accentColor.copy(alpha = 0.15f), RoundedCornerShape(12.dp))
            .padding(horizontal = 14.dp, vertical = 8.dp),
    ) {
        Icon(
            imageVector = Icons.Filled.Bolt,
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier.size(16.dp),
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = stringResource(id = labelRes),
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Bold,
                color = theme.textColor,
            ),
        )
    }
}

@Composable
fun BatteryCircleWidget(
    chargingState: ChargingState,
    theme: StandbyThemePreset,
    modifier: Modifier = Modifier,
) {
    val isCharging = chargingState == ChargingState.Charging
    val isFull = chargingState == ChargingState.Full

    val infiniteTransition = rememberInfiniteTransition(label = "battery_pulse")
    val strokeWidthState by infiniteTransition.animateFloat(
        initialValue = 4f,
        targetValue = 8f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "stroke",
    )

    val color = when {
        isCharging -> Color(0xFF60A5FA)
        isFull -> Color(0xFF10B981)
        else -> theme.accentColor
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.size(120.dp),
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                color = theme.secondaryTextColor.copy(alpha = 0.1f),
                radius = size.minDimension / 2 - 10,
                style = Stroke(width = 6f),
            )

            drawArc(
                color = color,
                startAngle = -90f,
                sweepAngle = if (isFull) {
                    360f
                } else if (isCharging) {
                    280f
                } else {
                    180f
                },
                useCenter = false,
                style = Stroke(width = if (isCharging) strokeWidthState else 6f),
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Icon(
                imageVector = Icons.Filled.Bolt,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(32.dp),
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = if (isFull) {
                    "100%"
                } else if (isCharging) {
                    "Charging"
                } else {
                    "Battery"
                },
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = theme.textColor,
                ),
            )
        }
    }
}

@Composable
fun CalendarPreviewWidget(
    todayEvents: List<CalendarEventSummary>,
    @Suppress("UNUSED_PARAMETER") nextEvent: CalendarEventSummary?,
    showCalendar: Boolean,
    permissionState: CalendarPermissionState,
    locale: Locale,
    theme: StandbyThemePreset,
    modifier: Modifier = Modifier,
    maxEvents: Int = 3,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = theme.cardColor),
        border = androidx.compose.foundation.BorderStroke(1.dp, theme.textColor.copy(alpha = 0.05f)),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = "Today's Schedule",
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = theme.accentColor,
                ),
            )

            if (!showCalendar) {
                Text(
                    text = "Calendar display is off in Settings",
                    style = MaterialTheme.typography.bodyMedium,
                    color = theme.secondaryTextColor.copy(alpha = 0.6f),
                )
                return@Column
            }

            when (permissionState) {
                CalendarPermissionState.Granted -> {
                    if (todayEvents.isEmpty()) {
                        Text(
                            text = stringResource(id = R.string.standby_no_events),
                            style = MaterialTheme.typography.bodyMedium,
                            color = theme.secondaryTextColor.copy(alpha = 0.7f),
                        )
                    } else {
                        todayEvents.take(maxEvents).forEach { event ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(theme.textColor.copy(alpha = 0.03f))
                                    .padding(horizontal = 10.dp, vertical = 6.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    text = event.title.ifBlank { "—" },
                                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                    color = theme.textColor,
                                    maxLines = 1,
                                    modifier = Modifier.weight(1.2f),
                                )
                                Text(
                                    text = if (event.allDay) {
                                        stringResource(id = R.string.standby_all_day_event)
                                    } else {
                                        Formatters.formatTime(event.startsAtMillis, locale)
                                    },
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontFamily = FontFamily.Monospace,
                                        color = theme.secondaryTextColor,
                                    ),
                                    modifier = Modifier.weight(0.8f),
                                    textAlign = TextAlign.End,
                                )
                            }
                        }
                    }
                }
                CalendarPermissionState.Unknown -> {
                    Text(
                        text = stringResource(id = R.string.standby_calendar_permission_loading),
                        style = MaterialTheme.typography.bodyMedium,
                        color = theme.secondaryTextColor.copy(alpha = 0.6f),
                    )
                }
                else -> {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = null,
                                tint = theme.secondaryTextColor,
                                modifier = Modifier.size(14.dp),
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Calendar off",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                color = theme.textColor,
                            )
                        }
                        Text(
                            text = "Enable calendar display in Settings to sync events.",
                            style = MaterialTheme.typography.labelSmall,
                            color = theme.secondaryTextColor.copy(alpha = 0.7f),
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PhotoPlaceholderWidget(
    theme: StandbyThemePreset,
    modifier: Modifier = Modifier,
) {
    val gradientBrush = Brush.linearGradient(
        colors = listOf(
            theme.accentColor.copy(alpha = 0.3f),
            theme.backgroundColor.copy(alpha = 0.8f),
            theme.accentColor.copy(alpha = 0.1f),
        ),
        start = Offset(0f, 0f),
        end = Offset(1000f, 1000f),
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(24.dp))
            .background(gradientBrush)
            .border(1.5.dp, theme.textColor.copy(alpha = 0.1f), RoundedCornerShape(24.dp)),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(24.dp),
        ) {
            Text(
                text = "Photo Frame Placeholder",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = theme.textColor,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Photo frame will be available later without requiring extra permissions.",
                style = MaterialTheme.typography.bodySmall,
                color = theme.secondaryTextColor,
                textAlign = TextAlign.Center,
                lineHeight = 16.sp,
            )
        }
    }
}
