# DockMode 화면 캡처

리뉴얼 디자인의 핵심 화면 5종을 모은 디렉터리입니다. README와 Play Store 등록 자료의 출처로 사용합니다.

## 파일 목록

| 파일 | 화면 | 모드 | 비고 |
|---|---|---|---|
| `01-home.png` | HomeScreen 상단 | 세로 | DOCK MODE 라벨 헤더, 충전 상태 카드, 대기 화면 시작 CTA |
| `01b-home-scrolled.png` | HomeScreen 하단 | 세로 | 캘린더/스크린세이버 카드, 설정 진입 OutlinedButton |
| `02-settings.png` | SettingsScreen | 세로 | 시계 스타일 카드 셀렉터, 토글 4종, 개인정보 카드 |
| `03-standby-minimal.png` | StandbyScreen Minimal | 가로 | 야간 호박색 톤 |
| `04-standby-digital.png` | StandbyScreen Digital | 가로 | 좌측 시계 + 우측 글래스 패널 "다음 일정" |
| `05-standby-calendar.png` | StandbyScreen CalendarFocus | 가로 | 좌측 시계 + 우측 "오늘 일정" 리스트 |

## 캡처 환경

- 기기: Galaxy S24 (SM-S921N)
- OS: Samsung One UI 기반 Android
- 해상도: 1080 × 2340 (세로) / 2340 × 1080 (가로)
- 빌드: `dockmode-debug.apk` (디자인 리뉴얼 이식 이후, statusBarColor deprecation 대응 후)
- 캘린더 권한은 의도적으로 거부 상태로 캡처 (권한 미허용 시 안내 문구 확인 목적)

## 재캡처 방법

`adb` 연결 후:

```powershell
adb shell am start -n io.jeiel85.dockmode.debug/io.jeiel85.dockmode.MainActivity
adb exec-out screencap -p > docs/screenshots/01-home.png
```

UI 자동화로 standby 시계 스타일을 바꾸려면 settings 진입 후 시계 스타일 카드를 좌표로 탭한 뒤 `대기 화면 시작` 버튼을 다시 누릅니다. 자세한 진입 흐름은 `HISTORY.md` 2026-05-23 항목을 참고하세요.
