# DECISIONS.md

# DockMode Decision Log

## ADR-001: Flutter 대신 Kotlin + Jetpack Compose 사용

날짜: 2026-05-22

### 결정

DockMode는 Android 네이티브 Kotlin + Jetpack Compose로 구현한다.

### 이유

- DreamService, Calendar Provider, BatteryManager, 화면 켜짐 유지 같은 Android 플랫폼 API 연동이 핵심이다.
- UI 자체보다 시스템 연동 안정성이 더 중요하다.
- Compose는 Android 공식 최신 UI 툴킷으로 유지보수성이 높다.

### 영향

- iOS 버전은 별도 프로젝트로 분리한다.
- Flutter 공유 UI의 장점은 포기한다.

---

## ADR-002: 자동 실행은 DreamService 중심으로 구현

날짜: 2026-05-22

### 결정

충전 연결을 감지해 Activity를 강제로 띄우는 방식은 구현하지 않는다. 자동 대기 화면 경험은 Android DreamService를 중심으로 제공한다.

### 이유

- 최신 Android는 백그라운드 Activity 시작을 제한한다.
- 사용자가 예측하지 못한 전체화면 전환은 UX와 정책 측면에서 위험하다.
- DreamService는 Android가 제공하는 스크린세이버 확장 지점이다.

### 영향

- 사용자는 Android 설정에서 DockMode를 스크린세이버로 선택해야 한다.
- 앱 내부에 설정 안내 화면이 필요하다.

---

## ADR-003: 캘린더는 Calendar Provider만 사용

날짜: 2026-05-22

### 결정

v1.0까지는 Google Calendar REST API를 사용하지 않고 Android Calendar Provider만 사용한다.

### 이유

- 로컬 캘린더 저장소를 읽는 것만으로 오늘 일정 표시 요구사항을 충족한다.
- 로그인, OAuth, 네트워크 권한, 외부 API 정책 부담을 줄인다.
- 개인정보 처리 범위를 최소화할 수 있다.

### 영향

- 기기 캘린더에 동기화된 일정만 표시한다.
- 계정별 고급 필터는 후속 작업으로 분리한다.

---

## ADR-004: 앱명 DockMode는 작업용 이름으로 사용

날짜: 2026-05-22

### 결정

문서와 초기 구현에서는 `DockMode`를 작업용 앱명으로 사용한다.

### 이유

- 충전 거치대 대기 모드라는 앱 정체성이 명확하다.
- Android 앱으로서 Apple StandBy 명칭을 직접 사용하지 않는다.

### 영향

- 출시 전 Play Store 중복 명칭, 상표, 앱 아이콘 유사성 검토가 필요하다.

---

## ADR-005: 프로젝트 코드와 레포 아이디 확정

날짜: 2026-05-22

### 결정

초기 구현 기준 프로젝트 코드는 `DKM-ANDROID`, GitHub Repository ID는 `dockmode-android`, Android applicationId와 namespace는 `io.jeiel85.dockmode`로 사용한다.

### 이유

- `dockmode-android`는 Android 전용 저장소임을 명확히 드러낸다.
- `DKM-ANDROID`는 이슈, 브랜치, 작업 로그에서 프로젝트를 짧고 명확하게 구분할 수 있다.
- `io.jeiel85.dockmode`는 사용자 GitHub 핸들을 기반으로 한 Android 식별자이며, `com` 도메인 소유 여부를 전제하지 않는다.

### 영향

- 에이전트는 Gradle `namespace`, `applicationId`, Kotlin package path, CI 산출물 이름을 `PROJECT_ID.md` 기준으로 맞춘다.
- 실제 GitHub 저장소 생성 전에는 Repository URL이 예약값이라는 점을 작업 보고에 명시한다.
