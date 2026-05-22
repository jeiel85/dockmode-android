# DockMode

Android 충전 거치대용 시계·달력 대시보드 앱입니다.

DockMode는 스마트폰을 충전 스탠드에 올려둔 상태에서 가로 화면으로 시간, 날짜, 오늘 일정, 다음 일정을 보기 좋게 표시하는 Android 네이티브 앱입니다. iPhone StandBy와 유사한 사용 맥락을 목표로 하지만, 구현은 Android의 공식 기능과 정책을 따르는 독립 앱입니다.

> 현재 앱명은 작업용 이름입니다. Play Store 출시 전 이름 중복, 상표, 스토어 정책 검토가 필요합니다.

## 프로젝트 식별값

| 항목 | 값 | 비고 |
|---|---|---|
| Project Name | `DockMode` | 사용자 노출 작업명 |
| Project Code | `DKM-ANDROID` | 이슈, 브랜치, 문서 태그용 내부 코드 |
| Repository ID | `dockmode-android` | GitHub 저장소 slug |
| Repository URL | `https://github.com/jeiel85/dockmode-android.git` | 원격 저장소 기본값 |
| Android Application ID | `io.jeiel85.dockmode` | Play Store와 Android 설치 식별자 |
| Android Namespace | `io.jeiel85.dockmode` | Gradle namespace 기본값 |
| Artifact Prefix | `dockmode` | APK/AAB/릴리즈 파일 접두어 |

> 위 값은 설계 묶음에 적용된 초기 기준값입니다. 실제 GitHub 저장소가 아직 만들어지지 않았다면 먼저 `dockmode-android` 이름으로 저장소를 생성한 뒤 사용하세요.

## 핵심 가치

- 침대 옆, 책상 위, 사무실 충전 거치대에서 바로 보는 시계와 일정
- 화면을 켜둔 상태에서도 눈부심과 번인 위험을 줄이는 대기 화면
- Android의 `DreamService`를 활용한 스크린세이버형 경험
- 캘린더 권한을 필요한 순간에만 요청하는 로컬 우선 구조

## 1차 기능 범위

### v0.1 수동 대기 화면

- 가로 전용 전체화면 시계
- 날짜와 요일 표시
- 충전 상태 감지
- 화면 꺼짐 방지
- 야간용 어두운 테마

### v0.2 일정 표시

- `READ_CALENDAR` 권한 요청 UX
- 오늘 일정 목록
- 다음 일정 표시
- 권한 거부 상태의 빈 화면 UX

### v0.3 스크린세이버 모드

- `DreamService` 등록
- Android 설정에서 스크린세이버로 선택할 수 있는 안내 화면
- 충전 거치/유휴 상태에서 대기 화면 경험 제공

### v1.0 출시 후보

- 시계 스타일 3종
- 번인 방지 위치 이동
- 저휘도/야간 색상 모드
- 태블릿/폴더블 레이아웃
- Play Store용 AAB, 릴리즈 노트, 개인정보 처리 안내

## 기술 스택

| 영역 | 선택 |
|---|---|
| 언어 | Kotlin |
| UI | Jetpack Compose |
| 아키텍처 | MVVM + Repository |
| 설정 저장 | Jetpack DataStore |
| 일정 데이터 | Android Calendar Provider |
| 충전 상태 | BatteryManager |
| 스크린세이버 | DreamService |
| 빌드 | Gradle Kotlin DSL |
| CI | GitHub Actions |

## 문서 구조

| 파일 | 역할 |
|---|---|
| `AGENTS.md` | 에이전트 작업 규칙과 프로젝트 고유 제약 |
| `SPEC.md` | 제품 요구사항과 사용자 흐름 |
| `TECH_SPEC.md` | 기술 아키텍처, 컴포넌트, 코드 설계 |
| `TASKS.md` | 구현 작업 목록과 완료 기준 |
| `GOALS.md` | 에이전트에게 바로 줄 수 있는 `/goal` 프롬프트 |
| `DECISIONS.md` | 기술적 결정과 근거 |
| `PRIVACY.md` | 권한, 개인정보, 로컬 데이터 정책 |
| `RELEASE.md` | 빌드, 검증, 릴리즈 절차 |
| `CHANGELOG.md` | 사용자에게 공개 가능한 변경 요약 |
| `HISTORY.md` | 작업 과정, 검증, 후속 작업 기록 |

## 에이전트 시작 방법

1. 새 Android 저장소 루트에 이 묶음의 파일을 복사합니다.
2. `AGENTS.md`의 저장소 URL, 패키지명, Target SDK 값을 실제 프로젝트에 맞게 확인합니다.
3. 에이전트에게 `GOALS.md`의 Goal 1부터 순서대로 실행하게 합니다.
4. 각 Goal 완료 후 `TASKS.md`, `HISTORY.md`, `CHANGELOG.md`, `DECISIONS.md`를 갱신하게 합니다.

## 권장 저장소명

```text
dockmode-android
```

## 프로젝트 코드

```text
DKM-ANDROID
```

## Android 애플리케이션 ID

```text
io.jeiel85.dockmode
```

## 권장 패키지명

```text
io.jeiel85.dockmode
```

## 주의 사항

- 충전기를 연결했다는 이유만으로 앱이 백그라운드에서 임의로 Activity를 강제 실행하면 Android 정책과 사용자 경험에 문제가 생길 수 있습니다.
- 따라서 자동 경험은 `DreamService`와 사용자가 탭하는 알림/바로가기 중심으로 설계합니다.
- 날씨, 외부 캘린더 API, 계정 로그인은 v1 이후 별도 승인 기능으로 분리합니다.
