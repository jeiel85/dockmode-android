package io.jeiel85.dockmode.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import io.jeiel85.dockmode.AppContainer
import io.jeiel85.dockmode.R
import io.jeiel85.dockmode.domain.model.ClockStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    appContainer: AppContainer,
    onBack: () -> Unit,
) {
    val viewModel: SettingsViewModel = viewModel(factory = SettingsViewModel.Factory(appContainer))
    val settings by viewModel.settings.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.settings_title)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                ),
                navigationIcon = {
                    TextButton(onClick = onBack) {
                        Text(text = stringResource(id = R.string.action_back))
                    }
                },
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            ClockStyleSection(
                selected = settings.clockStyle,
                onSelect = viewModel::setClockStyle,
            )
            HorizontalDivider(color = MaterialTheme.colorScheme.surface)
            ToggleRow(
                label = stringResource(id = R.string.settings_show_calendar),
                checked = settings.showCalendar,
                onCheckedChange = viewModel::setShowCalendar,
            )
            ToggleRow(
                label = stringResource(id = R.string.settings_night_mode),
                checked = settings.nightMode,
                onCheckedChange = viewModel::setNightMode,
            )
            ToggleRow(
                label = stringResource(id = R.string.settings_burn_in_guard),
                checked = settings.burnInGuard,
                onCheckedChange = viewModel::setBurnInGuard,
            )
            ToggleRow(
                label = stringResource(id = R.string.settings_keep_screen_on),
                checked = settings.keepScreenOn,
                onCheckedChange = viewModel::setKeepScreenOn,
            )
            HorizontalDivider(color = MaterialTheme.colorScheme.surface)
            PrivacySection()
        }
    }
}

@Composable
private fun ClockStyleSection(selected: ClockStyle, onSelect: (ClockStyle) -> Unit) {
    Column {
        Text(
            text = stringResource(id = R.string.settings_clock_style),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(modifier = Modifier.height(8.dp))
        ClockStyleOption(
            label = stringResource(id = R.string.settings_clock_style_minimal),
            selected = selected == ClockStyle.Minimal,
            onSelect = { onSelect(ClockStyle.Minimal) },
        )
        ClockStyleOption(
            label = stringResource(id = R.string.settings_clock_style_digital),
            selected = selected == ClockStyle.Digital,
            onSelect = { onSelect(ClockStyle.Digital) },
        )
        ClockStyleOption(
            label = stringResource(id = R.string.settings_clock_style_calendar),
            selected = selected == ClockStyle.CalendarFocus,
            onSelect = { onSelect(ClockStyle.CalendarFocus) },
        )
    }
}

@Composable
private fun ClockStyleOption(label: String, selected: Boolean, onSelect: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .selectable(selected = selected, onClick = onSelect)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(selected = selected, onClick = onSelect)
        Text(
            text = label,
            modifier = Modifier.padding(start = 12.dp),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}

@Composable
private fun ToggleRow(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}

@Composable
private fun PrivacySection() {
    Column {
        Text(
            text = stringResource(id = R.string.settings_privacy_section),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = R.string.settings_privacy_body),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}
