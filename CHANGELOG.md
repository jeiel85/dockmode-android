# CHANGELOG.md

## Unreleased

### Build / CI
- 태그 푸시(`v*.*.*`) 시 GitHub Release를 자동 생성·갱신하고 Debug APK / Release AAB / R8 mapping을 첨부하도록 `Android CI` 워크플로 확장
- 산출물 파일명을 `dockmode-vX.Y.Z-debug.apk`, `dockmode-vX.Y.Z-release.aab`, `dockmode-vX.Y.Z-mapping.txt`로 정규화
- 워크플로에 `permissions: contents: write` 추가하여 `softprops/action-gh-release@v2`가 Release를 만들 수 있도록 권한 부여

### Documentation
- `docs/releases/README.md`, `play_store/release_notes/README.md` 신규 추가로 새 버전 만들기 절차와 형식 가이드 정의
- `docs/releases/v0.1.0.md`, `play_store/release_notes/v0.1.0.txt`를 실제 v0.1 구현 기준으로 재작성
- `RELEASE.md`를 태그 트리거 자동화 기준으로 전면 갱신 (산출물 파일명 표, 새 버전 만들기 절차, 롤백 절차 추가)

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
