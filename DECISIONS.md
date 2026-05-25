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

---

## ADR-010: 코드 기반 대기모드 디자인 프리셋 및 테마 연동과 제스처 스와이프 기능 채택

날짜: 2026-05-25

### 결정

- 대기화면에 8종의 다양한 프리셋(Minimal, Digital, CalendarFocus, WarmBedside, OledNight, SplitDashboard, BatteryDock, PhotoFrame)과 6종의 프리미엄 테마를 코드 기반(Compose Canvas, Brush 등)으로 구축한다.
- StandbyScreen에서 좌우 스와이프 제스처(`detectHorizontalDragGestures`)로 시계 스타일(ClockStyle)을 실시간 전환할 수 있게 구현한다.
- `ClockStyle` enum 내에 abstract 메서드(`getTitle`, `getPreview`)를 직접 정의하여 다형성으로 분기 처리를 객체 지향화하고 detekt 규정인 Cyclomatic Complexity(복잡도 15 미만) 제약을 완벽히 준수한다.

### 이유

- 사용자가 거치대 충전 상황에 어울리는 최적의 대기화면 디자인을 다양하게 즐길 수 있게 한다.
- 스와이프 제스처는 물리 버튼을 누르지 않아도 직관적이고 아름답게 스타일을 바꿀 수 있는 프리미엄 Ambient UX를 보장한다.
- 뷰 구조의 when-expression 복잡도를 Kotlin 다형성 패턴을 사용해 Enum 내부에 정적으로 쪼개어 배치함으로써, 코드 품질과 린트(detekt) 기준을 동시에 만족할 수 있었다.

### 영향

- 마지막으로 선택된 시계 프리셋 및 테마 아이디는 DataStore 설정 저장소에 실시간 저장되어 앱을 재시작해도 온전히 복원된다.
- 설정 화면의 UI도 LazyRow 기반 가로 스크롤 카드 레이아웃으로 개편하여 공간을 적게 차지하면서도 깔끔한 조작계를 제공한다.

---

## ADR-011: 로컬 갤러리 사진 실연동, 조도 센서 자동 야간 모드 및 태블릿 5:5 대칭 분할 레이아웃 튜닝

날짜: 2026-05-25

### 결정

- 로컬 갤러리 연동: 외부 클라우드 호출 대신 사용자 기기의 로컬 저장소를 ContentResolver로 직접 쿼리하여 Photo Frame 내에서 10초 주기로 Coil `AsyncImage` + `Crossfade` 연동 슬라이드 쇼를 구현한다.
- 조도 센서 야간 테마: `Sensor.TYPE_LIGHT` 하드웨어 센서를 lifecycle-safe한 flow 스트림으로 감지하여, 주변 밝기가 설정된 4단계 감도 미만으로 어두워지면 화면을 강제로 `oled_pure_black` 테마로 연동한다.
- 태블릿 레이아웃: 가로 폭이 600dp 이상인 대화면 기기(태블릿, 폴더블)에서는 좌우 5:5 대칭 칼럼 구조(`leftWeight = 1f : rightWeight = 1f`)와 확장 패딩을 적용하여 광활한 화면에 어울리도록 반응형 튜닝을 수행한다.
- detekt 규정 준수: 설정 UI(`SettingsScreen`) 내 복잡도를 낮추기 위해 `SensorSensitivitySelector` Composable 및 `getSensitivityLabel` 다국어 헬퍼 함수를 추출하여 Cyclomatic Complexity 15 미만 조건을 완벽하게 만족시킨다.

### 이유

- 외부 클라우드 연동(Google Photo API 등)은 불필요한 네트워크 권한 및 API 키, 로그인 계정 부담을 발생시킨다. 로컬 ContentResolver 기반 연동으로 개인정보 보호 최우선 원칙과 앱 동작의 로컬 자립성을 확보한다.
- 주변 조도가 매우 어두운 베드사이드 환경에서 눈부심을 완벽하게 방지하기 위해 정교한 lux 임계 감도 설정과 OLED 최적화 야간 테마 강제 전환을 제공한다.
- 광활한 태블릿 가로 화면에서는 기존 모바일 중심의 비대칭 레이아웃이 엉성하게 보일 수 있으므로 웅장한 대칭 칼럼 구조와 앰비언트 마진을 적용하여 하이엔드 액자 느낌의 프리미엄 UX를 선사한다.
- 린트 및 품질 게이트(detekt/ktlint)를 온전하게 충족하면서도 가독성 높은 고품질 코드를 유지한다.

### 영향

- 사진 프레임 프리셋을 활용하기 위해 최초 진입 시 `READ_MEDIA_IMAGES` (Android 13+) 또는 `READ_EXTERNAL_STORAGE` (Android 13 미만) 권한 승인을 아름답게 유도한다.
- 조도 센서가 없는 디바이스의 경우 기본 lux를 안전한 최대값(999.0f)으로 처리하여 예외 없이 정상 복구되도록 설계하였다.

