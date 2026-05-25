package io.jeiel85.dockmode.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import io.jeiel85.dockmode.AppContainer
import io.jeiel85.dockmode.R
import io.jeiel85.dockmode.domain.model.ClockStyle
import io.jeiel85.dockmode.standby.theme.StandbyThemeRegistry
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    appContainer: AppContainer,
    onBack: () -> Unit,
) {
    val viewModel: SettingsViewModel = viewModel(
        factory = SettingsViewModel.Factory(appContainer),
    )
    val settings by viewModel.settings.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.settings_title),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                        ),
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                ),
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier
                            .size(54.dp)
                            .testTag("settings_back_button"),
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.action_back),
                            tint = MaterialTheme.colorScheme.onBackground,
                        )
                    }
                },
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            ClockStyleSelectorSection(
                selectedStyle = settings.clockStyle,
                onSelectStyle = viewModel::setClockStyle,
            )

            HorizontalDivider(
                color = MaterialTheme.colorScheme.surfaceVariant,
                thickness = 1.dp,
            )

            ThemeSelectorSection(
                selectedThemeId = settings.selectedThemeId,
                onSelectTheme = viewModel::setThemeId,
            )

            HorizontalDivider(
                color = MaterialTheme.colorScheme.surfaceVariant,
                thickness = 1.dp,
            )

            PreferencesSection(
                showCalendar = settings.showCalendar,
                onShowCalendarChanged = viewModel::setShowCalendar,
                nightMode = settings.nightMode,
                onNightModeChanged = viewModel::setNightMode,
                burnInGuard = settings.burnInGuard,
                onBurnInGuardChanged = viewModel::setBurnInGuard,
                keepScreenOn = settings.keepScreenOn,
                onKeepScreenOnChanged = viewModel::setKeepScreenOn,
            )

            HorizontalDivider(
                color = MaterialTheme.colorScheme.surfaceVariant,
                thickness = 1.dp,
            )

            PrivacySection()
        }
    }
}

@Composable
private fun ClockStyleSelectorSection(
    selectedStyle: ClockStyle,
    onSelectStyle: (ClockStyle) -> Unit,
) {
    val configuration = LocalConfiguration.current
    val locale: Locale = configuration.locales.get(0) ?: Locale.getDefault()
    val hmFormatter = remember(locale) { SimpleDateFormat("HH:mm", locale) }
    val hmsFormatter = remember(locale) { SimpleDateFormat("HH:mm:ss", locale) }
    val nowMillis by produceState(initialValue = System.currentTimeMillis(), locale) {
        while (true) {
            value = System.currentTimeMillis()
            delay(1_000L)
        }
    }
    val now = Date(nowMillis)
    val isKo = locale.language == "ko"

    val styles = ClockStyle.values()

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Text(
            text = stringResource(id = R.string.settings_clock_style),
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onBackground,
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(styles) { style ->
                val timeHm = hmFormatter.format(now)
                val timeHms = hmsFormatter.format(now)
                val title = style.getTitle(isKo)
                val preview = style.getPreview(timeHm, timeHms)

                ClockStyleCard(
                    title = title,
                    previewText = preview,
                    isSelected = selectedStyle == style,
                    modifier = Modifier
                        .width(160.dp)
                        .testTag("clock_style_${style.name.lowercase()}_card"),
                    onClick = { onSelectStyle(style) },
                )
            }
        }
    }
}

@Composable
private fun ThemeSelectorSection(
    selectedThemeId: String,
    onSelectTheme: (String) -> Unit,
) {
    val configuration = LocalConfiguration.current
    val locale: Locale = configuration.locales.get(0) ?: Locale.getDefault()
    val isKo = locale.language == "ko"

    val themes = StandbyThemeRegistry.themes

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Text(
            text = if (isKo) "대기모드 테마" else "Standby Theme",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onBackground,
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(themes) { theme ->
                val title = if (isKo) theme.displayNameKo else theme.displayNameEn
                val borderColor = if (selectedThemeId == theme.id) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                }
                val containerColor = if (selectedThemeId == theme.id) {
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
                } else {
                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                }

                Card(
                    modifier = Modifier
                        .width(140.dp)
                        .height(90.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .clickable { onSelectTheme(theme.id) }
                        .testTag("theme_card_${theme.id}"),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = containerColor),
                    border = BorderStroke(1.5.dp, borderColor),
                ) {
                    Column(
                        modifier = Modifier
                            .padding(12.dp)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        // Colored Circle representing theme color
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(theme.backgroundColor)
                                .border(1.dp, theme.textColor.copy(alpha = 0.2f), CircleShape),
                        )

                        Text(
                            text = title,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                            ),
                            color = if (selectedThemeId == theme.id) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.onBackground
                            },
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ClockStyleCard(
    title: String,
    previewText: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val borderColor = if (isSelected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
    }
    val containerColor = if (isSelected) {
        MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
    } else {
        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
    }
    val textColor = if (isSelected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.onBackground
    }

    Card(
        modifier = modifier
            .height(110.dp)
            .clip(RoundedCornerShape(14.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        border = BorderStroke(1.5.dp, borderColor),
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.background.copy(alpha = 0.6f)),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = previewText,
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        color = if (isSelected) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        },
                    ),
                )
            }

            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                ),
                color = textColor,
            )
        }
    }
}

@Composable
private fun PreferencesSection(
    showCalendar: Boolean,
    onShowCalendarChanged: (Boolean) -> Unit,
    nightMode: Boolean,
    onNightModeChanged: (Boolean) -> Unit,
    burnInGuard: Boolean,
    onBurnInGuardChanged: (Boolean) -> Unit,
    keepScreenOn: Boolean,
    onKeepScreenOnChanged: (Boolean) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        ControlRow(
            title = stringResource(id = R.string.settings_show_calendar),
            desc = stringResource(id = R.string.settings_show_calendar_desc),
            checked = showCalendar,
            modifier = Modifier.testTag("switch_show_calendar"),
            onCheckedChange = onShowCalendarChanged,
        )

        ControlRow(
            title = stringResource(id = R.string.settings_night_mode),
            desc = stringResource(id = R.string.settings_night_mode_desc),
            checked = nightMode,
            modifier = Modifier.testTag("switch_night_mode"),
            onCheckedChange = onNightModeChanged,
        )

        ControlRow(
            title = stringResource(id = R.string.settings_burn_in_guard),
            desc = stringResource(id = R.string.settings_burn_in_guard_desc),
            checked = burnInGuard,
            modifier = Modifier.testTag("switch_burn_in_guard"),
            onCheckedChange = onBurnInGuardChanged,
        )

        ControlRow(
            title = stringResource(id = R.string.settings_keep_screen_on),
            desc = stringResource(id = R.string.settings_keep_screen_on_desc),
            checked = keepScreenOn,
            modifier = Modifier.testTag("switch_keep_screen_on"),
            onCheckedChange = onKeepScreenOnChanged,
        )
    }
}

@Composable
private fun ControlRow(
    title: String,
    desc: String,
    checked: Boolean,
    modifier: Modifier = Modifier,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 16.dp),
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                ),
                color = MaterialTheme.colorScheme.onBackground,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = desc,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 16.sp,
            )
        }

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = modifier,
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
                checkedTrackColor = MaterialTheme.colorScheme.primary,
                uncheckedThumbColor = MaterialTheme.colorScheme.onSurfaceVariant,
                uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
        )
    }
}

@Composable
private fun PrivacySection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        ),
    ) {
        Row(
            modifier = Modifier.padding(18.dp),
            verticalAlignment = Alignment.Top,
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(24.dp)
                    .padding(top = 2.dp),
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(id = R.string.settings_privacy_section),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                    ),
                    color = MaterialTheme.colorScheme.onBackground,
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = stringResource(id = R.string.settings_privacy_body),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 20.sp,
                )
            }
        }
    }
}
