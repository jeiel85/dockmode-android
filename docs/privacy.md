---
title: DockMode 개인정보 처리방침
description: DockMode 앱의 권한 사용, 캘린더 데이터 처리, 네트워크 정책에 대한 공개 안내입니다.
permalink: /privacy/
---

# DockMode 개인정보 및 권한 정책

> 이 문서는 Play Console 등록 시 "개인정보처리방침 URL"로 입력되는 공개 페이지입니다.
> 정식 URL: <https://jeiel85.github.io/dockmode-android/privacy/>
>
> 저장소 내부 사본은 [PRIVACY.md](https://github.com/jeiel85/dockmode-android/blob/main/PRIVACY.md)에 있으며, 본 페이지와 동기화 상태로 유지합니다.

## 1. 기본 원칙

DockMode는 로컬 우선 앱입니다. 앱의 핵심 기능은 인터넷 연결 없이 동작합니다.

- 광고, 분석, 로그인, 결제, 추적 SDK가 포함되지 않습니다.
- 네트워크 권한(`INTERNET`, `ACCESS_NETWORK_STATE` 등)을 요청하지 않습니다.
- 사용자 데이터를 외부 서버로 전송하지 않습니다.

## 2. 권한 목록

| 권한 | 사용 시점 | 목적 | 필수 여부 |
|---|---|---|---|
| `READ_CALENDAR` | 사용자가 일정 표시 기능을 켤 때 | 오늘 일정과 다음 일정 표시 | 선택 |
| `BIND_DREAM_SERVICE` | DreamService 등록 | Android 스크린세이버 제공 | 시스템 바인딩용 |

## 3. 캘린더 데이터 처리

앱은 다음 항목만 UI 표시 목적으로 읽습니다.

- 이벤트 ID
- 이벤트 제목
- 시작 시간
- 종료 시간
- 종일 일정 여부

기본 정책:

- 캘린더 이벤트 원본 데이터를 별도 DB나 파일에 저장하지 않습니다.
- 이벤트 제목과 시간은 화면 표시를 위한 메모리 상태로만 사용합니다.
- 로그에 이벤트 제목, 참석자, 위치, 설명, 메모를 남기지 않습니다.
- 캘린더 위치, 설명, 참석자, 알림 데이터는 v1.0 범위에서 사용하지 않습니다.

## 4. 네트워크 정책

v1.0 범위에서는 네트워크 권한을 추가하지 않습니다.

날씨, 클라우드 동기화, 외부 캘린더 API, 로그인 기능은 별도 승인과 개인정보 설계 후에 추가합니다.

## 5. 권한 요청 UX

- 앱 시작 직후 권한을 요청하지 않습니다.
- 사용자가 일정 표시 기능을 켤 때 권한이 필요한 이유를 먼저 설명합니다.
- 사용자가 권한을 거부해도 시계, 날짜, 충전 상태 기능은 계속 동작합니다.
- "다시 묻지 않음" 상태에서는 시스템 설정으로 이동하는 안내를 제공합니다.

## 6. 데이터 삭제

DockMode는 외부 저장소에 사용자 데이터를 보관하지 않습니다. 모든 앱 상태는 디바이스의 앱 데이터 영역(`/data/data/io.jeiel85.dockmode/`)에만 존재하며, 앱을 제거하면 해당 데이터도 함께 제거됩니다.

별도의 외부 데이터 삭제 요청 절차는 제공하지 않습니다.

## 7. 문의

- GitHub Issues: <https://github.com/jeiel85/dockmode-android/issues>
- 정책 변경 이력은 저장소의 [CHANGELOG.md](https://github.com/jeiel85/dockmode-android/blob/main/CHANGELOG.md)와 [HISTORY.md](https://github.com/jeiel85/dockmode-android/blob/main/HISTORY.md)에서 확인할 수 있습니다.

---

본 문서는 v0.1 구현 기준입니다. 광고, 분석, 네트워크, 계정 기능이 추가되는 경우 본 페이지를 먼저 갱신한 뒤 새 권한을 추가합니다.
