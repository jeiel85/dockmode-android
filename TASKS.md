# TASKS.md

# DockMode 작업 목록

## 작업 운영 원칙

- 한 번의 작업 루프에서는 가장 우선순위가 높은 작업 하나만 진행한다.
- 완료된 작업은 체크하고, 실제 검증 명령을 함께 기록한다.
- 검증하지 않은 항목은 완료 처리하지 않는다.
- 구현 중 발견한 후속 작업은 즉시 구현하지 말고 “후속 작업” 섹션에 기록한다.

## Milestone 0: 프로젝트 초기화

- [ ] Android Studio / Gradle Kotlin DSL 프로젝트 생성
- [ ] `PROJECT_ID.md` 기준 Repository ID `dockmode-android`, namespace/applicationId `io.jeiel85.dockmode` 설정
- [ ] Kotlin, Compose, AndroidX 기본 설정
- [ ] `ktlint`, `detekt` 설정
- [ ] GitHub Actions CI 설정
- [ ] README, AGENTS, SPEC, TECH_SPEC, TASKS, DECISIONS, HISTORY, CHANGELOG 반영

완료 기준:

- `./gradlew assembleDebug` 성공
- GitHub Actions에서 Debug APK 산출물 생성

## Milestone 1: 수동 대기 화면 MVP

- [ ] `MainActivity` 생성
- [ ] `StandbyActivity` 생성
- [ ] 가로 고정 전체화면 처리
- [ ] 시스템 바 숨김 처리
- [ ] `FLAG_KEEP_SCREEN_ON` 적용
- [ ] 현재 시간 표시
- [ ] 날짜/요일 표시
- [ ] 충전 상태 감지
- [ ] 다크 테마 적용

완료 기준:

- 앱 실행 후 “대기 화면 시작” 버튼으로 StandbyActivity 진입
- 가로 화면에서 시계가 1분 단위 이상 갱신
- 충전/미충전 상태가 구분되어 표시
- `testDebugUnitTest`, `assembleDebug` 성공

## Milestone 2: 캘린더 연동

- [ ] `READ_CALENDAR` Manifest 선언
- [ ] 권한 설명 UX 작성
- [ ] 런타임 권한 요청 구현
- [ ] `CalendarRepository` 구현
- [ ] 오늘 일정 조회 구현
- [ ] 다음 일정 계산 구현
- [ ] 권한 없음/일정 없음/오류 상태 UI 구현

완료 기준:

- 권한 허용 시 오늘 일정이 표시됨
- 권한 거부 시 앱이 크래시 없이 시계 모드 유지
- 일정 없는 날에는 빈 상태 문구 표시
- 캘린더 이벤트 제목과 시간 외 불필요한 데이터 저장 없음

## Milestone 3: DreamService 모드

- [ ] `StandbyDreamService` 구현
- [ ] `@xml/standby_dream` 추가
- [ ] Manifest 서비스 등록
- [ ] HomeScreen에 스크린세이버 설정 안내 추가
- [ ] DreamService에서 Compose 화면 표시
- [ ] Dream lifecycle에서 리소스 정리

완료 기준:

- Android 설정에서 DockMode 스크린세이버 선택 가능
- 충전/유휴 상태에서 DreamService 화면 표시
- Dream 종료 시 타이머/Flow 수집 정리
- 실제 기기 검증 결과 HISTORY에 기록

## Milestone 4: 제품 완성도

- [ ] 시계 스타일 3종
- [ ] 야간 모드
- [ ] 번인 방지 위치 이동
- [ ] 태블릿/폴더블 레이아웃
- [ ] 설정 저장/복원
- [ ] 한국어/영어 문자열 리소스 정리
- [ ] 개인정보 안내 화면

완료 기준:

- 설정값이 앱 재시작 후 유지됨
- 한국어/영어 문자열 누락 없음
- 장시간 화면 표시 수동 테스트 기록

## Milestone 5: 배포 준비

- [ ] Release AAB 생성
- [ ] Debug APK 생성
- [ ] mapping.txt 산출물 보존
- [ ] GitHub Release 노트 작성
- [ ] Play Store 릴리즈 노트 초안 작성
- [ ] 개인정보 처리 안내 검토
- [ ] 앱명/패키지명/버전 일치 확인

완료 기준:

- `CHANGELOG.md`, `HISTORY.md`, `RELEASE.md`가 실제 산출물과 일치
- GitHub Actions 성공
- Release 산출물 크기 0이 아님

## 후속 작업 후보

- [ ] 날씨 API 연동 검토: 네트워크 권한과 외부 API 승인 필요
- [ ] 사진 프레임 모드 검토: 미디어 권한 정책 검토 필요
- [ ] 홈 화면 위젯 검토
- [ ] Wear OS 또는 태블릿 전용 모드 검토
