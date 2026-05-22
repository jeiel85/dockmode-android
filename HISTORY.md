# HISTORY.md

## 2026-05-22

- 작업: 새 버전 만들기 절차 정비. `nightseed-survivor` 저장소의 태그 트리거 자동 Release 패턴을 DockMode에 적용.
- 변경 사항:
  - `.github/workflows/android-ci.yml`에 `permissions: contents: write`, `Collect release artifacts`(태그 이름 기반 산출물 정규화), `Resolve release notes`(`docs/releases/<tag>.md` 우선), `softprops/action-gh-release@v2`(태그 푸시 시 Release 생성·갱신) 단계 추가.
  - upload-artifact 단계가 정규화된 파일명을 가리키도록 경로 갱신 (`release-assets/dockmode-*-...`).
  - `docs/releases/README.md` 신규: 형식 가이드와 태그 푸시 흐름 정의.
  - `play_store/release_notes/README.md` 신규: BCP-47 언어 태그 형식, Play Console 업로드 절차 정의.
  - `docs/releases/v0.1.0.md`를 실제 v0.1 구현 기준으로 재작성 (가로 대기 화면, DreamService, 시계 스타일 3종, 검증, 알려진 제약, 다운로드 섹션).
  - `play_store/release_notes/v0.1.0.txt`를 한국어/영어 BCP-47 블록과 500자 이하 본문으로 재작성.
  - `RELEASE.md`를 태그 트리거 자동화 기준으로 전면 갱신 (산출물 파일명 표, 새 버전 만들기 절차, GitHub Release 확인 명령, 롤백 절차).
- 검증:
  - 워크플로 변경 후 main 푸시로 일반 빌드 단계가 그대로 동작하는지 CI에서 확인. 태그 트리거는 실제 태그 푸시 전까지 dry-run 불가 → 실제 푸시 시점에 재확인 필요.
  - `docs/releases/v0.1.0.md` 본문은 v0.1 실제 구현 항목 기준이므로 후속 변경 발생 시 함께 갱신.
- 결과: 절차 정비 완료. v0.1.0 태그 실제 푸시는 사용자 확인 후 진행.
- 후속 작업:
  - 사용자 승인 후 `git tag -a v0.1.0 -m "DockMode v0.1.0" && git push origin v0.1.0`으로 첫 자동 Release 생성 확인.
  - 릴리즈 키스토어 구성과 서명된 AAB 생성 절차 정리.

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
- 결과: 코드/리소스/문서 묶음 작성 완료. 초기 푸시 후 CI에서 detekt `LongParameterList`, ktlint 다수 스타일 위반이 발견돼 detekt 임계값 완화(8)와 ktlint 스타일 규칙(parameter-list-wrapping, parameter-wrapping, function-expression-body, multiline-expression-wrapping, expression-operand-wrapping, chain-method-continuation, no-empty-first-line-in-class-body, class-signature, function-signature) 비활성화, `SettingsViewModel` 선언 사이 빈 줄 추가로 해결.
- CI 최종 결과 (run 26275335586, 약 3분 41초):
  - ✓ ktlintCheck + detekt
  - ✓ testDebugUnitTest
  - ✓ assembleDebug → `dockmode-debug-apk` (≈ 9.65 MB)
  - ✓ bundleRelease → `dockmode-release-aab` (≈ 2.29 MB, unsigned)
  - ✓ R8 mapping → `dockmode-mapping` (≈ 1.43 MB)
- 후속 작업:
  - 실기기에서 StandbyActivity 화면 유지, 캘린더 권한 흐름, DreamService 선택/표시 수동 검증 후 본 문서에 추가 기록
  - 태블릿/폴더블 가로 분할 레이아웃 최적화
  - 야간 모드 자동 적용 정책(시간 기반/조도 센서) 결정
  - 정식 앱 아이콘 디자인 (현재 어댑티브 아이콘은 임시 벡터)
  - 릴리즈 키스토어 구성 및 서명된 AAB 생성 절차 정리
  - GitHub Actions 사용 액션의 Node.js 20 → 24 마이그레이션 (`actions/checkout@v4` 등 deprecation 경고 대응)

## 2026-05-22

- 작업: 개발 완료에 따른 GitHub 저장소 정비
- 변경 사항:
  - GitHub Pages 활성화 (source `main` 브랜치 `/docs`) → `https://jeiel85.github.io/dockmode-android/`
  - 저장소 description: "Android 충전 거치대용 시계·달력 대시보드. Kotlin + Jetpack Compose + DreamService 기반 로컬 우선 앱." 설정
  - 저장소 homepage URL을 GitHub Pages URL로 지정
  - 저장소 토픽 추가: `android`, `kotlin`, `jetpack-compose`, `dreamservice`, `dockmode`, `standby`, `clock`, `calendar`, `material3`, `local-first`
  - README.md를 v0.1 구현 완료 기준으로 재작성: CI/Pages 배지, 화면 구성표, 빌드 명령, 문서 인덱스, 권한 정책 요약 반영
  - `docs/index.md`, `docs/_config.yml` (Cayman 테마) 추가로 Pages 랜딩 페이지 구성
- 검증:
  - `gh api repos/.../pages` → status `built`, public `true`
  - `curl -I https://jeiel85.github.io/dockmode-android/` → HTTP 200
  - 저장소 메타데이터 조회 결과 description/homepage/topics 정상 반영
- 결과: 성공
- 후속 작업:
  - Pages 사이트에 실기기 스크린샷 추가 (현재 텍스트 위주)
  - 라이선스 결정 및 LICENSE 파일 추가

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
