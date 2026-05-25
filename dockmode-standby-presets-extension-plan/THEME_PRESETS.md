# Theme Presets

## 1. 목적

Theme Preset은 Standby 화면의 색상, 배경, 텍스트, 카드, 강조 효과, 모션 성격을 정의합니다. 디자인 소스 없이 Compose 코드만으로 구현 가능한 수준을 기준으로 합니다.

## 2. 테마 모델

권장 모델:

```kotlin
@Immutable
data class StandbyThemePreset(
    val id: String,
    val displayNameKo: String,
    val displayNameEn: String,
    val background: StandbyBackgroundSpec,
    val textColor: Color,
    val secondaryTextColor: Color,
    val accentColor: Color,
    val cardColor: Color,
    val isLowBrightness: Boolean,
    val motionIntensity: MotionIntensity
)
```

## 3. 1차 테마

### 3.1 Midnight Glass

```text
Theme ID: midnight_glass
Mood: 어두운 유리, 은은한 블루/퍼플 글로우
Recommended Presets: Minimal Clock, Split Dashboard, Photo Frame Placeholder
```

구현 요소:

- 어두운 radial gradient
- 반투명 카드
- 흐린 accent glow
- white 또는 blue-tinted white 텍스트

### 3.2 Warm Bedside

```text
Theme ID: warm_bedside
Mood: 침실용 따뜻한 앰버 시계
Recommended Presets: Warm Bedside, Minimal Clock
```

구현 요소:

- 거의 검정에 가까운 배경
- amber/orange 계열 텍스트
- 낮은 대비
- 모션 최소화

### 3.3 OLED Pure Black

```text
Theme ID: oled_pure_black
Mood: 완전 검정, 번인 최소화
Recommended Presets: OLED Night Clock
```

구현 요소:

- `Color.Black` 배경
- 작은 정보량
- 낮은 밝기의 red/gray 텍스트
- burn-in offset 필수

### 3.4 Aurora Gradient

```text
Theme ID: aurora_gradient
Mood: 은은한 오로라/그라데이션
Recommended Presets: Battery Dock, Minimal Clock
```

구현 요소:

- dark navy 기반 gradient
- 초록/보라/파랑 계열 accent를 아주 약하게 사용
- 충전 pulse와 잘 맞게 설계

### 3.5 Paper Calendar

```text
Theme ID: paper_calendar
Mood: 디지털 종이 달력 느낌
Recommended Presets: Calendar Board
```

구현 요소:

- 완전한 흰색이 아니라 낮은 밝기의 warm surface
- 어두운 텍스트
- 카드/라인 중심
- 야간 사용 시 자동으로 어두운 변형을 쓰는 옵션 고려

### 3.6 Material You

```text
Theme ID: material_you
Mood: Android 기기와 잘 어울리는 동적 색감
Recommended Presets: 모든 기본 프리셋
```

구현 요소:

- 기존 MaterialTheme가 있으면 우선 활용
- dynamicColor 구조가 이미 있다면 연결
- 없으면 기본 컬러 fallback 사용

## 4. 색상 구현 기준

정확한 색상값은 기존 앱 테마와 충돌하지 않도록 에이전트가 조정할 수 있습니다. 다만 아래 방향을 지킵니다.

| 테마 | 배경 | 텍스트 | 강조 |
|---|---|---|---|
| Midnight Glass | near black/navy | off white | blue/purple |
| Warm Bedside | near black | amber | orange |
| OLED Pure Black | pure black | dim gray/red | muted red |
| Aurora Gradient | dark navy | white | cyan/violet/green |
| Paper Calendar | warm surface | charcoal | brown/gold |
| Material You | app theme | app theme | app theme |

## 5. 주의사항

- 밝은 색 고정 텍스트를 OLED 화면에 오래 표시하지 않습니다.
- 야간 모드 계열은 과도한 glow를 피합니다.
- 카드 shadow/blur는 성능이 나쁘면 단순 alpha 카드로 대체합니다.
- 외부 이미지 배경은 사용하지 않습니다.
