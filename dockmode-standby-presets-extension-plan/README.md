# DockMode 대기모드 프리셋 추가 개발 계획서 묶음

이 묶음은 이미 초기 구현이 완료된 `dockmode-android` 프로젝트에 **대기모드 디자인 프리셋 몇 가지를 추가하는 후속 개발 계획서**입니다.

초기 프로젝트 생성, 앱 기본 기능 재구현, 전체 아키텍처 재작성은 이 묶음의 목적이 아닙니다. 기존 구현을 보존하면서 아래 범위만 추가합니다.

## 적용 대상

```text
Project Name: DockMode
Project Code: DKM-ANDROID
Repository ID: dockmode-android
Android Application ID: io.jeiel85.dockmode
Android Namespace: io.jeiel85.dockmode
Kotlin Package Path: app/src/main/java/io/jeiel85/dockmode
```

## 이번 추가 개발의 목표

DockMode의 기존 대기모드 화면이 단조롭게 느껴지지 않도록, **코드 기반의 여러 Standby Preset**을 추가합니다.

핵심은 다음 네 가지입니다.

```text
Mode   = 무엇을 보여줄 것인가
Theme  = 어떤 분위기로 보여줄 것인가
Widget = 화면을 구성하는 정보 블록
Preset = Mode + Theme + Widget 조합
```

## 이번 묶음에서 강하게 제한하는 것

- 기존 앱을 새로 만들지 않습니다.
- 기존 초기 구현 계획서를 다시 수행하지 않습니다.
- 기존 Activity, ViewModel, Repository, Theme 구조를 무리하게 갈아엎지 않습니다.
- 외부 Weather API를 추가하지 않습니다.
- 사진 접근 권한을 추가하지 않습니다.
- 광고, 분석, 로그인, 결제, 원격 설정, crash reporting SDK를 추가하지 않습니다.
- 네트워크 권한을 추가하지 않습니다.
- iPhone StandBy 화면을 그대로 복제하지 않습니다.
- 앱 이름, 패키지명, 저장소 ID를 변경하지 않습니다.

## 포함 문서

| 파일 | 역할 |
|---|---|
| `ADDITIONAL_DEVELOPMENT_PLAN.md` | 추가 개발 전체 계획 |
| `PATCH_SCOPE.md` | 작업 범위와 금지 범위 |
| `STANDBY_PRESETS.md` | 추가할 대기모드 프리셋 정의 |
| `THEME_PRESETS.md` | 코드 기반 테마 정의 |
| `WIDGET_SYSTEM_EXTENSION.md` | 위젯 확장 구조 |
| `IMPLEMENTATION_GUIDE.md` | Kotlin/Compose 구현 가이드 |
| `TASKS.md` | 체크리스트형 작업 목록 |
| `ACCEPTANCE_CRITERIA.md` | 완료 기준 |
| `DECISIONS.md` | 기술 판단 기록 |
| `AGENT_PROMPT.md` | 에이전트에게 바로 붙여넣을 통합 프롬프트 |
| `CHANGELOG_PATCH.md` | 기존 CHANGELOG에 추가할 기록 초안 |
| `HISTORY_PATCH.md` | 기존 HISTORY에 추가할 기록 초안 |
| `docs/ui-wireframes.md` | 텍스트 기반 화면 와이어프레임 |
| `docs/references.md` | 공식 참고 문서 |

## 적용 순서

1. ZIP을 기존 `dockmode-android` 저장소 루트에 풀거나, 필요한 문서만 `docs/`에 복사합니다.
2. `AGENT_PROMPT.md` 내용을 에이전트에게 전달합니다.
3. 에이전트가 기존 구현을 먼저 확인한 뒤, 프리셋 추가만 수행하게 합니다.
4. 구현 완료 후 `TASKS.md`, `HISTORY.md`, `CHANGELOG.md`, `DECISIONS.md`를 갱신합니다.

## 가장 중요한 한 줄

> 이번 작업은 “새 앱 개발”이 아니라 “이미 구현된 DockMode에 코드 기반 대기모드 프리셋 몇 가지를 추가하는 후속 개발”입니다.
