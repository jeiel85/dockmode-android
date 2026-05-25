# Implementation Guide

## 1. 전제

DockMode의 초기 구현은 이미 완료된 상태입니다. 이 가이드는 기존 프로젝트에 **프리셋 확장 계층**만 추가하는 것을 목표로 합니다.

에이전트는 실제 파일 구조를 먼저 확인해야 하며, 아래 경로는 권장 예시입니다.

```text
app/src/main/java/io/jeiel85/dockmode/
```

## 2. 권장 패키지 구조

기존 구조가 없다면 아래처럼 최소 추가합니다.

```text
ui/standby/
  StandbyScreen.kt
  StandbyRenderer.kt
  StandbyUiState.kt
  StandbyPreset.kt
  StandbyPresetRegistry.kt
  BurnInProtection.kt

ui/standby/presets/
  MinimalClockPreset.kt
  WarmBedsidePreset.kt
  OledNightClockPreset.kt
  SplitDashboardPreset.kt
  CalendarBoardPreset.kt
  BatteryDockPreset.kt
  PhotoFramePlaceholderPreset.kt

ui/standby/theme/
  StandbyThemePreset.kt
  StandbyThemeRegistry.kt
  StandbyBackground.kt

ui/standby/widgets/
  TimeWidget.kt
  DateWidget.kt
  BatteryWidget.kt
  CalendarPreviewWidget.kt
  PhotoPlaceholderWidget.kt

settings/
  StandbyPresetSettings.kt
```

기존 프로젝트에 `feature/standby`, `presentation/standby`, `core/designsystem` 같은 구조가 있다면 그 구조를 우선합니다.

## 3. Preset 모델 예시

```kotlin
@Immutable
data class StandbyPreset(
    val id: String,
    val displayNameKo: String,
    val displayNameEn: String,
    val descriptionKo: String,
    val defaultThemeId: String,
    val widgets: List<StandbyWidgetType>,
    val burnInProtection: Boolean = false,
)
```

## 4. Preset Registry 예시

```kotlin
object StandbyPresetRegistry {
    val presets: List<StandbyPreset> = listOf(
        StandbyPreset(
            id = "minimal_clock",
            displayNameKo = "미니멀 시계",
            displayNameEn = "Minimal Clock",
            descriptionKo = "시간과 날짜만 크게 보여주는 기본 대기 화면",
            defaultThemeId = "midnight_glass",
            widgets = listOf(StandbyWidgetType.TIME, StandbyWidgetType.DATE),
        ),
        StandbyPreset(
            id = "warm_bedside",
            displayNameKo = "침실 시계",
            displayNameEn = "Warm Bedside",
            descriptionKo = "눈부심을 줄인 따뜻한 야간 시계",
            defaultThemeId = "warm_bedside",
            widgets = listOf(StandbyWidgetType.TIME, StandbyWidgetType.DATE),
        ),
        StandbyPreset(
            id = "oled_night_clock",
            displayNameKo = "OLED 야간 시계",
            displayNameEn = "OLED Night Clock",
            descriptionKo = "완전 검정 배경과 낮은 밝기의 야간 시계",
            defaultThemeId = "oled_pure_black",
            widgets = listOf(StandbyWidgetType.TIME),
            burnInProtection = true,
        ),
    )

    fun findById(id: String?): StandbyPreset =
        presets.firstOrNull { it.id == id } ?: presets.first()
}
```

## 5. Renderer 구조 예시

```kotlin
@Composable
fun StandbyRenderer(
    state: StandbyUiState,
    preset: StandbyPreset,
    theme: StandbyThemePreset,
    modifier: Modifier = Modifier,
) {
    val burnInModifier = if (preset.burnInProtection) {
        Modifier.burnInProtectionOffset()
    } else {
        Modifier
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .standbyBackground(theme)
            .then(burnInModifier)
    ) {
        when (preset.id) {
            "minimal_clock" -> MinimalClockPreset(state, theme)
            "warm_bedside" -> WarmBedsidePreset(state, theme)
            "oled_night_clock" -> OledNightClockPreset(state, theme)
            "split_dashboard" -> SplitDashboardPreset(state, theme)
            "calendar_board" -> CalendarBoardPreset(state, theme)
            "battery_dock" -> BatteryDockPreset(state, theme)
            "photo_frame_placeholder" -> PhotoFramePlaceholderPreset(state, theme)
            else -> MinimalClockPreset(state, theme)
        }
    }
}
```

## 6. Burn-in Protection 예시

```kotlin
@Composable
fun Modifier.burnInProtectionOffset(): Modifier {
    val periodMs = 60_000L
    val maxOffsetDp = 8.dp
    val infiniteTransition = rememberInfiniteTransition(label = "burn_in_protection")
    val offset by infiniteTransition.animateFloat(
        initialValue = -1f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = periodMs.toInt(), easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "burn_in_offset"
    )

    return this.offset(x = maxOffsetDp * offset, y = maxOffsetDp * -offset)
}
```

프로젝트의 Compose 버전/API에 따라 label 파라미터 지원 여부가 다를 수 있습니다. 빌드 오류가 나면 현재 Compose 버전에 맞게 수정합니다.

## 7. DataStore 저장 예시

기존 설정 저장 구조가 있으면 반드시 그것을 우선합니다.

신규로 추가해야 한다면 최소 키만 사용합니다.

```kotlin
object StandbyPresetPreferences {
    val SELECTED_PRESET_ID = stringPreferencesKey("selected_standby_preset_id")
    val SELECTED_THEME_ID = stringPreferencesKey("selected_standby_theme_id")
}
```

DataStore 접근은 Composable 내부에서 직접 하지 말고 Repository/ViewModel 계층을 통해 노출합니다.

## 8. StandbyActivity / DreamService 공유

기존 `StandbyActivity`와 `StandbyDreamService`가 있다면 둘 다 `StandbyScreen` 또는 `StandbyRenderer`를 사용하게 정리합니다.

```text
StandbyActivity
  -> StandbyRoute
    -> StandbyScreen
      -> StandbyRenderer

StandbyDreamService
  -> ComposeView
    -> StandbyScreen
      -> StandbyRenderer
```

단, 이번 작업은 대규모 리팩터링이 아니므로 기존 구조가 크게 다르면 최소 adapter만 추가합니다.

## 9. 검증 명령 예시

프로젝트에 맞는 명령을 우선합니다.

```bash
./gradlew test
./gradlew lint
./gradlew assembleDebug
```

실행하지 못한 명령은 성공으로 기록하지 않습니다.
