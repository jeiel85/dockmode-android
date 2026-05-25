# Standby Presets

## 1. 프리셋 원칙

프리셋은 사용자가 선택 가능한 완성형 대기 화면입니다. 내부적으로는 Mode, Theme, Widget 조합이지만, 사용자는 복잡한 조합을 직접 만들지 않고 프리셋 이름으로 선택합니다.

```text
Preset = Mode + Theme + Widget Layout + Motion Rules
```

## 2. 1차 구현 프리셋

### 2.1 Minimal Clock

```text
Preset ID: minimal_clock
Purpose: 가장 기본적인 큰 시계 화면
Use Case: 책상, 거실, 일반 충전 거치
Required Widgets: Time, Date, ChargingState(optional)
Theme: Midnight Glass 또는 Material You
Implementation Priority: P0
```

UI 특징:

- 화면 중앙 큰 디지털 시계
- 날짜는 시계 아래 작게 표시
- 배경은 어두운 그라데이션 또는 순수 블랙
- 불필요한 정보 표시 최소화

### 2.2 Warm Bedside

```text
Preset ID: warm_bedside
Purpose: 침대 옆에서 눈부심 없이 보기 좋은 따뜻한 시계
Use Case: 야간 충전, 침실
Required Widgets: Time, Date
Theme: Warm Bedside
Implementation Priority: P0
```

UI 특징:

- 낮은 밝기 느낌의 앰버/오렌지 계열
- 시계는 크지만 대비를 과하게 높이지 않음
- 배경은 거의 검정에 가까움
- 모션은 최소화

### 2.3 OLED Night Clock

```text
Preset ID: oled_night_clock
Purpose: OLED 번인과 배터리 소모를 줄이는 저휘도 화면
Use Case: 야간 장시간 거치
Required Widgets: Time
Theme: OLED Pure Black
Implementation Priority: P0
```

UI 특징:

- 완전 검정 배경
- 작은 크기의 시간 표시
- 일정 주기로 아주 미세한 위치 이동
- 흰색보다는 어두운 회색 또는 붉은 계열 사용

### 2.4 Split Dashboard

```text
Preset ID: split_dashboard
Purpose: 시간과 주요 정보를 좌우로 나누어 표시
Use Case: 책상, 업무 중 거치
Required Widgets: Time, Date, Battery, NextEventPlaceholder
Theme: Midnight Glass
Implementation Priority: P1
```

UI 특징:

- 왼쪽: 큰 시계
- 오른쪽: 오늘 정보 카드
- 일정 권한이 없으면 “일정 표시를 설정에서 켤 수 있음” 안내
- 카드형 레이아웃 사용

### 2.5 Calendar Board

```text
Preset ID: calendar_board
Purpose: 오늘 날짜와 일정 중심 표시
Use Case: 업무/일정 확인
Required Widgets: Time, Date, CalendarPreview
Theme: Paper Calendar 또는 Material You
Implementation Priority: P1
```

UI 특징:

- 시간은 상단 또는 좌측에 보조 표시
- 오늘 날짜와 일정 목록이 중심
- 일정 권한이 없으면 안전한 빈 상태 표시
- 실제 Calendar Provider 연동이 이미 있으면 연결, 없으면 placeholder만 구현

### 2.6 Battery Dock

```text
Preset ID: battery_dock
Purpose: 충전 중인 기기의 느낌을 강조하는 도크 화면
Use Case: MagSafe형 거치대, 책상 충전
Required Widgets: Time, Battery, ChargingState
Theme: Aurora Gradient 또는 Material You
Implementation Priority: P1
```

UI 특징:

- 충전 상태를 큰 링 또는 바 형태로 표현
- 충전 중이면 은은한 pulse 애니메이션
- 완충 상태일 때 다른 표현
- 배터리 퍼센트가 없으면 상태 텍스트만 표시

### 2.7 Photo Frame Placeholder

```text
Preset ID: photo_frame_placeholder
Purpose: 향후 사진 프레임 기능을 위한 UI 뼈대
Use Case: 가족사진/인테리어 모드의 후속 확장
Required Widgets: Time, Date, PhotoPlaceholder
Theme: Midnight Glass
Implementation Priority: P2
```

UI 특징:

- 실제 사진 접근 권한은 요청하지 않음
- 임시 그라데이션/패턴 배경 사용
- 향후 사진 연동이 들어갈 위치만 준비
- 권한 추가는 별도 후속 작업으로 분리

## 3. 1차 구현 우선순위

| 우선순위 | 프리셋 |
|---|---|
| P0 | Minimal Clock |
| P0 | Warm Bedside |
| P0 | OLED Night Clock |
| P1 | Split Dashboard |
| P1 | Calendar Board |
| P1 | Battery Dock |
| P2 | Photo Frame Placeholder |

## 4. 성공 기준

- 최소 P0 프리셋 3개는 실제 동작해야 합니다.
- P1 프리셋은 가능한 범위에서 실제 동작하되, 권한/기존 구현 부족으로 어려우면 깨지지 않는 placeholder를 제공합니다.
- P2 프리셋은 placeholder 수준이어도 됩니다.
- 사용자는 프리셋을 선택하고 저장할 수 있어야 합니다.
