# 통합 에이전트 요청 프롬프트

아래 내용을 그대로 에이전트에게 붙여넣어 사용하세요.

```text
현재 dockmode-android 프로젝트는 초기 구현이 이미 완료된 상태야.
이번 작업은 초기 구현을 다시 만드는 것이 아니라, 기존 DockMode 앱에 “대기모드 디자인 프리셋 몇 가지”를 추가하는 후속 개발 작업이야.

먼저 기존 저장소의 AGENTS.md, README.md, SPEC.md, TECH_SPEC.md, TASKS.md, DECISIONS.md, HISTORY.md, CHANGELOG.md를 읽고 현재 구조를 파악해줘.
그 다음 이번 추가 계획서 묶음의 README.md, ADDITIONAL_DEVELOPMENT_PLAN.md, PATCH_SCOPE.md, STANDBY_PRESETS.md, THEME_PRESETS.md, WIDGET_SYSTEM_EXTENSION.md, IMPLEMENTATION_GUIDE.md, TASKS.md, ACCEPTANCE_CRITERIA.md, DECISIONS.md를 읽고 작업 범위를 확인해줘.

중요한 전제:
- Project Name: DockMode
- Project Code: DKM-ANDROID
- Repository ID: dockmode-android
- Android Application ID: io.jeiel85.dockmode
- Android Namespace: io.jeiel85.dockmode
- Kotlin Package Path: app/src/main/java/io/jeiel85/dockmode

이번 작업 목표:
기존 대기모드 화면을 유지하면서, 사용자가 선택 가능한 코드 기반 Standby Preset을 추가해줘.

수행 범위:
1. 기존 Standby 화면, 설정 저장 구조, Theme 구조, DreamService 구조를 먼저 확인한다.
2. 기존 구조가 있으면 그 구조를 우선하고, 없을 때만 계획서의 권장 구조를 최소 단위로 추가한다.
3. StandbyPreset, StandbyThemePreset, StandbyWidgetType 같은 프리셋 모델을 추가한다.
4. 최소 5개 이상의 대기모드 프리셋을 추가한다.
   - Minimal Clock
   - Warm Bedside
   - OLED Night Clock
   - Split Dashboard
   - Calendar Board
   - Battery Dock
   - Photo Frame Placeholder 중 가능한 범위
5. 최소 5개 이상의 테마 프리셋을 추가한다.
   - Midnight Glass
   - Warm Bedside
   - OLED Pure Black
   - Aurora Gradient
   - Paper Calendar
   - Material You 중 가능한 범위
6. 사용자가 프리셋을 선택할 수 있는 설정 UI를 추가하거나 기존 설정 화면에 연결한다.
7. 선택한 프리셋과 테마를 저장하고 앱 재시작 후 복원한다.
8. StandbyActivity에서 선택 프리셋을 적용한다.
9. StandbyDreamService가 이미 구현되어 있다면 같은 StandbyRenderer/StandbyScreen을 공유하게 한다.
10. OLED Night Clock에는 번인 방지용 최소 offset 로직을 적용한다.
11. 캘린더 권한이 없거나 Calendar Provider 연동이 부족해도 Calendar Board가 깨지지 않게 placeholder/fallback을 제공한다.
12. 사진 권한은 추가하지 말고 Photo Frame은 placeholder로만 구현한다.
13. 구현 후 README.md, SPEC.md, TECH_SPEC.md, TASKS.md, HISTORY.md, CHANGELOG.md, DECISIONS.md를 갱신한다.

강한 제한 사항:
- 초기 프로젝트 생성을 다시 하지 마.
- 기존 구현 전체를 삭제하거나 새로 만들지 마.
- 앱 ID, namespace, package path를 변경하지 마.
- 네트워크 권한을 추가하지 마.
- 외부 Weather API를 추가하지 마.
- 사진 접근 권한을 추가하지 마.
- 광고, 분석, 로그인, 결제, 원격 설정, crash reporting SDK를 추가하지 마.
- iPhone StandBy 디자인을 그대로 복제하지 마. 영감만 참고하고 DockMode만의 Android 스타일로 재해석해.
- 전체 파일 포맷팅, 대규모 패키지 이동, 요청 범위 밖 리팩터링은 하지 마.

검증:
- 가능한 로컬 검증을 실행해.
- 우선순위는 ./gradlew test, ./gradlew lint, ./gradlew assembleDebug 순서야.
- 현재 환경에서 실패하면 로그를 읽고 수정 가능한 것은 수정해.
- 환경 문제로 실행하지 못한 검증은 성공으로 기록하지 말고 이유를 명확히 적어.
- 실제 실행하지 않은 테스트/빌드는 성공했다고 쓰지 마.

완료 보고:
AGENTS.md의 Final Report Format을 따라 한국어로 보고해.
반드시 아래를 포함해:
- 작업 요약
- 변경 파일
- 검증 결과
- 실패하거나 생략한 검증
- 커밋 여부
- 푸시 여부
- 후속 작업
```
