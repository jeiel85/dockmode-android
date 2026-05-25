# CHANGELOG.md

## Unreleased

### Documentation
- GitHub Pages 랜딩 페이지를 실제 앱 스크린샷, 권한 정책, Play Store 준비 자산을 보여 주는 제품 소개형 페이지로 개편
- README를 앱 정체성, 화면 미리보기, 현재 구현 상태, Play Store 등록 자료 경로 중심으로 재구성
- Pages용 Open Graph 이미지와 favicon으로 `play_store/graphics/`의 앱 아이콘/피처 그래픽 사본을 추가

### Verification
- GitHub Pages 설정(`main` 브랜치 `/docs`, HTTPS enforced), 저장소 description/homepage/topics 확인
- GitHub Actions Android CI와 pages-build-deployment 성공 확인, 공개 랜딩/개인정보 URL HTTP 200 확인

## v1.1.0 - 2026-05-25

### Added
- DockMode 대기 화면에 여러 Standby Preset을 선택할 수 있는 구조 추가
- Minimal Clock, Warm Bedside, OLED Night Clock, Split Dashboard, Calendar Board, Battery Dock, Photo Frame 등 코드 기반 대기모드 프리셋 추가
- Midnight Glass, Warm Bedside, OLED Pure Black, Aurora Gradient, Paper Calendar, Material You 등 테마 프리셋 추가
- 선택한 대기모드 프리셋과 테마를 저장하고 복원하는 설정 흐름 추가
- OLED 야간 시계용 번인 방지 offset 로직 추가

### Changed
- StandbyActivity 및 StandbyDreamService에서 선택된 프리셋을 기준으로 대기 화면을 렌더링하도록 개선
- 기존 대기 화면 UI를 프리셋 기반 확장 구조로 정리

### Documentation
- 대기모드 프리셋, 테마, 위젯 구조 문서 추가 또는 갱신
- 후속 디자인 확장 작업을 TASKS.md에 기록

### Verification
- 로컬 단위 테스트 `./gradlew testDebugUnitTest` 100% 통과
- ktlint/detekt 정적 분석 `./gradlew ktlintCheck detekt` 100% 통과
- 디버그 빌드 APK 생성 `./gradlew assembleDebug` 성공

## v0.1.2 - 2026-05-23

설정 화면 시계 스타일 카드의 정적 미리보기 텍스트를 실제 현재 시각 기반 동적 표시로 교체. 자세한 본문: [docs/releases/v0.1.2.md](docs/releases/v0.1.2.md).

### Changed
- `SettingsScreen.ClockStyleSelectorSection`이 `produceState`로 매초 갱신되는 `nowMillis`를 받아 세 카드의 미리보기를 동적으로 표시. 정적 텍스트(`"12:00"`, `"12:00:30"`, `"12 / 일정"`) 제거.
- 미니멀 = `HH:mm`, 디지털 = `HH:mm:ss`, 캘린더 중심 = `HH:mm · 일정` (locale별 suffix)

### Added
- `settings_clock_style_calendar_preview_suffix` 문자열 (ko=`일정`, en=`Events`)

### Build / CI
- `versionCode` 2 → 3, `versionName` 0.1.1 → 0.1.2

### Documentation
- `play_store/release_notes/v0.1.2.txt`, `docs/releases/v0.1.2.md` 추가

## v0.1.1 - 2026-05-23

DockMode 첫 Play Store 출시용 signed 빌드. 코드/디자인 동일, versionCode/서명만 변경. 자세한 본문: [docs/releases/v0.1.1.md](docs/releases/v0.1.1.md).

### Build / CI
- `versionCode` 1 → 2, `versionName` 0.1.0 → 0.1.1
- GitHub Actions Secrets(`DOCKMODE_KEYSTORE_BASE64` 외 3종)로 등록된 업로드 키스토어로 release AAB 자동 서명. v0.1.0의 unsigned AAB를 대체하는 첫 signed 산출물.

### Documentation
- `play_store/release_notes/v0.1.1.txt`, `docs/releases/v0.1.1.md` 추가

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

## Earlier Unreleased Work

### Added
- Play Store 그래픽 자산 추가
  - `play_store/graphics/app-icon-512.png`: 고해상도 앱 아이콘 512×512 PNG
  - `play_store/graphics/feature-graphic-1024x500.png`: 피처 그래픽 1024×500 PNG
  - `play_store/graphics/README.md`: 그래픽 자산 용도와 재생성 방법
- Play Console 최초 등록 자료 일괄 추가
  - `play_store/listing/`: 한국어/영어 짧은·긴 설명, 카테고리·타깃 사용자, 데이터 보안 양식, 콘텐츠 등급(IARC) 설문 답변 초안 + 매핑 README
  - `play_store/onboarding-checklist.md`: Play Console 클릭 단계 + 사용자/자동 구분 체크리스트
  - `docs/branding-research.md`: Play Store 검색 결과·상표 등록 사전 점검·후보명·부제 권장안
  - `docs/keystore-guide.md`: keytool 키스토어 생성, Play App Signing 옵트인, GitHub Actions Secrets 등록 단계
  - `docs/privacy.md`: Play Console 입력용 개인정보 처리방침 공개 페이지 (Jekyll permalink `/privacy/`)
- `app/build.gradle.kts` `signingConfigs.release`: 환경변수 4종(`DOCKMODE_KEYSTORE_PATH`, `_PASSWORD`, `DOCKMODE_KEY_PASSWORD`, `_ALIAS`) 모두 있을 때만 적용되는 conditional signing. 시크릿 미설정 시 기존 unsigned 빌드 동작 유지
- GitHub Actions 워크플로 `Decode upload keystore` 단계: `DOCKMODE_KEYSTORE_BASE64` 시크릿이 있으면 임시 파일로 디코드 후 `bundleRelease`에 환경변수 전달

### Changed
- 어댑티브 런처 아이콘의 배경/전경 벡터를 정식 앱 아이콘과 같은 시계·거치대 모티프로 갱신
- Play Console 체크리스트와 등록정보 README의 앱 아이콘/피처 그래픽 상태를 준비 완료로 갱신
- `PRIVACY.md` 상단에 공개 URL(`https://jeiel85.github.io/dockmode-android/privacy/`) 안내 추가
- `docs/index.md` 개인정보 링크를 GitHub 마크다운 raw 대신 GitHub Pages 내부 페이지로 교체
- `RELEASE.md` §7을 unsigned 메모에서 키스토어 가이드 요약·시크릿 흐름·체크리스트로 확장. §6 Play Store 체크리스트에 PRIVACY URL, listing 자료, 스크린샷, 상표 검토 항목 추가

### Changed
- 시스템 바 색상 처리를 `WindowInsetsControllerCompat` 기반 라이트/다크 아이콘 토글로 교체. Android 15에서 deprecated된 `window.statusBarColor` / `navigationBarColor` 직접 호출 제거 (배경 색은 `enableEdgeToEdge` + 화면 배경에 위임).
- UI 디자인 리뉴얼 1차 이식: `D:/Project/dockmode-renew` 시안의 화면 구성·컬러·타이포그래피를 본 프로젝트 패키지(`io.jeiel85.dockmode`)에 맞춰 가져옴.
  - HomeScreen: "DOCK MODE" 라벨 헤더, 충전 상태 카드(아이콘·펄스 애니메이션), 캘린더/스크린세이버 카드 아이콘화, Start Standby 풀폭 CTA 강조
  - SettingsScreen: 시계 스타일 3종을 미리보기가 포함된 카드형 세그먼트 셀렉터로 교체, 토글 항목마다 설명 문구 추가, 개인정보 카드 강조
  - StandbyScreen: 디지털 레이아웃에 우측 글래스 패널, 캘린더 포커스 레이아웃에 이벤트 리스트 카드, 미니멀 모드 야간 호박색 톤, 우상단 닫기 버튼 추가
  - MainActivity: `androidx.navigation.compose` 기반 `NavHost`로 home ↔ settings 전환
  - 테마: 새 "Premium Ambient Slate & Obsidian" 다크 팔레트 + 라이트 팔레트, 상태바/네비게이션바 색상 동기화, 풍부한 Typography
- 한국어/영어 문자열 보강: 설정 토글 설명 문구 4종, 대기 화면 닫기/일정 표시 꺼짐 문구 추가

### Added
- `scripts/export-release-to-desktop.ps1`: 릴리즈된 AAB와 Play Store 노트를 사용자 바탕화면에 `dockmode-<tag>.aab` + `dockmode-<tag>-release-notes.txt` 형식으로 내보내는 PowerShell 스크립트 (최신 태그 자동 감지 지원)
- `androidx.compose.material:material-icons-core` / `material-icons-extended` 의존성 (Bolt, CalendarToday, Monitor, Settings, PlayArrow, CheckCircle, Warning, Info, ArrowBack, Close, EventNote 사용)
- `androidx.navigation:navigation-compose` 의존성 (홈 ↔ 설정 화면 전환)
- 리뉴얼 디자인 실기기 스크린샷 5종 (`docs/screenshots/01-home.png`, `02-settings.png`, `03-standby-minimal.png`, `04-standby-digital.png`, `05-standby-calendar.png`) + 보조 컷 `01b-home-scrolled.png`. Galaxy S24에서 캡처
- Play Store 등록용 스크린샷 사본 (`play_store/screenshots/`)과 업로드 순서 가이드 README
- README "화면 미리보기" 섹션 신설 (Home/Settings + Standby 3종 비교표)

### Documentation
- `RELEASE.md` §4-1 "바탕화면으로 산출물 내보내기" 추가
- `docs/releases/README.md` 흐름에 바탕화면 내보내기 단계 추가
- `HISTORY.md`에 2026-05-22 디자인 리뉴얼 1차 이식 + 2026-05-23 statusBar deprecation 대응/스크린샷 작업 기록
- `DECISIONS.md` ADR-009 (외부 디자인 시안 이식 정책) 추가
- `docs/screenshots/README.md` (캡처 환경/재캡처 방법), `play_store/screenshots/README.md` (업로드 순서/Play Console 요건)

### Verification
- 로컬: `./gradlew ktlintCheck detekt testDebugUnitTest assembleDebug lintDebug` 모두 성공
- 실기기(Galaxy S24): Home/Settings/Standby 3종 화면 동작 확인, 시스템 바 아이콘이 다크 배경에 라이트 톤으로 정상 표시
- CI: 푸시 후 GitHub Actions 결과 확인 예정

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
