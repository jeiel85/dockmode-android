# DockMode 대기모드 프리셋 추가 개발 계획

## 1. 배경

DockMode의 초기 구현은 이미 완료되어 있다고 가정합니다. 현재 추가로 필요한 것은 기본 대기모드 화면의 단조로움을 줄이고, 사용자가 거치 환경에 맞게 선택할 수 있는 여러 시각 모드를 제공하는 것입니다.

이번 작업은 디자인 소스, 이미지 에셋, 외부 API 없이 **Jetpack Compose 코드만으로 구현 가능한 프리셋 확장**을 우선합니다.

## 2. 목표

- 기존 대기화면을 유지하면서 프리셋 선택 구조를 추가합니다.
- 최소 5개 이상의 대기모드 프리셋을 제공합니다.
- 최소 5개 이상의 테마 프리셋을 제공합니다.
- 선택한 프리셋은 기존 설정 저장 구조가 있으면 그것을 사용하고, 없으면 DataStore 기반으로 저장합니다.
- StandbyActivity와 StandbyDreamService가 존재한다면 같은 렌더러를 공유합니다.
- 기존 구조를 확인한 뒤 최소 변경으로 추가합니다.

## 3. 추가할 사용자 경험

### 3.1 프리셋 전환

- Standby 화면에서 좌우 스와이프로 프리셋 전환
- 설정 화면에서 프리셋 선택
- 마지막 선택 프리셋 저장
- DreamService 모드에서도 동일한 프리셋 사용

### 3.2 디자인 모드

- 침실용 저휘도 모드
- 책상용 대시보드 모드
- OLED 번인 방지용 블랙 모드
- 캘린더 중심 모드
- 충전 상태를 강조하는 도크 모드

### 3.3 코드 기반 디자인

이번 단계에서는 별도 디자인 소스 없이 다음 요소만 사용합니다.

- Compose `Box`, `Column`, `Row`, `Canvas`, `Text`
- Material 3 theme/token
- Brush gradient
- RoundedCornerShape
- alpha/blur/shadow 효과
- Compose animation
- Canvas 기반 아날로그/배터리 그래픽

## 4. 비목표

아래는 이번 추가 개발의 범위가 아닙니다.

- 앱 전체 리디자인
- 네트워크 기반 날씨 연동
- 사진 프레임 실제 갤러리 연동
- 사용자 계정/동기화
- 광고/분석 SDK
- 신규 외부 디자인 에셋 대량 추가
- 기존 패키지명/앱 ID 변경
- 초기 프로젝트 생성 작업 재수행

## 5. 권장 마일스톤

### Milestone A — 기존 구조 확인 및 안전한 확장점 확보

- 현재 Standby 화면 진입점 확인
- 기존 Theme/Settings/DataStore 구조 확인
- 기존 Activity/DreamService 구조 확인
- 프리셋 모델을 추가할 위치 결정
- 기존 구현을 깨지 않는 어댑터 방식 우선

### Milestone B — Preset 모델과 저장 구조 추가

- `StandbyPresetId` 정의
- `StandbyPreset` 정의
- `StandbyThemePreset` 정의
- `StandbyWidgetType` 정의
- 선택 프리셋 저장/복원

### Milestone C — 기본 프리셋 구현

- Minimal Clock
- Warm Bedside Clock
- OLED Night Clock
- Split Dashboard
- Calendar Board
- Battery Dock
- Photo Frame Placeholder

### Milestone D — 전환 UX와 설정 화면 연결

- Standby 화면 좌우 스와이프
- 설정 화면 목록 연결
- 현재 프리셋 미리보기 또는 이름 표시
- 접근성 라벨 추가

### Milestone E — 검증 및 문서 갱신

- 빌드/테스트/lint 가능한 범위 실행
- `TASKS.md` 갱신
- `HISTORY.md` 갱신
- `CHANGELOG.md` 갱신
- `DECISIONS.md` 갱신

## 6. 완료 결과

완료 시 사용자는 기존 DockMode 앱 안에서 여러 대기모드 프리셋을 전환할 수 있어야 합니다. 프리셋 전환은 앱을 재시작해도 유지되어야 하며, DreamService가 이미 구현되어 있다면 해당 모드에서도 동일하게 반영되어야 합니다.
