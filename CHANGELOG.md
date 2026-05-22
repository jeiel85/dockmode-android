# CHANGELOG.md

## v0.1.0 - 2026-05-22

DockMode 첫 동작 빌드 릴리즈. 자세한 본문은 [docs/releases/v0.1.0.md](docs/releases/v0.1.0.md) 또는 [GitHub Release](https://github.com/jeiel85/dockmode-android/releases/tag/v0.1.0) 참고.

### Added
- 가로 전체화면 시계·날짜·요일 대기 화면 (`StandbyActivity`)
- Android 스크린세이버(`StandbyDreamService`) 모드
- HomeScreen, SettingsScreen, 시계 스타일 3종, OLED 번인 방지 위치 이동
- BatteryManager 충전 상태, Calendar Provider 일정 조회, DataStore 설정 저장
- 한국어/영어 문자열 리소스, 다크 테마, 어댑티브 런처 아이콘(임시)

### Build / CI
- 태그 푸시 시 GitHub Release 자동 생성, APK/AAB/mapping 첨부 (`dockmode-v0.1.0-*`)
- ktlint/detekt + JUnit 단위 테스트 5종을 포함하는 GitHub Actions 워크플로

### Documentation
- README, RELEASE, docs/releases/README, play_store/release_notes/README 가이드 작성
- ADR-001 ~ ADR-008 (`DECISIONS.md`)

### Known Limitations
- Release AAB는 unsigned. Play Store 업로드 전 키스토어 구성 필요.
- 앱명 `DockMode` 상표/중복 검토 미완료.
- 정식 앱 아이콘 디자인 미완료.

## Unreleased

### Added
- `scripts/export-release-to-desktop.ps1`: 릴리즈된 AAB와 Play Store 노트를 사용자 바탕화면에 `dockmode-<tag>.aab` + `dockmode-<tag>-release-notes.txt` 형식으로 내보내는 PowerShell 스크립트 (최신 태그 자동 감지 지원)

### Documentation
- `RELEASE.md` §4-1 "바탕화면으로 산출물 내보내기" 추가
- `docs/releases/README.md` 흐름에 바탕화면 내보내기 단계 추가

### Added
- Kotlin + Jetpack Compose + Gradle Kotlin DSL 기반 Android 앱 골격 구성 (`namespace`/`applicationId` `io.jeiel85.dockmode`, minSdk 26, compileSdk 35)
- `MainActivity` HomeScreen: 앱 소개, 충전 상태 카드, 대기 화면 시작, 캘린더 권한 카드, 스크린세이버 설정 카드, 설정 진입
- `StandbyActivity`: 가로 고정 + 시스템 바 숨김 + `FLAG_KEEP_SCREEN_ON` 적용 전체화면
- `StandbyScreen`: 현재 시간/날짜/요일, 충전 상태, 다음 일정, 시계 스타일 3종 (Minimal/Digital/CalendarFocus)
- `BatteryStateRepository`: `ACTION_BATTERY_CHANGED` sticky broadcast 기반 충전 상태 Flow + 순수 매핑 함수 분리
- `CalendarRepository`: `READ_CALENDAR` 권한 확인 후 Calendar Provider 오늘 일정 조회 (ContentResolver, IO dispatcher)
- `SettingsRepository`: DataStore Preferences 기반 시계 스타일/캘린더 표시/야간 모드/번인 방지/화면 켜짐 유지 설정 저장
- `SettingsScreen`: 시계 스타일 선택, 4개 토글, 개인정보 안내 섹션
- `StandbyDreamService`: Android 스크린세이버 모드. Lifecycle/ViewModelStore/SavedStateRegistry owner 구현 및 `ComposeView` 재사용
- `BurnInOffset`: 1분 주기 원형 이동으로 정적 요소 위치를 미세 변경하는 OLED 번인 방지 로직
- 한국어(기본)/영어 문자열 리소스, 다크 컬러 팔레트, 어댑티브 런처 아이콘(임시), backup/data extraction 규칙
- 단위 테스트: `Formatters`, `CalendarFilters`, `BurnInOffset`, `BatteryStatusMapper`, `CalendarRepository.todayRangeMillis`
- ktlint(1.3.1) / detekt(1.23.7) Gradle 플러그인 설정, `config/detekt/detekt.yml`, `.editorconfig`
- Gradle 8.10.2 wrapper, `.gitattributes`로 줄바꿈 정규화

### Changed
- `app/build.gradle.kts`에서 `archivesName`을 `base { }` 블록으로 분리하여 산출물 접두어를 `dockmode`로 통일
- `TASKS.md` Milestone 0/1/2/3/4 진행 상태와 후속 작업 후보 갱신

### Documentation
- `HISTORY.md` 2026-05-22 초기 구현 항목 추가
- `DECISIONS.md`에 ADR-006 ~ ADR-008 추가 (DreamService Compose 호스팅 / 번인 방지 / 의존성 버전 선정)
- 기존 범용 `AGENTS.md` 템플릿을 DockMode 프로젝트 기준으로 통합
- 모바일 배포 산출물과 스토어 검증 규칙을 Android 앱 기준으로 반영

### Verification
- 로컬에서 `./gradlew` 실행 가능한 Gradle/Android SDK가 없어 빌드/테스트는 GitHub Actions에서 검증 예정
- 실기기 검증(DreamService 선택, 화면 켜짐 유지, 캘린더 권한 흐름)은 후속 작업으로 분리

### Earlier Unreleased Entries
- 프로젝트 코드 `DKM-ANDROID`, 레포 아이디 `dockmode-android`, Android applicationId `io.jeiel85.dockmode` 적용
- `PROJECT_ID.md` 식별값 기준 문서 추가
- DockMode Android 충전 거치대 시계·달력 앱 설계 문서 묶음 추가
- Kotlin + Jetpack Compose + DreamService 기반 기술 설계 추가
- Calendar Provider 기반 일정 표시 설계 추가
- 에이전트 실행용 3단계 Goal 프롬프트 추가
- 개인정보/권한 정책, 릴리즈 체크리스트, 의사결정 로그 추가

## v0.1.0 - 2026-05-22

### Added
- 초기 설계 묶음 작성
