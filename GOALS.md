# GOALS.md

# DockMode 에이전트 실행용 Goal 프롬프트

아래 Goal은 한 번에 너무 작은 작업이 되지 않도록 2~3시간 단위 작업량을 기준으로 구성했습니다. 각 Goal은 4000자 이하로 유지했습니다.

---

## Goal 1: Android 프로젝트 초기화와 수동 Standby 화면 MVP 구현

```text
/goal DockMode Android 프로젝트를 초기화하고 수동 대기 화면 MVP를 구현해줘.

반드시 먼저 AGENTS.md, PROJECT_ID.md, SPEC.md, TECH_SPEC.md, TASKS.md, DECISIONS.md, HISTORY.md, CHANGELOG.md를 읽고 작업 범위와 완료 기준을 확인해.

작업 범위:
1. Kotlin + Jetpack Compose + Gradle Kotlin DSL 기반 Android 앱 구조를 만든다.
2. PROJECT_ID.md 기준으로 Repository ID는 dockmode-android, Android applicationId/namespace는 io.jeiel85.dockmode로 설정한다.
3. MainActivity에는 앱 설명, 충전 상태 카드, “대기 화면 시작” 버튼을 둔다.
4. StandbyActivity를 만들고 가로모드 전체화면으로 표시한다.
5. StandbyActivity에서는 FLAG_KEEP_SCREEN_ON을 적용한다.
6. StandbyScreen에는 대형 현재 시간, 날짜/요일, 충전 상태를 표시한다.
7. BatteryManager와 ACTION_BATTERY_CHANGED 기반 충전 상태 조회를 구현한다.
8. 한국어와 영어 문자열 리소스를 함께 작성한다.
9. ktlint/detekt가 없다면 최소 설정을 추가한다.
10. GitHub Actions Android CI 기본 워크플로를 추가한다.

금지:
- 캘린더 권한과 DreamService는 이번 Goal에서 구현하지 않는다.
- 네트워크 권한, 광고, 분석, 외부 SDK를 추가하지 않는다.
- Apple StandBy 이름, UI, 아이콘, 화면 구성을 복제하지 않는다.

검증:
- ./gradlew ktlintCheck 가능한 경우 실행
- ./gradlew testDebugUnitTest 가능한 경우 실행
- ./gradlew assembleDebug 실행
- 실행하지 못한 검증은 이유를 HISTORY.md에 기록

문서 갱신:
- TASKS.md 체크 상태 갱신
- HISTORY.md 작업/변경 파일/검증/결과 기록
- CHANGELOG.md에 unreleased 변경 사항 기록
- 필요한 기술 판단은 DECISIONS.md에 기록

완료 후 AGENTS.md의 Final Report Format에 맞춰 보고해.
```

---

## Goal 2: 캘린더 권한 UX와 오늘 일정 표시 구현

```text
/goal DockMode에 Android Calendar Provider 기반 오늘 일정 표시 기능을 구현해줘.

먼저 AGENTS.md, PROJECT_ID.md, SPEC.md, TECH_SPEC.md, TASKS.md, PRIVACY.md를 읽고 권한과 개인정보 정책을 확인해.

작업 범위:
1. READ_CALENDAR 권한을 Manifest에 선언한다.
2. 앱 시작 즉시 권한을 요청하지 말고, 사용자가 캘린더 연동을 누를 때만 설명 후 요청한다.
3. CalendarRepository를 만들어 ContentResolver로 오늘 일정과 다음 일정을 조회한다.
4. 일정 모델은 id, title, startsAtMillis, endsAtMillis, allDay만 사용한다.
5. StandbyScreen에 다음 일정과 오늘 일정 요약 영역을 추가한다.
6. 권한 없음, 권한 거부, 일정 없음, 조회 실패 상태를 각각 UI로 처리한다.
7. 캘린더 이벤트 데이터를 별도 DB에 저장하지 않는다.
8. 권한 설명 문구는 한국어와 영어 리소스를 모두 추가한다.
9. 시간/날짜 포맷과 일정 필터링 단위 테스트를 추가한다.

금지:
- Google Calendar REST API, 로그인, 계정 연동을 추가하지 않는다.
- 외부 네트워크 API를 추가하지 않는다.
- 권한 거부 시 기능을 강제하거나 반복 팝업을 띄우지 않는다.

검증:
- ./gradlew ktlintCheck 가능한 경우 실행
- ./gradlew testDebugUnitTest 실행
- ./gradlew assembleDebug 실행
- 권한 허용/거부/일정 없음 흐름을 수동 확인하고 HISTORY.md에 기록

문서 갱신:
- TASKS.md 체크 상태 갱신
- PRIVACY.md와 실제 권한 사용이 일치하는지 확인
- HISTORY.md, CHANGELOG.md 갱신
- 필요한 결정은 DECISIONS.md에 기록

완료 후 Final Report Format에 맞춰 보고해.
```

---

## Goal 3: DreamService, 번인 방지, 릴리즈 준비 구현

```text
/goal DockMode의 Android 스크린세이버 모드와 출시 후보 품질 작업을 구현해줘.

먼저 AGENTS.md, PROJECT_ID.md, SPEC.md, TECH_SPEC.md, TASKS.md, RELEASE.md, DECISIONS.md를 읽고 DreamService와 배포 기준을 확인해.

작업 범위:
1. StandbyDreamService를 구현한다.
2. Manifest에 android.service.dreams.DreamService 서비스와 BIND_DREAM_SERVICE 권한을 등록한다.
3. @xml/standby_dream 메타데이터를 추가한다.
4. DreamService에서 ComposeView로 StandbyScreen을 재사용한다.
5. Dream lifecycle에서 타이머, Flow 수집, 리소스를 정리한다.
6. HomeScreen에 Android 스크린세이버 설정 안내 CTA를 추가한다.
7. 번인 방지 위치 이동 기능을 구현하고 설정으로 켜고 끌 수 있게 한다.
8. 야간 모드와 시계 스타일 3종을 구현한다.
9. Release AAB 생성과 GitHub Actions 산출물 업로드를 구성한다.
10. RELEASE.md 기준으로 릴리즈 전 체크리스트를 갱신한다.

금지:
- 충전 연결만으로 백그라운드 Activity를 강제 실행하지 않는다.
- 시스템 설정을 사용자 동의 없이 변경하려고 하지 않는다.
- 광고, 분석, crash reporting, remote config를 추가하지 않는다.

검증:
- ./gradlew ktlintCheck 가능한 경우 실행
- ./gradlew testDebugUnitTest 실행
- ./gradlew assembleDebug 실행
- ./gradlew bundleRelease 실행
- 실제 기기에서 DreamService 선택 가능 여부와 충전/유휴 표시를 확인하고 HISTORY.md에 기록

문서 갱신:
- TASKS.md, HISTORY.md, CHANGELOG.md 갱신
- RELEASE.md 산출물 체크리스트 갱신
- DECISIONS.md에 DreamService 구현 결정과 제약 기록

완료 후 Final Report Format에 맞춰 보고해.
```
