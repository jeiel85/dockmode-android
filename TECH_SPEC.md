# TECH_SPEC.md

# DockMode 기술 설계서

## 1. 기술 방향

DockMode는 Android 네이티브 앱으로 구현한다.

## 0. 프로젝트 식별값

```text
Project Code: DKM-ANDROID
Repository ID: dockmode-android
Android applicationId: io.jeiel85.dockmode
Android namespace: io.jeiel85.dockmode
Kotlin package path: app/src/main/java/io/jeiel85/dockmode
Artifact prefix: dockmode
```

- Kotlin
- Jetpack Compose
- AndroidX Lifecycle / ViewModel
- Kotlin Coroutines / Flow
- DataStore Preferences
- Android Calendar Provider
- DreamService
- Gradle Kotlin DSL

## 2. 공식 문서 근거

- Jetpack Compose는 Android UI를 빌드하기 위한 최신 툴킷이며 다양한 화면 크기에 적응하는 앱 개발을 단순화한다.  
  https://developer.android.com/develop/ui/compose/documentation
- DreamService는 사용자가 선택 가능한 Android dream, 즉 interactive screensaver를 구현하기 위한 서비스다.  
  https://developer.android.com/reference/android/service/dreams/DreamService
- Activity에서 화면을 계속 켜려면 `FLAG_KEEP_SCREEN_ON`을 사용할 수 있으며, 이 플래그는 Activity에서만 설정해야 한다.  
  https://developer.android.com/develop/background-work/background-tasks/awake/screen-on
- BatteryManager는 `ACTION_BATTERY_CHANGED` Intent 기반 배터리/충전 상태 조회에 사용된다.  
  https://developer.android.com/reference/kotlin/android/os/BatteryManager
- Calendar Provider는 캘린더, 이벤트, 참석자, 알림 등을 조회/삽입/수정/삭제할 수 있는 사용자 캘린더 저장소 API다.  
  https://developer.android.com/identity/providers/calendar-provider
- 런타임 권한은 Manifest 선언과 사용자에게 권한 필요성을 설명하는 UX가 필요하다.  
  https://developer.android.com/training/permissions/requesting
- SensorManager 및 Sensor.TYPE_LIGHT 하드웨어 조도 센서를 통해 주변 밝기 값을 실시간 측정할 수 있다.  
  https://developer.android.com/reference/android/hardware/Sensor
- Android 13(API 33) 이상에서는 미디어 이미지 쿼리를 위해 `READ_MEDIA_IMAGES` 권한이 필요하며, 이전 버전은 `READ_EXTERNAL_STORAGE` 권한을 사용한다.  
  https://developer.android.com/about/versions/13/behavior-changes-13#granular-media-permissions

## 3. 모듈 구조

```text
app/
  src/main/java/io/jeiel85/dockmode/
    MainActivity.kt
    standby/StandbyActivity.kt
    standby/StandbyDreamService.kt
    standby/StandbyScreen.kt
    home/HomeScreen.kt
    settings/SettingsScreen.kt
    domain/model/
    domain/usecase/
    data/calendar/CalendarRepository.kt
    data/battery/BatteryStateRepository.kt
    data/settings/SettingsRepository.kt
    data/gallery/GalleryRepository.kt
    data/sensor/LightSensorRepository.kt
    ui/theme/
    util/
```

## 4. 아키텍처

```text
UI Layer: Compose Screens
  ↓
ViewModel Layer: StateFlow<UiState>
  ↓
Domain Layer: UseCase
  ↓
Data Layer: Repository
  ↓
Android Platform APIs: Calendar Provider, BatteryManager, DataStore, DreamService
```

## 5. 주요 컴포넌트

### 5.1 MainActivity

역할:

- HomeScreen 표시
- 권한 상태 확인
- 수동 대기 화면 진입
- Android 스크린세이버 설정 안내

### 5.2 StandbyActivity

역할:

- 사용자가 직접 실행하는 전체화면 대기 화면
- 가로모드 고정
- 화면 켜짐 유지
- 시스템 바 숨김

핵심 구현:

```kotlin
class StandbyActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setContent {
            DockModeTheme {
                StandbyRoute(mode = StandbyLaunchMode.Activity)
            }
        }
    }
}
```

### 5.3 StandbyDreamService

역할:

- Android 스크린세이버 모드 제공
- `ComposeView`를 통해 StandbyScreen 재사용
- Dream lifecycle에 맞춰 리소스 시작/정리

핵심 구현:

```kotlin
class StandbyDreamService : DreamService() {
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        isInteractive = true
        isFullscreen = true
        setScreenBright(false)
        setContentView(
            ComposeView(this).apply {
                setContent {
                    DockModeTheme {
                        StandbyRoute(mode = StandbyLaunchMode.Dream)
                    }
                }
            }
        )
    }
}
```

### 5.4 CalendarRepository

역할:

- 권한 확인 후 오늘 일정 조회
- ContentResolver query를 IO dispatcher에서 실행
- 일정 제목, 시작/종료 시간만 UI 모델로 매핑
- 권한 없음, 캘린더 없음, 일정 없음 상태를 구분

반환 모델:

```kotlin
data class CalendarEventSummary(
    val id: Long,
    val title: String,
    val startsAtMillis: Long,
    val endsAtMillis: Long,
    val allDay: Boolean
)
```

### 5.5 BatteryStateRepository

역할:

- 현재 충전 상태 조회
- 충전 상태 변화 감지
- UI에 `Charging`, `Full`, `Discharging`, `Unknown` 상태 제공

```kotlin
enum class ChargingState {
    Charging,
    Full,
    Discharging,
    Unknown
}
```

### 5.6 SettingsRepository

역할:

- DataStore Preferences로 사용자 설정 저장
- 시계 스타일, 테마 스타일, 일정 표시 여부, 야간 모드, 번인 방지, 화면 유지, 조도 센서 야간 모드 및 4단계 감도 설정 관리

### 5.7 GalleryRepository

역할:

- 갤러리 미디어 접근 권한 유효성 체크
- ContentResolver를 활용하여 기기 내부의 미디어 이미지 폴더의 고유 URI 목록을 비동기(IO Dispatcher) 쿼리

### 5.8 LightSensorRepository

역할:

- `Sensor.TYPE_LIGHT` 하드웨어 조도 센서를 lifecycle-safe한 flow 스트림으로 감지
- `callbackFlow` 래퍼 내에서 리스너가 등록되고, flow 종료 및 수명주기 해제 시 `unregisterListener`를 완벽히 수행하여 누수 방지
- 센서 미지원 디바이스를 위해 기본 999.0f lux 방출 Fallback 지원

## 6. Manifest 설계

```xml
<uses-permission android:name="android.permission.READ_CALENDAR" />
<uses-permission android:name="android.permission.READ_MEDIA_IMAGES" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" android:maxSdkVersion="32" />

<application
    android:theme="@style/Theme.DockMode">

    <activity
        android:name=".standby.StandbyActivity"
        android:exported="false"
        android:screenOrientation="landscape" />

    <activity
        android:name=".MainActivity"
        android:exported="true">
        <intent-filter>
            <action android:name="android.intent.action.MAIN" />
            <category android:name="android.intent.category.LAUNCHER" />
        </intent-filter>
    </activity>

    <service
        android:name=".standby.StandbyDreamService"
        android:exported="true"
        android:label="@string/app_name"
        android:permission="android.permission.BIND_DREAM_SERVICE">
        <intent-filter>
            <action android:name="android.service.dreams.DreamService" />
            <category android:name="android.intent.category.DEFAULT" />
        </intent-filter>
        <meta-data
            android:name="android.service.dream"
            android:resource="@xml/standby_dream" />
    </service>
</application>
```

## 7. UI 상태 모델

```kotlin
data class StandbyUiState(
    val nowMillis: Long = System.currentTimeMillis(),
    val chargingState: ChargingState = ChargingState.Unknown,
    val clockStyle: ClockStyle = ClockStyle.Minimal,
    val selectedThemeId: String = "midnight_glass",
    val showCalendar: Boolean = true,
    val burnInGuard: Boolean = true,
    val calendarPermissionState: CalendarPermissionState = CalendarPermissionState.Unknown,
    val nextEvent: CalendarEventSummary? = null,
    val todayEvents: List<CalendarEventSummary> = emptyList(),
    val calendarLoadFailed: Boolean = false,
    val isLoadingEvents: Boolean = false,
    val galleryImages: List<android.net.Uri> = emptyList(),
    val autoNightModeByLightSensor: Boolean = false,
    val lightSensorSensitivityLux: Int = 10,
    val isSensorNightActive: Boolean = false,
)
```

## 8. 권한 정책

- `READ_CALENDAR`는 앱 시작 즉시 요청하지 않는다.
- 사용자가 일정 표시 기능을 켜거나 캘린더 카드에서 “연동하기”를 누를 때 설명 후 요청한다.
- 권한이 거부되면 시계 기능은 정상 동작해야 한다.
- 권한 재요청은 Android 권한 UX 원칙에 맞춰 사용자 동작에 연결한다.

## 9. 백그라운드 실행 정책

- 충전 연결 브로드캐스트만으로 Activity를 강제 실행하지 않는다.
- 충전 감지 후 필요한 경우 알림 또는 홈 화면 카드로 “대기 화면 시작”을 제안한다.
- 자동 표시 경험은 DreamService에 위임한다.

## 10. 테스트 전략

### Unit Test

- 시간 포맷터
- 날짜 포맷터
- 일정 필터링
- 충전 상태 매핑
- 설정 저장/복원

### Instrumented Test

- StandbyScreen 렌더링
- 권한 없음 상태 UI
- 일정 없음 상태 UI
- SettingsScreen 설정 토글

### Manual Test

- 실제 기기 충전 중 StandbyActivity 화면 유지
- Android 스크린세이버 설정에서 DreamService 선택 가능 여부
- 충전/완충/미충전 상태 표시
- OLED 번인 방지 위치 이동 동작

## 11. 빌드 명령

```bash
./gradlew ktlintCheck
./gradlew detekt
./gradlew testDebugUnitTest
./gradlew assembleDebug
./gradlew bundleRelease
```

## 12. 출시 전 필수 검증

- Debug APK 설치 및 수동 대기 화면 확인
- Release AAB 생성 확인
- R8/ProGuard mapping 파일 보존 확인
- 개인정보 처리 안내와 실제 권한 사용 일치 확인
- 스토어 등록정보의 광고 없음, 데이터 수집 없음/있음 상태 확인
