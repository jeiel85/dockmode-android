# Apply Notes

## 권장 적용 방식

1. 이 묶음의 문서를 프로젝트 루트 또는 `docs/standby-presets/`에 복사합니다.
2. `AGENT_PROMPT.md` 내용을 코딩 에이전트에게 전달합니다.
3. 에이전트가 기존 구현을 확인한 뒤, 프리셋 추가만 수행하게 합니다.
4. 기존 구조와 계획서가 충돌하면 기존 구조를 우선합니다.
5. 구현이 너무 커지면 P0 프리셋 3개를 먼저 완료하고 나머지는 후속 작업으로 남깁니다.

## 추천 최소 성공 단위

- Preset 모델 추가
- Minimal Clock / Warm Bedside / OLED Night Clock 구현
- 설정 저장/복원
- StandbyActivity 적용
- README/HISTORY/CHANGELOG 갱신
- assembleDebug 검증

## 위험 신호

에이전트가 아래 작업을 하려 하면 중단해야 합니다.

- 프로젝트를 새로 생성하려고 함
- 앱 ID를 변경하려고 함
- 전체 패키지 구조를 이동하려고 함
- 외부 API 키를 요구함
- 사진/네트워크/광고 권한을 추가하려고 함
- iPhone StandBy와 같은 화면을 복제하려고 함
