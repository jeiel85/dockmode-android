# DockMode

[![Android CI](https://github.com/jeiel85/dockmode-android/actions/workflows/android-ci.yml/badge.svg?branch=main)](https://github.com/jeiel85/dockmode-android/actions/workflows/android-ci.yml)
[![GitHub Pages](https://img.shields.io/badge/landing-jeiel85.github.io%2Fdockmode--android-2ea44f)](https://jeiel85.github.io/dockmode-android/)
[![Latest Release](https://img.shields.io/github/v/release/jeiel85/dockmode-android?include_prereleases&label=release)](https://github.com/jeiel85/dockmode-android/releases)
[![License](https://img.shields.io/badge/license-TBD-lightgrey)](#라이선스)

Android 충전 거치대용 시계·달력 대시보드 앱입니다.

DockMode는 스마트폰을 책상, 침대 옆, 주방, 사무실 충전 스탠드에 올려둘 때 가로 화면으로 시간, 날짜, 충전 상태, 오늘 일정을 보기 좋게 보여 줍니다. 구현은 Kotlin + Jetpack Compose 기반 Android 네이티브이며, 자동 대기 화면 경험은 Android `DreamService`를 통해 제공합니다.

> 현재 `DockMode`는 작업용 앱명입니다. Play Store 정식 출시 전 상표, 중복 앱명, 스토어 정책 검토를 별도로 완료해야 합니다.

- 랜딩 페이지: <https://jeiel85.github.io/dockmode-android/>
- 개인정보 처리방침: <https://jeiel85.github.io/dockmode-android/privacy/>
- 릴리즈: <https://github.com/jeiel85/dockmode-android/releases>

## 앱 정체성

- 충전 거치대에서 바로 보이는 대형 시계와 날짜
- 오늘 일정과 다음 일정을 빠르게 확인하는 로컬 캘린더 대시보드
- 야간에도 눈부심이 적은 어두운 화면과 OLED 번인 방지 위치 이동
- Android 정책을 우회하지 않는 수동 실행 + 스크린세이버 모드
- 네트워크 권한, 광고, 분석, 로그인 없는 로컬 우선 구조

## 화면 미리보기

| Home | Settings |
|---|---|
| <img src="docs/screenshots/01-home.png" alt="DockMode 홈 화면" width="260"/> | <img src="docs/screenshots/02-settings.png" alt="DockMode 설정 화면" width="260"/> |

| Minimal | Digital | Calendar Focus |
|---|---|---|
| <img src="docs/screenshots/03-standby-minimal.png" alt="미니멀 대기 화면" width="300"/> | <img src="docs/screenshots/04-standby-digital.png" alt="디지털 대기 화면" width="300"/> | <img src="docs/screenshots/05-standby-calendar.png" alt="캘린더 중심 대기 화면" width="300"/> |

캡처 원본은 [docs/screenshots/](docs/screenshots/)에, Play Store 업로드용 사본은 [play_store/screenshots/](play_store/screenshots/)에 있습니다.

## 현재 구현

| 영역 | 상태 |
|---|---|
| 수동 대기 화면 | `StandbyActivity`, 가로 전체화면, 시스템 바 숨김, 화면 켜짐 유지 |
| 스크린세이버 | `StandbyDreamService`, Android DreamService 등록 |
| 시계 스타일 | Minimal, Digital, CalendarFocus 3종 |
| 일정 표시 | `READ_CALENDAR` 허용 시 Android Calendar Provider로 오늘 일정 조회 |
| 충전 상태 | `ACTION_BATTERY_CHANGED` sticky broadcast 기반 감지 |
| 설정 저장 | Jetpack DataStore Preferences |
| 다국어 | 한국어 기본, 영어 필수 리소스 |
| 품질 확인 | ktlint, detekt, unit test, debug APK, release AAB GitHub Actions |

자세한 진행 상태는 [TASKS.md](TASKS.md)를 참고하세요.

## Play Store 준비 자료

| 항목 | 파일 |
|---|---|
| 앱 아이콘 512 x 512 | [play_store/graphics/app-icon-512.png](play_store/graphics/app-icon-512.png) |
| 피처 그래픽 1024 x 500 | [play_store/graphics/feature-graphic-1024x500.png](play_store/graphics/feature-graphic-1024x500.png) |
| 휴대전화 스크린샷 | [play_store/screenshots/](play_store/screenshots/) |
| 한국어/영어 등록정보 | [play_store/listing/](play_store/listing/) |
| 개인정보 처리방침 URL | <https://jeiel85.github.io/dockmode-android/privacy/> |
| 온보딩 체크리스트 | [play_store/onboarding-checklist.md](play_store/onboarding-checklist.md) |

그래픽 자산은 `scripts/generate-store-graphics.py`로 재생성할 수 있습니다.

```powershell
python scripts\generate-store-graphics.py
```

## 프로젝트 식별값

| 항목 | 값 |
|---|---|
| Project Code | `DKM-ANDROID` |
| Repository ID | `dockmode-android` |
| Repository URL | `https://github.com/jeiel85/dockmode-android.git` |
| Android Application ID | `io.jeiel85.dockmode` |
| Android Namespace | `io.jeiel85.dockmode` |
| Artifact Prefix | `dockmode` |
| Min SDK | 26 |
| Compile SDK | 35 |

## 기술 스택

| 영역 | 선택 |
|---|---|
| 언어 | Kotlin 2.0 |
| UI | Jetpack Compose, Material 3 |
| 아키텍처 | MVVM + Repository + StateFlow |
| 설정 저장 | Jetpack DataStore Preferences |
| 일정 데이터 | Android Calendar Provider |
| 충전 상태 | BatteryManager |
| 스크린세이버 | DreamService |
| 빌드 | Gradle Kotlin DSL, AGP 8.7 |
| 정적 분석 | ktlint, detekt |
| CI | GitHub Actions |

## 빠른 시작

사전 조건:

- JDK 17
- Android SDK Platform 35 / Build-Tools 35
- Android Studio Koala 이상 권장

```bash
git clone https://github.com/jeiel85/dockmode-android.git
cd dockmode-android
./gradlew assembleDebug
```

자주 쓰는 검증 명령:

```bash
./gradlew ktlintCheck
./gradlew detekt
./gradlew testDebugUnitTest
./gradlew assembleDebug
./gradlew bundleRelease
```

## 권한과 개인정보

- `READ_CALENDAR` 권한은 사용자가 일정 표시 기능을 켤 때만 요청합니다.
- 캘린더 이벤트 제목과 시간은 화면 표시 목적 외 저장하지 않습니다.
- 네트워크 권한, 광고 SDK, 분석 SDK, crash reporting SDK, 로그인 기능은 포함하지 않습니다.
- 공개 개인정보 처리방침은 [GitHub Pages 개인정보 처리방침](https://jeiel85.github.io/dockmode-android/privacy/)입니다.

## 문서

| 파일 | 역할 |
|---|---|
| [SPEC.md](SPEC.md) | 제품 요구사항과 사용자 흐름 |
| [TECH_SPEC.md](TECH_SPEC.md) | 기술 아키텍처와 컴포넌트 설계 |
| [TASKS.md](TASKS.md) | 마일스톤, 완료 기준, 후속 작업 |
| [DECISIONS.md](DECISIONS.md) | 기술적 결정과 근거 |
| [RELEASE.md](RELEASE.md) | 빌드, 서명, 릴리즈 절차 |
| [CHANGELOG.md](CHANGELOG.md) | 사용자에게 공개 가능한 변경 요약 |
| [HISTORY.md](HISTORY.md) | 작업 과정, 검증, 결과 기록 |
| [AGENTS.md](AGENTS.md) | AI 코딩 에이전트 작업 규칙 |

## 주의 사항

DockMode는 충전 연결만으로 백그라운드에서 Activity를 강제 실행하지 않습니다. 자동 대기 화면은 사용자가 Android 스크린세이버로 선택한 `DreamService` 흐름을 따릅니다. 날씨, 외부 캘린더 API, 계정 로그인, 광고, 분석은 별도 승인 전까지 추가하지 않습니다.

## 라이선스

라이선스는 아직 지정되지 않았습니다. 외부에서 코드를 재사용하기 전에 저장소 소유자에게 문의하세요.
