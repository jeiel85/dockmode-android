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

---

## ADR-006: DreamService에서 ComposeView 호스팅 시 Lifecycle/ViewModelStore/SavedStateRegistry owner를 직접 구현

날짜: 2026-05-22

### 결정

`StandbyDreamService`는 `LifecycleOwner`, `ViewModelStoreOwner`, `SavedStateRegistryOwner`를 직접 구현하고, `ComposeView`에 `setViewTreeLifecycleOwner` / `setViewTreeViewModelStoreOwner` / `setViewTreeSavedStateRegistryOwner`를 설정한 뒤 `ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed`로 컴포지션을 해제한다.

### 이유

- `DreamService`는 `ComponentActivity`가 아니므로 Compose가 기대하는 lifecycle/saved-state owner를 자동으로 제공하지 않는다.
- ViewModel과 `collectAsStateWithLifecycle`을 `StandbyRoute`에서 그대로 재사용하려면 owner가 view tree에 연결돼 있어야 한다.
- Dream 종료 시 컴포지션과 ViewModelStore를 정리해 메모리 누수를 막는다.

### 영향

- DreamService는 lifecycle 이벤트를 수동으로 전달한다 (`onCreate`/`onAttachedToWindow`/`onDetachedFromWindow`/`onDestroy`).
- 향후 `DreamService` 전용 UI 분기를 추가할 때 `StandbyLaunchMode.Dream`을 활용한다.

---

## ADR-007: OLED 번인 방지는 1분 주기 원형 미세 이동으로 구현

날짜: 2026-05-22

### 결정

`BurnInOffset.calculate`는 `nowMillis`에 비례한 위상으로 cos/sin 좌표를 만들어 ±12px 이내의 정수 픽셀 오프셋을 반환한다. `StandbyScreen` 루트 컨테이너에 `Modifier.offset { IntOffset(dx, dy) }`로 적용한다.

### 이유

- 시계와 날짜처럼 장시간 같은 위치에 머무는 정적 요소가 OLED 번인을 일으킬 수 있다.
- 사용자가 인지하기 어려운 정도의 미세 이동만으로 픽셀을 분산시킬 수 있다.
- 별도 애니메이션 라이브러리 없이 매 프레임 단순 좌표만 계산해 비용이 거의 없다.

### 영향

- `burnInGuard` 설정으로 끌 수 있다.
- 추후 시계 위치를 큰 폭으로 이동시키는 별도 “쉬프트” 모드를 추가하려면 `BurnInOffset`을 확장한다.

---

## ADR-008: 의존성 버전은 Compose BOM 2024.10.01 / Kotlin 2.0.21 / AGP 8.7.2를 기준값으로 사용

날짜: 2026-05-22

### 결정

- Android Gradle Plugin 8.7.2
- Kotlin 2.0.21 (Compose Compiler는 `org.jetbrains.kotlin.plugin.compose` 적용)
- Compose BOM 2024.10.01, Material 3 1.3.1
- AndroidX Lifecycle 2.8.7, Activity Compose 1.9.3
- DataStore 1.1.1, kotlinx-coroutines 1.9.0
- ktlint Gradle 12.1.2 (ktlint 1.3.1) / detekt 1.23.7
- Gradle 8.10.2 wrapper, Java target 17

### 이유

- 2026년 초 기준 안정된 최신 조합으로, Kotlin 2.0+ Compose Compiler 플러그인이 별도 버전 매핑 없이 동작한다.
- AGP 8.7+는 Java 17/21 JDK에서 모두 빌드 가능하다.
- BOM 사용으로 Compose 하위 라이브러리 버전을 동기 관리한다.

### 영향

- 향후 Compose/Kotlin 업그레이드 시 BOM과 플러그인 버전을 함께 갱신한다.
- ktlint/detekt 도구 버전은 별도 후속 작업으로 정기 갱신한다.

---

## ADR-009: 외부 디자인 시안(`dockmode-renew`) 이식 정책

날짜: 2026-05-22

### 결정

사용자가 별도 워크스페이스에서 AI Studio 기반으로 작업한 UI 디자인을 본 저장소로 가져올 때, **화면 구성·컬러·타이포그래피·아이콘 사용 패턴은 그대로 이식**하되, **빌드/배포/외부 서비스 의존성은 가져오지 않는다.** 패키지명은 본 저장소 식별값(`io.jeiel85.dockmode`)으로 변환한다.

### 이유

- 시안 프로젝트는 AI Studio 기본 namespace(`com.example`)와 applicationId(`com.aistudio.dockmode.vbxwqh`)를 사용하므로 본 저장소 식별값과 충돌한다.
- 시안은 Firebase BOM, Retrofit, OkHttp, Moshi, Room, KSP, Roborazzi, Secrets Gradle Plugin을 포함하지만, 본 프로젝트 정책(`AGENTS.md §1.1`, ADR-002/ADR-003)은 네트워크 권한·외부 API·계정 연동·crash reporting을 금지한다.
- UI 디자인은 사용자 경험에 직접 기여하므로 이식 대상이지만, 위 의존성들은 정책 충돌 또는 외부 콘솔 설정이 필요해 사전 승인 대상이다.

### 영향

- 디자인 이식 시 필요한 최소 의존성만 추가한다(이번 작업: `material-icons-core`, `material-icons-extended`, `navigation-compose`).
- 시안의 `MainActivity.NavHost` 패턴은 채택하여 향후 화면 추가 시 라우팅이 용이하다.
- 시안의 라이트 모드 컬러 스킴도 함께 가져오지만, 기본값은 다크 모드를 유지하여 베드사이드 UX를 보전한다.
- 시안에 의도된 야간 호박색(Amber) 톤은 미니멀 시계 스타일에서만 활성화되어 OLED 번인·눈부심 방지 정책과 조화롭게 동작한다.
