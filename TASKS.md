# TASKS.md

# DockMode 작업 목록

## 작업 운영 원칙

- 한 번의 작업 루프에서는 가장 우선순위가 높은 작업 하나만 진행한다.
- 완료된 작업은 체크하고, 실제 검증 명령을 함께 기록한다.
- 검증하지 않은 항목은 완료 처리하지 않는다.
- 구현 중 발견한 후속 작업은 즉시 구현하지 말고 “후속 작업” 섹션에 기록한다.

## Milestone 0: 프로젝트 초기화

- [x] Android Studio / Gradle Kotlin DSL 프로젝트 생성
- [x] `PROJECT_ID.md` 기준 Repository ID `dockmode-android`, namespace/applicationId `io.jeiel85.dockmode` 설정
- [x] Kotlin, Compose, AndroidX 기본 설정
- [x] `ktlint`, `detekt` 설정
- [x] GitHub Actions CI 설정
- [x] README, AGENTS, SPEC, TECH_SPEC, TASKS, DECISIONS, HISTORY, CHANGELOG 반영

완료 기준:

- `./gradlew assembleDebug` 성공 (로컬 환경에 Gradle/Android SDK 부재 → CI에서 검증 예정)
- GitHub Actions에서 Debug APK 산출물 생성 (CI 결과 확인 필요)

## Milestone 1: 수동 대기 화면 MVP

- [x] `MainActivity` 생성
- [x] `StandbyActivity` 생성
- [x] 가로 고정 전체화면 처리 (`sensorLandscape` + `windowFullscreen`)
- [x] 시스템 바 숨김 처리 (`WindowInsetsControllerCompat`)
- [x] `FLAG_KEEP_SCREEN_ON` 적용
- [x] 현재 시간 표시 (`TickerFlow` 1초 갱신)
- [x] 날짜/요일 표시 (로케일별 포맷)
- [x] 충전 상태 감지 (`ACTION_BATTERY_CHANGED` sticky broadcast)
- [x] 다크 테마 적용 (`DockModeTheme`)

완료 기준:

- 앱 실행 후 “대기 화면 시작” 버튼으로 StandbyActivity 진입
- 가로 화면에서 시계가 1분 단위 이상 갱신
- 충전/미충전 상태가 구분되어 표시
- `testDebugUnitTest`, `assembleDebug` 성공 (CI 검증 필요)

## Milestone 2: 캘린더 연동

- [x] `READ_CALENDAR` Manifest 선언
- [x] 권한 설명 UX 작성 (HomeScreen 카드 + 영구 거부 시 시스템 설정 안내)
- [x] 런타임 권한 요청 구현 (`rememberLauncherForActivityResult`)
- [x] `CalendarRepository` 구현 (ContentResolver + IO dispatcher)
- [x] 오늘 일정 조회 구현 (자정 기준 범위 쿼리)
- [x] 다음 일정 계산 구현 (`CalendarFilters.nextEvent`)
- [x] 권한 없음/일정 없음/오류 상태 UI 구현

완료 기준:

- 권한 허용 시 오늘 일정이 표시됨 (실기기 확인 필요)
- 권한 거부 시 앱이 크래시 없이 시계 모드 유지 (UI 분기 처리 확인)
- 일정 없는 날에는 빈 상태 문구 표시
- 캘린더 이벤트 제목과 시간 외 불필요한 데이터 저장 없음 (DB 없음)

## Milestone 3: DreamService 모드

- [x] `StandbyDreamService` 구현 (Lifecycle/ViewModelStore/SavedStateRegistry owner 직접 구현)
- [x] `@xml/standby_dream` 추가 (settingsActivity → MainActivity)
- [x] Manifest 서비스 등록 (BIND_DREAM_SERVICE 권한 포함)
- [x] HomeScreen에 스크린세이버 설정 안내 추가 (`ACTION_DREAM_SETTINGS`)
- [x] DreamService에서 Compose 화면 표시 (`ComposeView` + ViewCompositionStrategy)
- [x] Dream lifecycle에서 리소스 정리 (`ViewModelStore.clear`, lifecycle ON_DESTROY)

완료 기준:

- Android 설정에서 DockMode 스크린세이버 선택 가능 (실기기 검증 필요)
- 충전/유휴 상태에서 DreamService 화면 표시 (실기기 검증 필요)
- Dream 종료 시 타이머/Flow 수집 정리
- 실제 기기 검증 결과 HISTORY에 기록 (후속 작업)

## Milestone 4: 제품 완성도

- [x] 시계 스타일 3종 (Minimal / Digital / CalendarFocus)
- [x] 야간 모드 (현재는 다크 테마 고정, 자동 전환은 후속)
- [x] 번인 방지 위치 이동 (`BurnInOffset` 1분 주기 원형 이동)
- [ ] 태블릿/폴더블 레이아웃 최적화 (후속)
- [x] 설정 저장/복원 (DataStore Preferences)
- [x] 한국어/영어 문자열 리소스 정리
- [x] 개인정보 안내 화면 (SettingsScreen 하단)

완료 기준:

- 설정값이 앱 재시작 후 유지됨 (DataStore 사용)
- 한국어/영어 문자열 누락 없음 (`MissingTranslation` 외 동시 작성)
- 장시간 화면 표시 수동 테스트 기록 (후속)

## Milestone 5: 배포 준비

- [ ] Release AAB 생성 (CI 검증)
- [ ] Debug APK 생성 (CI 검증)
- [ ] mapping.txt 산출물 보존 (CI 워크플로 업로드 단계 존재)
- [ ] GitHub Release 노트 작성 (첫 태그 시점)
- [ ] Play Store 릴리즈 노트 초안 작성
- [ ] 개인정보 처리 안내 검토 (PRIVACY.md 일치 확인)
- [ ] 앱명/패키지명/버전 일치 확인 (`versionName=0.1.0`)

완료 기준:

- `CHANGELOG.md`, `HISTORY.md`, `RELEASE.md`가 실제 산출물과 일치
- GitHub Actions 성공
- Release 산출물 크기 0이 아님

## 후속 작업 후보

- [ ] 날씨 API 연동 검토: 네트워크 권한과 외부 API 승인 필요
- [ ] 사진 프레임 모드 검토: 미디어 권한 정책 검토 필요
- [ ] 홈 화면 위젯 검토
- [ ] Wear OS 또는 태블릿 전용 모드 검토
- [ ] 야간 모드 자동 적용(시간/조도 센서) 정책 결정 및 구현
- [ ] 태블릿/폴더블 가로 분할 레이아웃 튜닝
- [ ] StandbyScreen instrumented test 추가 (Compose UI Test)
- [ ] 실기기 DreamService 검증 결과 HISTORY 기록
- [ ] 앱 아이콘 디자인 정식 작업 (현재는 임시 어댑티브 아이콘)
- [ ] Play Store 출시 전 앱명 `DockMode` 상표/중복 검토
