---
title: DockMode
description: Android 충전 거치대용 시계와 일정 대시보드 앱
---

# DockMode

> Android 스마트폰을 충전 거치대에 올렸을 때 가로 화면으로 시간, 날짜, 오늘 일정을 보여 주는 네이티브 앱입니다.
>
> Kotlin · Jetpack Compose · DreamService · Android Calendar Provider 기반.

[GitHub 저장소 보기](https://github.com/jeiel85/dockmode-android){: .btn}
[CI 워크플로](https://github.com/jeiel85/dockmode-android/actions){: .btn}
[릴리즈](https://github.com/jeiel85/dockmode-android/releases){: .btn}

---

## 핵심 가치

- 충전 거치대에서 바로 보이는 대형 시계와 일정 정보
- 야간에도 눈부심이 적은 다크 모드와 OLED 번인 방지
- 네트워크 권한 없이 동작하는 로컬 우선 구조
- Android `DreamService`로 제공하는 시스템 표준 스크린세이버 모드

## 화면 구성

| 화면 | 역할 |
|---|---|
| HomeScreen | 충전 상태, 캘린더 권한, 스크린세이버 설정 안내 |
| StandbyActivity | 가로 전체화면 시계·일정 대시보드 |
| StandbyDreamService | Android 스크린세이버에서 선택하는 자동 표시 모드 |
| SettingsScreen | 시계 스타일, 일정 표시, 야간 모드, 번인 방지, 화면 켜짐 유지 |

## 기술 스택

- Kotlin 2.0
- Jetpack Compose (BOM 2024.10.01)
- AndroidX Lifecycle + ViewModel + DataStore
- Calendar Provider (`READ_CALENDAR`)
- BatteryManager `ACTION_BATTERY_CHANGED`
- DreamService (`BIND_DREAM_SERVICE`)
- Gradle Kotlin DSL 8.10, AGP 8.7
- GitHub Actions 기반 CI

## 권한과 개인정보

- `READ_CALENDAR`: 일정 표시를 사용자가 켤 때만 요청합니다. 캘린더 데이터는 화면 표시 외 용도로 저장하지 않으며, 외부로 전송하지 않습니다.
- 네트워크 권한은 v1.0 범위에서 추가하지 않습니다.
- 광고, 분석, 로그인, 결제, 원격 설정, crash reporting SDK는 사용하지 않습니다.

자세한 내용은 [PRIVACY.md](https://github.com/jeiel85/dockmode-android/blob/main/PRIVACY.md)를 참고하세요.

## 더 알아보기

- [제품 정의 (SPEC.md)](https://github.com/jeiel85/dockmode-android/blob/main/SPEC.md)
- [기술 설계 (TECH_SPEC.md)](https://github.com/jeiel85/dockmode-android/blob/main/TECH_SPEC.md)
- [작업 목록 (TASKS.md)](https://github.com/jeiel85/dockmode-android/blob/main/TASKS.md)
- [의사결정 로그 (DECISIONS.md)](https://github.com/jeiel85/dockmode-android/blob/main/DECISIONS.md)
- [릴리즈 절차 (RELEASE.md)](https://github.com/jeiel85/dockmode-android/blob/main/RELEASE.md)
- [변경 이력 (CHANGELOG.md)](https://github.com/jeiel85/dockmode-android/blob/main/CHANGELOG.md)

## 상태

현재 v0.1 단계로 수동 대기 화면, 캘린더 권한 흐름, DreamService, 기본 설정 화면이 구현되어 있습니다. Play Store 출시 전 앱명 `DockMode`의 상표·중복 검토가 별도 작업으로 남아 있습니다.
