# Play Store 그래픽 자산

Play Console > 스토어 등록정보 > 그래픽 자산에 업로드할 대표 이미지입니다.

## 파일 목록

| 파일 | 용도 | 사양 |
|---|---|---|
| `app-icon-512.png` | 고해상도 앱 아이콘 | 512 × 512 PNG, 32-bit |
| `feature-graphic-1024x500.png` | 피처 그래픽 | 1024 × 500 PNG |

## 디자인 기준

- 기존 DockMode 다크 팔레트(Obsidian, Space Blue, Amber)를 사용합니다.
- Apple StandBy 명칭, UI 구성, 아이콘 형태를 복제하지 않고 Android 충전 거치대용 시계 앱 정체성을 독립적으로 표현합니다.
- 피처 그래픽은 실제 앱 화면 캡처(`play_store/screenshots/03-standby-minimal.png`, `04-standby-digital.png`)를 합성해 스토어 등록정보와 실제 구현이 어긋나지 않게 유지합니다.

## 재생성

아래 명령으로 동일 경로에 다시 생성할 수 있습니다.

```powershell
python scripts\generate-store-graphics.py
```
