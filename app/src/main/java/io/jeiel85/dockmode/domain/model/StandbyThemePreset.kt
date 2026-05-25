package io.jeiel85.dockmode.domain.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class StandbyThemePreset(
    val id: String,
    val displayNameKo: String,
    val displayNameEn: String,
    val backgroundColor: Color,
    val textColor: Color,
    val secondaryTextColor: Color,
    val accentColor: Color,
    val cardColor: Color,
    val isLowBrightness: Boolean,
)
