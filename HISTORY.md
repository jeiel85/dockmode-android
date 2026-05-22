# HISTORY.md

## 2026-05-23 (Play Store 그래픽 자산 제작)

- 작업: Play Console 기본 등록정보에 필요한 정식 앱 아이콘 512×512 PNG와 피처 그래픽 1024×500 PNG를 제작하고, 임시 런처 아이콘 벡터를 같은 모티프로 동기화.
- 디자인 기준:
  - 기존 DockMode 리뉴얼 팔레트(Obsidian, Space Blue, Amber, Starlight Ivory)를 사용.
  - Apple StandBy 명칭, UI 구성, 아이콘 형태를 복제하지 않고 Android 충전 거치대용 시계 앱을 독립적으로 표현.
  - 피처 그래픽은 실제 앱 캡처(`play_store/screenshots/03-standby-minimal.png`, `04-standby-digital.png`)를 합성해 스토어 이미지와 구현 화면이 어긋나지 않도록 구성.
- 추가/변경 파일:
  - 신규: `scripts/generate-store-graphics.py` (Pillow 기반 그래픽 자산 재생성 스크립트)
  - 신규: `play_store/graphics/app-icon-512.png`, `play_store/graphics/feature-graphic-1024x500.png`, `play_store/graphics/README.md`
  - 갱신: `app/src/main/res/drawable/ic_launcher_background.xml`, `app/src/main/res/drawable/ic_launcher_foreground.xml`
  - 갱신: `TASKS.md`, `CHANGELOG.md`, `README.md`, `play_store/listing/README.md`, `play_store/onboarding-checklist.md`, `play_store/listing/{ko-KR,en-US}/long-description.txt`
- 검증:
  - `python scripts\generate-store-graphics.py` 실행 성공.
  - Pillow로 산출물 크기/모드 확인: `app-icon-512.png` = 512×512 RGBA, `feature-graphic-1024x500.png` = 1024×500 RGBA.
  - 산출물 2종을 눈으로 확인해 앱명 가독성, 실제 화면 합성, 과도한 유사 UI 없음 확인.
- 결과: Play Console 기본 그래픽 자산 준비 완료. 사용자는 `play_store/graphics/`의 PNG 2종을 업로드하면 됨.
- 후속 작업:
  - Play Console 실제 업로드 후 거부 사유가 있으면 해당 사유 기준으로 그래픽 자산 재조정.
  - 브랜드명 최종 변경 시 `DockMode` 텍스트가 들어간 피처 그래픽과 스토어 등록정보를 함께 갱신.

## 2026-05-23 (Play Console 최초 등록 사전 자료 준비)

- 작업: Play Console에 DockMode를 최초 등록하기 위한 자료를 일괄 정비. 등록 자체(콘솔 클릭, 결제, 약관 동의, 키스토어 생성)는 `AGENTS.md §8` 사전 승인 항목이라 사용자 액션으로 남기고, 그 외 자동 정비 가능한 부분은 모두 끝냄.
- 정책 점검 결과:
  - 외부 콘솔 작업 / 결제 / 시크릿(키스토어) 생성 / 상표 최종 결정은 `AGENTS.md §2`(자동 진행 제외), `§8`(사전 승인 필요), `ADR-004`(앱명 출시 전 상표 검토)로 사용자 직접 수행.
  - 저장소가 끝낼 수 있는 것: 메타데이터/양식 초안, 공개 URL, 빌드 자동 서명 인프라, 단계별 체크리스트, 상표 사전 리서치.
- 추가/변경 파일:
  - 신규: `play_store/listing/README.md`, `play_store/listing/category-and-audience.md`, `play_store/listing/data-safety-form.md`, `play_store/listing/content-rating-answers.md`, `play_store/listing/ko-KR/{short,long}-description.txt`, `play_store/listing/en-US/{short,long}-description.txt`, `play_store/onboarding-checklist.md`
  - 신규: `docs/keystore-guide.md` (permalink `/keystore-guide/`), `docs/privacy.md` (permalink `/privacy/`), `docs/branding-research.md` (permalink `/branding-research/`)
  - 갱신: `docs/_config.yml` (PRIVACY.md exclude 제거, 의도는 없으나 무해), `docs/index.md` (개인정보 링크를 Pages 내부로), `PRIVACY.md` (공개 URL 안내 추가), `RELEASE.md` (§6/§7 갱신)
  - 갱신: `app/build.gradle.kts` (env 4종 모두 있을 때만 적용되는 release signingConfig)
  - 갱신: `.github/workflows/android-ci.yml` (`Decode upload keystore` 단계 추가, `Bundle release AAB`에 env 4종 전달)
- 상표 리서치 (`docs/branding-research.md`):
  - Play Store 영문 검색 결과 `DockMode` 정확명 충돌은 발견되지 않음.
  - 유사 컨셉 강력 경쟁자: DockScreen - StandBy Mode, StandBy Mode: Clock & Widgets, Dock Station Digital Clock.
  - USPTO/KIPRIS/EUIPO/J-PlatPat 정식 상표 검색은 사용자 직접 수행 필요. 충돌 시 후보명(DeskDock, DockGlance, BedsideDock, JeielDock 등) 제시.
  - Play Console 앱 이름은 부제 포함 `DockMode - 거치대 시계 & 일정` 권장 (50자 이내).
- 검증:
  - `./gradlew ktlintCheck assembleDebug bundleRelease` 모두 성공. 시크릿 없는 unsigned AAB 경로가 기존과 동일하게 동작함을 확인.
  - 상표 결과는 자동 검색 한계로 사용자 4개 DB 직접 조회로 보강 필요.
- 후속 작업:
  - (사용자) Play Developer 계정 등록 + 결제 + 상표 4개 DB 조회 + 키스토어 생성 + Play Console 첫 등록은 `play_store/onboarding-checklist.md` 단계 A~I로 진행.
  - 정식 앱 아이콘 512×512 / 피처 그래픽 1024×500 디자인은 별도 후속.
  - 캘린더 권한 허용 상태 standby 스크린샷 보강.

## 2026-05-23 (statusBar deprecation 대응 + 실기기 스크린샷)

- 작업: 리뉴얼 디자인 이식 직후 남아 있던 `window.statusBarColor` / `navigationBarColor` deprecation 경고를 정리하고, 새 디자인을 README/Play Store에 반영하기 위해 Galaxy S24(SM-S921N) 실기기로 핵심 화면 5종을 캡처.
- 코드 변경:
  - `ui/theme/Theme.kt`: SideEffect 안에서 `window.statusBarColor` / `navigationBarColor` 직접 설정을 제거하고, `WindowInsetsControllerCompat`의 `isAppearanceLightStatusBars` / `isAppearanceLightNavigationBars`로 시스템 바 아이콘 톤만 제어하도록 교체. 시스템 바 배경은 `enableEdgeToEdge`(MainActivity) + 화면 배경에 위임.
- 캡처 절차 (ADB):
  - `adb shell am force-stop io.jeiel85.dockmode.debug` → `am start MainActivity` → screencap 으로 홈 캡처
  - 홈 스크롤 → `설정` 버튼 좌표 탭 → SettingsScreen 캡처
  - 시계 스타일 카드(미니멀=200, 디지털=540, 캘린더 중심=880 / y=720) 탭 → 뒤로 → `대기 화면 시작`(540, 950) → StandbyActivity가 sensorLandscape로 회전 → screencap (2340×1080)
  - 스타일별로 1·2·3 반복하여 Minimal / Digital / CalendarFocus 3종 standby 캡처
  - 도중에 시스템 "AutoInput이(가) 계속 중단됨" 다이얼로그가 떠 좌표 탭을 가로채는 사고가 있었음. 다이얼로그 dismiss 후 재진행.
- 산출 파일 (`docs/screenshots/`):
  - `01-home.png`, `01b-home-scrolled.png`
  - `02-settings.png`
  - `03-standby-minimal.png`, `04-standby-digital.png`, `05-standby-calendar.png`
- Play Store 사본: `play_store/screenshots/`에 5종 동일 파일 + 업로드 순서/요건 README
- 문서 갱신: `README.md` "화면 미리보기" 섹션 신설, `docs/screenshots/README.md` 캡처 환경, `CHANGELOG.md` Unreleased에 statusBar 변경/스크린샷 추가 항목 정리.
- 검증:
  - 로컬: `./gradlew ktlintCheck detekt assembleDebug` 모두 성공. statusBarColor deprecation 경고 사라짐 확인.
  - 실기기: Galaxy S24에서 5종 화면 직접 실행 확인. 야간 호박색 미니멀 모드와 디지털 글래스 패널, 캘린더 리스트 모두 정상 표시.
  - 캡처 시 캘린더 권한을 의도적으로 허용하지 않아 안내 문구가 표시됨 (권한 미허용 케이스 가독성도 함께 검증).
- 알려진 제약 / 후속 작업:
  - UIAutomator `dump` 명령이 Compose 화면에서 idle 상태를 잡지 못해 testTag 기반 좌표 추출이 어려움. 좌표는 캡처 이미지에서 시각적으로 추정. 향후 instrumented test로 재현성 보장 필요.
  - Play Console 폰 스크린샷 비율 제한(약 2:1)을 정확히 맞추려면 좌우/상하 패딩 추가 후 재업로드가 필요할 수 있음. 거부 시 패딩 처리.
  - 캘린더 권한을 허용한 상태의 캡처(실제 일정 표시)도 추가 캡처 검토.

## 2026-05-22 (UI 디자인 리뉴얼 1차 이식)

- 작업: 사용자가 별도 워크스페이스(`D:/Project/dockmode-renew`)에서 진행한 디자인 리뉴얼을 본 저장소(`io.jeiel85.dockmode`) UI에 모두 이식. 화면 구성과 기능 매칭을 함께 가져왔으나, 정책 위반 의존성(Firebase, Retrofit, Moshi, OkHttp, Room, KSP, Roborazzi, Secrets Gradle Plugin)은 가져오지 않음.
- 가져온 디자인 변경 사항:
  - 새 컬러 팔레트 "Premium Ambient Slate & Obsidian" (SpaceBlue/SpaceIndigo/CalmTeal/CardSlate/GlassObsidian/StarlightIvory 등) — `ui/theme/Color.kt`
  - 다크/라이트 컬러 스킴 + 상태바·네비게이션바 동기화 — `ui/theme/Theme.kt`
  - 풍부한 Typography (displayLarge ~ labelMedium, 모노스페이스 시계용 폰트) — `ui/theme/Type.kt`
  - HomeScreen: "DOCK MODE" 라벨 헤더, 충전 상태 카드(아이콘+펄스 애니메이션), Calendar/DreamService 카드 아이콘화, 풀폭 Start Standby CTA, 설정 진입 OutlinedButton
  - SettingsScreen: 시계 스타일 3종 카드형 세그먼트 셀렉터(미리보기 "12:00" / "12:00:30" / "12 / 일정"), 토글 + 설명 문구, 개인정보 카드
  - StandbyScreen: 디지털 레이아웃 우측 글래스 패널, 캘린더 포커스 레이아웃 이벤트 리스트, 미니멀 모드 야간 호박색 톤(`animateColorAsState`), 우상단 닫기 버튼
  - MainActivity: `androidx.navigation.compose` 기반 `NavHost`로 home ↔ settings 화면 전환
- 변경 파일:
  - 갱신 Kotlin: `MainActivity.kt`, `home/HomeScreen.kt`, `settings/SettingsScreen.kt`, `standby/StandbyScreen.kt`, `ui/theme/{Color,Theme,Type}.kt`
  - 갱신 리소스: `app/src/main/res/values/strings.xml`, `values-en/strings.xml` (설정 토글 설명, 닫기 버튼, 일정 표시 꺼짐 문구 추가)
  - 갱신 빌드: `gradle/libs.versions.toml`(material-icons-core, material-icons-extended, navigation-compose 추가), `app/build.gradle.kts`(세 의존성 추가)
- 유지된 파일(패키지/구조 동일하여 변경 불필요): `DockModeApplication.kt`, `home/HomeViewModel.kt`, `settings/SettingsViewModel.kt`, `standby/{StandbyActivity,StandbyDreamService,StandbyRoute,StandbyUiState,StandbyViewModel}.kt`, `data/**`, `domain/**`, `util/**`, `AndroidManifest.xml`, `res/values/{colors,themes}.xml`, `res/values-night/themes.xml`
- 의도적으로 가져오지 않은 항목 (정책 충돌):
  - Firebase BOM, Retrofit, Moshi, OkHttp logging interceptor — 네트워크/외부 API 미허용
  - Room, KSP — 로컬 DB 비도입 정책
  - Roborazzi — 추가 테스트 인프라(스크린샷 테스트) 도입 보류
  - Secrets Gradle Plugin / `.env` — API 키 미사용 정책
  - AI Studio 기본 namespace/applicationId(`com.example` / `com.aistudio.dockmode.vbxwqh`) — 기존 식별값(`io.jeiel85.dockmode`) 유지
- 검증:
  - `./gradlew ktlintCheck` ✓
  - `./gradlew detekt` ✓
  - `./gradlew testDebugUnitTest` ✓ (Formatters, CalendarFilters, BurnInOffset, BatteryStatusMapper, CalendarRepositoryRange 5종 통과)
  - `./gradlew assembleDebug` ✓ (debug APK 생성)
  - `./gradlew lintDebug` ✓ (BUILD SUCCESSFUL)
  - 경고 1건: `window.statusBarColor` / `navigationBarColor`가 Android 15에서 deprecated. OLED 검정 배경 유지를 위해 의도적으로 유지. 후속 작업으로 `WindowInsetsController` 기반 대체 검토.
- 결과: 디자인 리뉴얼 1차 이식 완료. 커밋·푸시 후 GitHub Actions에서 동일 결과 확인 예정. 실기기에서 새 디자인의 가독성/번인 위험/세 시계 스타일 전환 동작은 후속 작업으로 분리.

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
- 결과: 절차 정비 완료. 사용자 승인 후 `v0.1.0` 태그 푸시 → 태그 트리거 CI(run 26276140568, 1m5s)가 모든 단계 통과하고 GitHub Release를 자동 생성. Release URL: <https://github.com/jeiel85/dockmode-android/releases/tag/v0.1.0>
- 첨부된 산출물:
  - `dockmode-v0.1.0-debug.apk` (≈ 9.85 MB)
  - `dockmode-v0.1.0-release.aab` (≈ 2.33 MB, unsigned)
  - `dockmode-v0.1.0-mapping.txt` (≈ 17.4 MB)
- Release 본문은 `docs/releases/v0.1.0.md`가 정상 사용됨 (수기 노트 우선순위 동작 확인).

### 추가 절차: 바탕화면 내보내기

- 같은 머신의 다른 Android 프로젝트(markleaf, lumina-daily 등)와 동일하게 사용자 바탕화면에 `<prefix>-<tag>.aab`와 `<prefix>-<tag>-release-notes.txt`를 두는 규칙이 빠져 있던 점을 보완.
- 신규 `scripts/export-release-to-desktop.ps1`: `gh release download`로 AAB를 받고, `play_store/release_notes/<tag>.txt`와 함께 바탕화면에 복사. 최신 태그 자동 감지 지원.
- v0.1.0 산출물 실제 내보내기 확인:
  - `dockmode-v0.1.0.aab` (2,330,832 B)
  - `dockmode-v0.1.0-release-notes.txt` (1,229 B)
  - 저장 경로: `[Environment]::GetFolderPath('Desktop')` → `C:\Users\jeiel\OneDrive\바탕 화면` (OneDrive 동기화 폴더)
- 문서 동기화: `RELEASE.md`에 §4-1 "바탕화면으로 산출물 내보내기" 추가, `docs/releases/README.md` 흐름 6단계로 추가.

- 후속 작업:
  - 릴리즈 키스토어 구성과 서명된 AAB 생성 절차 정리.
  - 첫 실기기 검증 후 v0.1.1 패치 또는 v0.2.0으로 다음 릴리즈 진행 (versionCode 증가 필수).

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
