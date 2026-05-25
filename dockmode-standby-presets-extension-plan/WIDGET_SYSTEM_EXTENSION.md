# Widget System Extension

## 1. 목적

이번 추가 개발에서 말하는 Widget은 Android 홈 화면 위젯이 아니라, Standby 화면 내부를 구성하는 작은 정보 블록입니다.

예:

- Time widget
- Date widget
- Battery widget
- Charging state widget
- Calendar preview widget
- Photo placeholder widget

## 2. 권장 모델

```kotlin
enum class StandbyWidgetType {
    TIME,
    DATE,
    BATTERY,
    CHARGING_STATE,
    CALENDAR_PREVIEW,
    PHOTO_PLACEHOLDER,
    PRESET_NAME
}
```

권장 Composable 구조:

```kotlin
@Composable
fun StandbyWidget(
    type: StandbyWidgetType,
    state: StandbyUiState,
    theme: StandbyThemePreset,
    modifier: Modifier = Modifier
) {
    when (type) {
        StandbyWidgetType.TIME -> TimeWidget(state.time, theme, modifier)
        StandbyWidgetType.DATE -> DateWidget(state.date, theme, modifier)
        StandbyWidgetType.BATTERY -> BatteryWidget(state.battery, theme, modifier)
        StandbyWidgetType.CHARGING_STATE -> ChargingStateWidget(state.chargingState, theme, modifier)
        StandbyWidgetType.CALENDAR_PREVIEW -> CalendarPreviewWidget(state.calendar, theme, modifier)
        StandbyWidgetType.PHOTO_PLACEHOLDER -> PhotoPlaceholderWidget(theme, modifier)
        StandbyWidgetType.PRESET_NAME -> PresetNameWidget(state.currentPreset, theme, modifier)
    }
}
```

## 3. 최소 상태 모델

기존 `UiState`가 있으면 그 구조를 우선합니다. 없으면 아래와 유사한 구조를 추가합니다.

```kotlin
@Immutable
data class StandbyUiState(
    val time: LocalTime,
    val date: LocalDate,
    val isCharging: Boolean,
    val batteryPercent: Int?,
    val selectedPresetId: String,
    val calendar: CalendarPreviewState = CalendarPreviewState.Unavailable,
)
```

## 4. Calendar Preview 처리

캘린더 권한이 있으면 기존 일정 조회 구조를 사용합니다.

권한이 없으면 다음 중 하나를 표시합니다.

```text
오늘 일정 표시를 설정에서 켤 수 있습니다.
```

또는 더 간결하게:

```text
Calendar off
```

이번 작업에서는 캘린더 기능 자체를 새로 크게 구현하지 않습니다. 기존 Calendar Provider 연동이 없다면 placeholder 상태만 제공합니다.

## 5. Photo Placeholder 처리

이번 작업에서는 사진 접근 권한을 추가하지 않습니다.

Photo Frame Placeholder는 다음 중 하나로 구현합니다.

- 코드 기반 gradient background
- abstract shape pattern
- blurred color panels
- 임시 placeholder card

## 6. Battery Widget

배터리 정보가 이미 있다면 기존 로직을 사용합니다.

없으면 다음 단계로 최소 구현합니다.

- 충전 여부는 기존 BatteryStateObserver 또는 Android BatteryManager 기반 로직 사용
- 퍼센트 조회가 어렵거나 기존 구조가 없다면 `isCharging`만 표시
- 배터리 ring/capsule 디자인은 Canvas 또는 Box 기반으로 구현

## 7. 접근성

- 시계는 TalkBack에서 현재 시간을 읽을 수 있게 합니다.
- 프리셋 전환 버튼 또는 목록에는 한국어/영어 문자열을 추가합니다.
- 시각 효과만으로 상태를 전달하지 않습니다.
