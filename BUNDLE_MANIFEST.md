# BUNDLE_MANIFEST.md

# DockMode 설계 묶음 목록

적용 식별값: Project Code `DKM-ANDROID`, Repository ID `dockmode-android`, Android Application ID `io.jeiel85.dockmode`.

## 루트 문서

- AGENTS.md
- README.md
- PROJECT_ID.md
- SPEC.md
- TECH_SPEC.md
- TASKS.md
- GOALS.md
- DECISIONS.md
- PRIVACY.md
- RELEASE.md
- CHANGELOG.md
- HISTORY.md
- GEMINI.md
- CLAUDE.md
- .cursorrules

## 보조 문서

- docs/references.md
- docs/ui-wireframes.md
- docs/permission-policy.md
- docs/store-checklist.md
- docs/releases/v0.1.0.md
- play_store/release_notes/v0.1.0.txt

## CI 템플릿

- .github/workflows/android-ci.yml

## 사용 순서

1. 저장소 루트에 전체 복사
2. `AGENTS.md` 프로젝트 설정값 확인
3. `GOALS.md`의 Goal 1부터 에이전트에게 실행
4. Goal 완료마다 `TASKS.md`, `HISTORY.md`, `CHANGELOG.md` 갱신
