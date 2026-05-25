# CHANGELOG 추가 초안

기존 `CHANGELOG.md` 상단에 아래 형식으로 추가하세요. 버전은 실제 프로젝트 버전에 맞게 조정합니다.

```md
## vX.Y.Z - 2026-05-25

### Added
- DockMode 대기 화면에 여러 Standby Preset을 선택할 수 있는 구조 추가
- Minimal Clock, Warm Bedside, OLED Night Clock 등 코드 기반 대기모드 프리셋 추가
- Midnight Glass, Warm Bedside, OLED Pure Black 등 테마 프리셋 추가
- 선택한 대기모드 프리셋과 테마를 저장하고 복원하는 설정 흐름 추가
- OLED 야간 시계용 번인 방지 offset 로직 추가

### Changed
- StandbyActivity에서 선택된 프리셋을 기준으로 대기 화면을 렌더링하도록 개선
- 기존 대기 화면 UI를 프리셋 기반 확장 구조로 정리

### Documentation
- 대기모드 프리셋, 테마, 위젯 구조 문서 추가 또는 갱신
- 후속 디자인 확장 작업을 TASKS.md에 기록

### Verification
- 실행한 검증 명령과 결과를 여기에 기록
```
