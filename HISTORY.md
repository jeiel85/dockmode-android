# HISTORY.md

## 2026-05-22

- 작업: DockMode 프로젝트 코드와 레포 아이디 확정 및 설계 묶음 반영
- 적용 식별값:
  - Project Code: `DKM-ANDROID`
  - Repository ID: `dockmode-android`
  - Repository URL: `https://github.com/jeiel85/dockmode-android.git`
  - Android applicationId/namespace: `io.jeiel85.dockmode`
- 변경 파일:
  - PROJECT_ID.md: 프로젝트 식별값 기준 문서 추가
  - AGENTS.md: 프로젝트 설정값과 고유 정책에 식별값 반영
  - README.md: 프로젝트 식별값 표 추가
  - GOALS.md: Goal 프롬프트에 PROJECT_ID.md 확인 절차와 식별값 반영
  - TECH_SPEC.md: 기술 설계 식별값 블록 추가
  - TASKS.md: 저장소 초기화 작업에 식별값 확인 항목 추가
  - DECISIONS.md: ADR-005 추가
  - CHANGELOG.md: 식별값 적용 변경 사항 기록
- 검증:
  - 문서 내 기존 임시 패키지명 참조 제거 확인
  - ZIP 묶음 재생성 확인
- 결과: 성공
- 후속 작업:
  - 실제 GitHub 저장소 `dockmode-android` 생성 후 origin URL 연결
  - Gradle 프로젝트 생성 시 applicationId/namespace 값 일치 확인

## 2026-05-22

- 작업: Android 충전 거치대 시계·달력 앱 DockMode 설계 묶음 작성
- 변경 파일:
  - AGENTS.md: 기존 범용 에이전트 규칙을 DockMode 프로젝트 설정값과 Android 고유 정책으로 통합
  - README.md: 프로젝트 개요, 기능 범위, 문서 구조 작성
  - SPEC.md: 제품 요구사항, 사용자 시나리오, 화면 정의, 성공 기준 작성
  - TECH_SPEC.md: Kotlin, Compose, DreamService, BatteryManager, Calendar Provider 기반 기술 설계 작성
  - TASKS.md: 마일스톤별 작업 목록과 완료 기준 작성
  - GOALS.md: 에이전트 코딩용 3단계 Goal 프롬프트 작성
  - DECISIONS.md: 주요 기술 결정 기록
  - PRIVACY.md: 권한과 개인정보 처리 정책 작성
  - RELEASE.md: Android 릴리즈 검증 절차 작성
  - CHANGELOG.md: 초기 변경 사항 기록
  - .github/workflows/android-ci.yml: Android CI 템플릿 작성
- 검증:
  - 문서 파일 생성 확인
  - ZIP 묶음 생성 확인
- 결과: 설계 묶음 생성 완료
- 후속 작업:
  - 실제 Android 프로젝트 생성 후 Gradle 명령 실행 검증
  - Play Store 출시 전 앱명/상표/스토어 정책 검토
  - 실기기에서 DreamService 동작 검증
