# Play Console 스토어 등록정보 자료

Play Console > 스토어 등록정보(Store presence)에 입력할 모든 텍스트/메타데이터/양식 답변 초안을 모은 디렉터리입니다. 사용자가 Play Console에서 클릭/붙여넣기로 사용합니다.

## 파일 구조

```
play_store/
├── listing/
│   ├── README.md                        # 이 파일
│   ├── category-and-audience.md         # 카테고리, 타깃 사용자, 가격, 배포 국가
│   ├── content-rating-answers.md        # IARC 콘텐츠 등급 설문 답변
│   ├── data-safety-form.md              # 데이터 보안 양식 답변
│   ├── ko-KR/
│   │   ├── short-description.txt        # 80자 이내
│   │   └── long-description.txt         # 4000자 이내
│   └── en-US/
│       ├── short-description.txt
│       └── long-description.txt
├── screenshots/                         # 폰 스크린샷 5종 + 업로드 가이드
└── release_notes/                       # 버전별 "What's new" 본문
```

## Play Console 필드 ↔ 본 자료 매핑

| Play Console 입력 위치 | 사용할 파일 |
|---|---|
| 스토어 등록정보 → 앱 이름 (50자) | `DockMode - 거치대 시계 & 일정` 또는 사용자 결정 (`docs/branding-research.md` 참고) |
| 스토어 등록정보 → 짧은 설명 (80자) | `ko-KR/short-description.txt`, `en-US/short-description.txt` |
| 스토어 등록정보 → 전체 설명 (4000자) | `ko-KR/long-description.txt`, `en-US/long-description.txt` |
| 스토어 등록정보 → 카테고리 | `category-and-audience.md` |
| 스토어 등록정보 → 휴대전화 스크린샷 | `play_store/screenshots/` |
| 스토어 등록정보 → 피처 그래픽 | **미준비** — 정식 디자인 후속 작업 |
| 스토어 등록정보 → 고해상도 아이콘 (512×512) | **미준비** — 정식 디자인 후속 작업 |
| 정책 → 개인정보처리방침 | `https://jeiel85.github.io/dockmode-android/privacy/` |
| 앱 콘텐츠 → 콘텐츠 등급 | `content-rating-answers.md` |
| 앱 콘텐츠 → 데이터 보안 | `data-safety-form.md` |
| 앱 콘텐츠 → 대상 사용자 및 콘텐츠 | `category-and-audience.md` |
| 앱 콘텐츠 → 광고 | 없음 (광고 SDK 미포함) |
| 출시 → 테스트/프로덕션 → 출시 노트 | `play_store/release_notes/<tag>.txt` |

## 미준비 자산 (후속 디자인 작업)

Play Console 등록을 100% 완료하려면 아래 자산이 추가로 필요합니다. 현재는 임시 어댑티브 아이콘 외에는 준비되지 않았습니다.

| 자산 | 사양 | 상태 |
|---|---|---|
| 고해상도 앱 아이콘 | 512×512 PNG, 32-bit | **미준비** (어댑티브 아이콘에서 추출 가능하나 정식 디자인 권장) |
| 피처 그래픽 | 1024×500 PNG/JPG | **미준비** |
| TV 배너 | 1280×720 | 해당 없음 (TV 미지원) |
| 동영상 (선택) | YouTube URL | 해당 없음 |

후속 작업으로 `TASKS.md`에 디자인 항목을 추가하여 추적합니다.

## 갱신 규칙

- 앱 동작/권한/네트워크 정책이 변경되면 `data-safety-form.md`와 `long-description.txt`를 먼저 갱신합니다.
- 새 시계 스타일이나 핵심 기능이 추가되면 `long-description.txt` "핵심 기능" 항목과 스크린샷을 함께 교체합니다.
- 영어 번역은 한국어 본문과 동일한 PR에서 함께 갱신합니다.
