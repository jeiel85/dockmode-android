# HISTORY.md

## 2026-05-22

- 작업: GOALS.md Goal 1+2+3 통합 구현. DockMode Android 앱의 Gradle 골격, MainActivity/HomeScreen, StandbyActivity, StandbyDreamService, 캘린더 권한 UX, 번인 방지, 다크 테마, 단위 테스트, ktlint/detekt 설정 일괄 추가.
- 적용 식별값:
  - Gradle namespace/applicationId: `io.jeiel85.dockmode`
  - minSdk 26 / compileSdk 35 / versionName 0.1.0 / versionCode 1
  - 산출물 접두어: `dockmode`
- 변경 파일:
  - 신규: `settings.gradle.kts`, `build.gradle.kts`, `gradle.properties`, `gradle/libs.versions.toml`, `gradle/wrapper/gradle-wrapper.{jar,properties}`, `gradlew`, `gradlew.bat`, `.editorconfig`, `.gitattributes`, `config/detekt/detekt.yml`
  - 신규: `app/build.gradle.kts`, `app/proguard-rules.pro`, `app/src/main/AndroidManifest.xml`
  - 신규: `app/src/main/res/values/{strings,colors,themes}.xml`, `values-en/strings.xml`, `values-night/themes.xml`, `xml/{standby_dream,backup_rules,data_extraction_rules}.xml`, `mipmap-anydpi-v26/*`, `drawable/ic_launcher_*.xml`
  - 신규 Kotlin: `DockModeApplication.kt`, `MainActivity.kt`, `domain/model/*`, `data/battery/BatteryStateRepository.kt`, `data/calendar/CalendarRepository.kt`, `data/settings/SettingsRepository.kt`, `home/HomeScreen.kt`, `home/HomeViewModel.kt`, `settings/SettingsScreen.kt`, `settings/SettingsViewModel.kt`, `standby/{StandbyActivity,StandbyDreamService,StandbyRoute,StandbyScreen,StandbyUiState,StandbyViewModel}.kt`, `ui/theme/{Color,Theme,Type}.kt`, `util/{Formatters,CalendarFilters,BurnInOffset,TickerFlow}.kt`
  - 신규 테스트: `app/src/test/java/io/jeiel85/dockmode/util/{Formatters,CalendarFilters,BurnInOffset}Test.kt`, `data/battery/BatteryChargingMapperTest.kt`, `data/calendar/CalendarRepositoryRangeTest.kt`
  - 갱신: `TASKS.md` (Milestone 0~4 체크 갱신), `CHANGELOG.md` (Unreleased 상세화), `DECISIONS.md` (ADR-006~008 추가), `HISTORY.md` (본 항목)
- 검증:
  - 로컬 환경에 Gradle CLI와 Android SDK가 없음 (`JAVA_HOME` 미설정, `ANDROID_HOME` 없음). `./gradlew` 실행 불가로 lint/test/build는 모두 GitHub Actions에 위임.
  - 정적 검토: Manifest 권한·서비스 메타데이터, ProjectId 일치, Compose 컴포저블 구조, 권한 분기, DreamService Lifecycle 처리 수동 확인.
  - 단위 테스트는 android.jar의 `static final int` 상수만 참조하도록 매핑 로직을 순수 함수(`BatteryStatusMapper.map`)로 분리해 작성.
- 결과: 코드/리소스/문서 묶음 작성 완료. 빌드 성공 여부는 CI 결과 확인 후 별도 기록 필요.
- 후속 작업:
  - GitHub Actions `Android CI` 워크플로 결과 확인 (lint/test/assembleDebug/bundleRelease 단계)
  - 실기기에서 StandbyActivity 화면 유지, 캘린더 권한 흐름, DreamService 선택/표시 수동 검증 후 본 문서에 추가 기록
  - 태블릿/폴더블 가로 분할 레이아웃 최적화
  - 야간 모드 자동 적용 정책(시간 기반/조도 센서) 결정
  - 정식 앱 아이콘 디자인 (현재 어댑티브 아이콘은 임시 벡터)

## 2026-05-22

- 작업: DockMode 프로젝트 코드와 레포 아이디 확정 및 설계 묶음 반영
- 적용 식별값:
  - Project Code: `DKM-ANDROID`
  - Repository ID: `dockmode-android`
  - Repository URL: `https://github.com/jeiel85/dockmode-android.git`
  - Android applicationId/namespace: `io.jeiel85.dockmode`
- 변경 파일:
  - PROJECT_ID.md: 프로젝트 식별값 기준 문서 추가
  - AGENTS.md: 프로젝트 설정값과 고유 정책에 식별값 반영
  - README.md: 프로젝트 식별값 표 추가
  - GOALS.md: Goal 프롬프트에 PROJECT_ID.md 확인 절차와 식별값 반영
  - TECH_SPEC.md: 기술 설계 식별값 블록 추가
  - TASKS.md: 저장소 초기화 작업에 식별값 확인 항목 추가
  - DECISIONS.md: ADR-005 추가
  - CHANGELOG.md: 식별값 적용 변경 사항 기록
- 검증:
  - 문서 내 기존 임시 패키지명 참조 제거 확인
  - ZIP 묶음 재생성 확인
- 결과: 성공
- 후속 작업:
  - 실제 GitHub 저장소 `dockmode-android` 생성 후 origin URL 연결
  - Gradle 프로젝트 생성 시 applicationId/namespace 값 일치 확인

## 2026-05-22

- 작업: Android 충전 거치대 시계·달력 앱 DockMode 설계 묶음 작성
- 변경 파일:
  - AGENTS.md: 기존 범용 에이전트 규칙을 DockMode 프로젝트 설정값과 Android 고유 정책으로 통합
  - README.md: 프로젝트 개요, 기능 범위, 문서 구조 작성
  - SPEC.md: 제품 요구사항, 사용자 시나리오, 화면 정의, 성공 기준 작성
  - TECH_SPEC.md: Kotlin, Compose, DreamService, BatteryManager, Calendar Provider 기반 기술 설계 작성
  - TASKS.md: 마일스톤별 작업 목록과 완료 기준 작성
  - GOALS.md: 에이전트 코딩용 3단계 Goal 프롬프트 작성
  - DECISIONS.md: 주요 기술 결정 기록
  - PRIVACY.md: 권한과 개인정보 처리 정책 작성
  - RELEASE.md: Android 릴리즈 검증 절차 작성
  - CHANGELOG.md: 초기 변경 사항 기록
  - .github/workflows/android-ci.yml: Android CI 템플릿 작성
- 검증:
  - 문서 파일 생성 확인
  - ZIP 묶음 생성 확인
- 결과: 설계 묶음 생성 완료
- 후속 작업:
  - 실제 Android 프로젝트 생성 후 Gradle 명령 실행 검증
  - Play Store 출시 전 앱명/상표/스토어 정책 검토
  - 실기기에서 DreamService 동작 검증
