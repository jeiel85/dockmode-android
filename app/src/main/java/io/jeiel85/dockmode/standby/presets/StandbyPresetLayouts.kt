package io.jeiel85.dockmode.standby.presets

import androidx.compose.animation.Crossfade
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import io.jeiel85.dockmode.domain.model.StandbyThemePreset
import io.jeiel85.dockmode.standby.StandbyUiState
import io.jeiel85.dockmode.standby.widgets.BatteryCircleWidget
import io.jeiel85.dockmode.standby.widgets.BatteryWidget
import io.jeiel85.dockmode.standby.widgets.CalendarPreviewWidget
import io.jeiel85.dockmode.standby.widgets.DateWidget
import io.jeiel85.dockmode.standby.widgets.PhotoFrameEmptyWidget
import io.jeiel85.dockmode.standby.widgets.TimeWidget
import kotlinx.coroutines.delay
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
    val configuration = LocalConfiguration.current
    val isTablet = configuration.screenWidthDp >= 600

    val spacing = if (isTablet) 48.dp else 24.dp
    val leftWeight = if (isTablet) 1.0f else 1.1f
    val rightWeight = if (isTablet) 1.0f else 0.9f
    val horizontalPadding = if (isTablet) 32.dp else 0.dp

    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = horizontalPadding),
        horizontalArrangement = Arrangement.spacedBy(spacing),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier
                .weight(leftWeight)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
        ) {
            TimeWidget(
                nowMillis = state.nowMillis,
                theme = theme,
                locale = locale,
                fontSize = if (isTablet) 96.sp else 82.sp,
            )
            Spacer(modifier = Modifier.height(8.dp))
            DateWidget(
                nowMillis = state.nowMillis,
                theme = theme,
                locale = locale,
                fontSize = if (isTablet) 24.sp else 20.sp,
            )
            Spacer(modifier = Modifier.height(16.dp))
            BatteryWidget(
                chargingState = state.chargingState,
                theme = theme,
            )
        }

        Column(
            modifier = Modifier
                .weight(rightWeight)
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
    val configuration = LocalConfiguration.current
    val isTablet = configuration.screenWidthDp >= 600

    val spacing = if (isTablet) 54.dp else 36.dp
    val leftWeight = if (isTablet) 1.0f else 0.8f
    val rightWeight = if (isTablet) 1.0f else 1.2f
    val horizontalPadding = if (isTablet) 36.dp else 0.dp

    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = horizontalPadding),
        horizontalArrangement = Arrangement.spacedBy(spacing),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier
                .weight(leftWeight)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
        ) {
            TimeWidget(
                nowMillis = state.nowMillis,
                theme = theme,
                locale = locale,
                fontSize = if (isTablet) 72.sp else 62.sp,
            )
            Spacer(modifier = Modifier.height(4.dp))
            DateWidget(
                nowMillis = state.nowMillis,
                theme = theme,
                locale = locale,
                fontSize = if (isTablet) 20.sp else 16.sp,
            )
            Spacer(modifier = Modifier.height(14.dp))
            BatteryWidget(
                chargingState = state.chargingState,
                theme = theme,
            )
        }

        Column(
            modifier = Modifier
                .weight(rightWeight)
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
                maxEvents = if (isTablet) 5 else 4,
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
    val configuration = LocalConfiguration.current
    val isTablet = configuration.screenWidthDp >= 600
    val horizontalPadding = if (isTablet) 64.dp else 0.dp

    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = horizontalPadding),
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
                fontSize = if (isTablet) 90.sp else 76.sp,
            )
            Spacer(modifier = Modifier.height(4.dp))
            DateWidget(
                nowMillis = state.nowMillis,
                theme = theme,
                locale = locale,
                fontSize = if (isTablet) 20.sp else 16.sp,
            )
        }

        BatteryCircleWidget(
            chargingState = state.chargingState,
            theme = theme,
            modifier = if (isTablet) Modifier.padding(16.dp) else Modifier,
        )
    }
}

@Composable
fun PhotoFramePreset(
    state: StandbyUiState,
    theme: StandbyThemePreset,
    locale: Locale,
    modifier: Modifier = Modifier,
) {
    var currentImageIndex by remember { mutableStateOf(0) }

    val hasImages = state.localGalleryImages.isNotEmpty()

    LaunchedEffect(state.localGalleryImages) {
        if (state.localGalleryImages.isNotEmpty()) {
            currentImageIndex = 0
        }
    }

    LaunchedEffect(hasImages) {
        if (hasImages) {
            while (true) {
                delay(10000L) // 10초 주기로 순환
                currentImageIndex = (currentImageIndex + 1) % state.localGalleryImages.size
            }
        }
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        if (hasImages) {
            val currentImageUrl = state.localGalleryImages[currentImageIndex]
            Crossfade(
                targetState = currentImageUrl,
                animationSpec = androidx.compose.animation.core.tween(1000),
                label = "photo_fade",
            ) { url ->
                AsyncImage(
                    model = url,
                    contentDescription = "Photo slide",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        } else {
            PhotoFrameEmptyWidget(
                hasPermission = state.galleryPermissionState,
                theme = theme,
            )
        }

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
                val isKo = locale.language == "ko"
                val subtitle = if (hasImages) {
                    if (isKo) "포토 프레임 (슬라이드 쇼)" else "Photo Frame (Slide Show)"
                } else if (state.galleryPermissionState) {
                    if (isKo) "포토 프레임" else "Photo Frame"
                } else {
                    if (isKo) "포토 프레임 (권한 필요)" else "Photo Frame (Permission Required)"
                }
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = theme.textColor.copy(alpha = 0.8f),
                )
            }
        }
    }
}
