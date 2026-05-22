# docs/store-checklist.md

# Play Store 출시 전 체크리스트

## 앱 정보

- [ ] 앱명 `DockMode` 사용 가능성 확인
- [ ] 패키지명/applicationId `io.jeiel85.dockmode` 확정
- [ ] 앱 아이콘이 타사 앱 또는 Apple StandBy와 유사하지 않음
- [ ] 스크린샷에 실제 구현 화면만 사용

## 권한 / 개인정보

- [ ] Manifest 권한과 PRIVACY.md 일치
- [ ] READ_CALENDAR 사용 이유 설명
- [ ] 캘린더 데이터 외부 전송 없음 확인
- [ ] 광고 SDK 없음 확인
- [ ] 분석 SDK 없음 확인
- [ ] crash reporting SDK 없음 확인 또는 추가 시 문서 갱신

## 빌드 산출물

- [ ] Release AAB 생성
- [ ] Debug APK 생성
- [ ] mapping.txt 보존
- [ ] versionName과 태그 일치
- [ ] versionCode 증가

## 실기기 검증

- [ ] 수동 StandbyActivity 동작
- [ ] 충전 상태 표시
- [ ] 화면 켜짐 유지
- [ ] 캘린더 권한 허용/거부 흐름
- [ ] DreamService 스크린세이버 선택 가능
- [ ] 충전/유휴 상태 DreamService 표시
- [ ] 야간 모드
- [ ] 번인 방지 위치 이동
