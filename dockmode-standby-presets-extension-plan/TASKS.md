# Additional Development Tasks — Standby Presets

## 0. 사전 확인

- [ ] `git status` 확인
- [ ] 기존 구현이 완료된 상태인지 확인
- [ ] 기존 Standby 화면 파일 확인
- [ ] 기존 설정 저장 구조 확인
- [ ] 기존 Theme 구조 확인
- [ ] 기존 DreamService 구조 확인
- [ ] 기존 Battery/Calendar 관련 코드 확인

## 1. 모델 추가

- [ ] `StandbyPreset` 모델 추가 또는 기존 모델 확장
- [ ] `StandbyWidgetType` 정의
- [ ] `StandbyThemePreset` 정의
- [ ] `StandbyPresetRegistry` 추가
- [ ] `StandbyThemeRegistry` 추가
- [ ] 기존 구조와 충돌 시 `DECISIONS.md`에 기록

## 2. 프리셋 구현

- [ ] Minimal Clock
- [ ] Warm Bedside
- [ ] OLED Night Clock
- [ ] Split Dashboard
- [ ] Calendar Board
- [ ] Battery Dock
- [ ] Photo Frame Placeholder

## 3. 테마 구현

- [ ] Midnight Glass
- [ ] Warm Bedside
- [ ] OLED Pure Black
- [ ] Aurora Gradient
- [ ] Paper Calendar
- [ ] Material You fallback 또는 기존 MaterialTheme 연결

## 4. 설정/저장

- [ ] 선택 프리셋 저장
- [ ] 선택 테마 저장
- [ ] 앱 재시작 후 마지막 선택 복원
- [ ] 설정 화면에서 프리셋 선택 가능
- [ ] 문자열 리소스 한국어/영어 추가

## 5. 전환 UX

- [ ] 좌우 스와이프로 프리셋 전환
- [ ] 설정 화면 목록 또는 카드 선택 UI 추가
- [ ] 현재 프리셋 이름 표시 여부 결정
- [ ] DreamService에서도 동일 프리셋 사용

## 6. 보호 로직

- [ ] OLED Night Clock에 burn-in offset 적용
- [ ] 고휘도 고정 텍스트를 피하도록 조정
- [ ] 야간용 테마 대비 조정
- [ ] 과한 애니메이션 비활성화 또는 약화

## 7. 문서 갱신

- [ ] README.md에 프리셋 목록 추가
- [ ] SPEC.md에 대기모드 프리셋 확장 내용 추가
- [ ] TECH_SPEC.md에 Preset/Theme/Widget 구조 추가
- [ ] TASKS.md 갱신
- [ ] HISTORY.md 갱신
- [ ] CHANGELOG.md 갱신
- [ ] DECISIONS.md 갱신

## 8. 검증

- [ ] 단위 테스트 가능한 범위 실행
- [ ] lint 실행 가능하면 실행
- [ ] `assembleDebug` 실행 가능하면 실행
- [ ] 에뮬레이터 또는 실기기에서 Standby 화면 확인 가능하면 확인
- [ ] DreamService 확인 가능하면 확인
- [ ] 실행하지 못한 검증은 이유와 함께 보고

## 9. 완료 보고

- [ ] 작업 요약 작성
- [ ] 변경 파일 목록 작성
- [ ] 검증 결과 작성
- [ ] 실패/생략한 검증 작성
- [ ] 커밋/푸시 여부 작성
- [ ] 후속 작업 작성
