package io.jeiel85.dockmode.standby.theme

import androidx.compose.ui.graphics.Color
import io.jeiel85.dockmode.domain.model.StandbyThemePreset

object StandbyThemeRegistry {
    val MidnightGlass = StandbyThemePreset(
        id = "midnight_glass",
        displayNameKo = "미드나잇 글래스",
        displayNameEn = "Midnight Glass",
        backgroundColor = Color(0xFF070714),
        textColor = Color(0xFFE2E8F0),
        secondaryTextColor = Color(0xFF94A3B8),
        accentColor = Color(0xFF3B82F6),
        cardColor = Color(0x1F1E293B),
        isLowBrightness = false,
    )

    val WarmBedside = StandbyThemePreset(
        id = "warm_bedside",
        displayNameKo = "따뜻한 침실",
        displayNameEn = "Warm Bedside",
        backgroundColor = Color(0xFF0F0800),
        textColor = Color(0xFFD97706),
        secondaryTextColor = Color(0xFFB45309),
        accentColor = Color(0xFFF59E0B),
        cardColor = Color(0x1578350F),
        isLowBrightness = true,
    )

    val OledPureBlack = StandbyThemePreset(
        id = "oled_pure_black",
        displayNameKo = "OLED 퓨어 블랙",
        displayNameEn = "OLED Pure Black",
        backgroundColor = Color.Black,
        textColor = Color(0xFF7F1D1D),
        secondaryTextColor = Color(0xFF450A0A),
        accentColor = Color(0xFFDC2626),
        cardColor = Color.Black,
        isLowBrightness = true,
    )

    val AuroraGradient = StandbyThemePreset(
        id = "aurora_gradient",
        displayNameKo = "오로라 그라데이션",
        displayNameEn = "Aurora Gradient",
        backgroundColor = Color(0xFF03001E), // Radial/Linear gradient will be drawn dynamically based on this
        textColor = Color.White,
        secondaryTextColor = Color(0xFFCBD5E1),
        accentColor = Color(0xFF8A2387),
        cardColor = Color(0x221E1B4B),
        isLowBrightness = false,
    )

    val PaperCalendar = StandbyThemePreset(
        id = "paper_calendar",
        displayNameKo = "종이 달력",
        displayNameEn = "Paper Calendar",
        backgroundColor = Color(0xFFF5F5F4),
        textColor = Color(0xFF1C1917),
        secondaryTextColor = Color(0xFF78716C),
        accentColor = Color(0xFFD97706),
        cardColor = Color(0xFFE7E5E4),
        isLowBrightness = false,
    )

    val MaterialYou = StandbyThemePreset(
        id = "material_you",
        displayNameKo = "머티리얼 유",
        displayNameEn = "Material You",
        backgroundColor = Color(0xFF1C1B1F),
        textColor = Color(0xFFE6E1E5),
        secondaryTextColor = Color(0xFFCAC4D0),
        accentColor = Color(0xFFD0BCFF),
        cardColor = Color(0xFF49454F),
        isLowBrightness = false,
    )

    val themes = listOf(
        MidnightGlass,
        WarmBedside,
        OledPureBlack,
        AuroraGradient,
        PaperCalendar,
        MaterialYou,
    )

    fun findById(id: String?): StandbyThemePreset =
        themes.firstOrNull { it.id == id } ?: MidnightGlass
}
