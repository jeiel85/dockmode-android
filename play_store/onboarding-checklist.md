# Play Console 최초 등록 체크리스트

DockMode를 처음으로 Play Console에 등록하기 위한 사용자 액션 체크리스트입니다. 각 단계 옆에 **[사용자]** / **[자동]** / **[참고]** 표시가 있습니다.

> 사전 결정 사항: 앱명은 [docs/branding-research.md](../docs/branding-research.md)의 결론대로 `DockMode`로 가정합니다. 상표/중복명 확인 결과에 따라 후보명으로 전환할 수 있습니다.

---

## A. 사전 준비 (등록 전 1회)

- [ ] **[사용자]** Google Play Developer 계정 등록 (1회 $25 결제, 본인 인증 1~2일 소요): <https://play.google.com/console/signup>
- [ ] **[사용자]** [docs/branding-research.md](../docs/branding-research.md) §3의 USPTO TSDR / KIPRIS / EUIPO / J-PlatPat 4곳에서 `DockMode` 또는 `독모드` 상표 충돌 여부 확인. 충돌 시 후보명으로 전환 후 [DECISIONS.md](../DECISIONS.md)에 ADR 추가.
- [ ] **[사용자]** [docs/keystore-guide.md](../docs/keystore-guide.md) §1에 따라 `keytool`로 업로드 키스토어(`dockmode-upload.jks`) 생성 + 비밀번호 4종을 비밀번호 관리자에 저장.
- [ ] **[사용자]** GitHub Actions Secrets 4종 등록: `DOCKMODE_KEYSTORE_BASE64`, `DOCKMODE_KEYSTORE_PASSWORD`, `DOCKMODE_KEY_PASSWORD`, `DOCKMODE_KEY_ALIAS`. 단계는 [docs/keystore-guide.md](../docs/keystore-guide.md) §3.

## B. Play Console에 앱 만들기

- [ ] **[사용자]** Play Console → **앱 만들기**.
  - 앱 이름: `DockMode` (또는 §A에서 결정한 이름. Play Console 표시명은 부제 포함 50자 이내로 `DockMode - 거치대 시계 & 일정` 권장)
  - 기본 언어: `한국어 - 대한민국 (ko-KR)`
  - 앱/게임: 앱
  - 무료/유료: 무료
  - 선언: 모든 약관 동의 체크
- [ ] **[참고]** 패키지 이름은 첫 AAB 업로드 시 결정되며 변경 불가. 본 저장소는 `io.jeiel85.dockmode`로 고정되어 있습니다 ([PROJECT_ID.md](../PROJECT_ID.md), `app/build.gradle.kts` `applicationId`).

## C. 출시 → 설정 → 앱 무결성

- [ ] **[사용자]** **앱 서명** → **Google이 앱 서명 키 관리** (Play App Signing 옵트인).
- [ ] **[사용자]** 업로드 키 인증서는 첫 AAB 업로드 시 자동 등록되도록 두거나, [docs/keystore-guide.md](../docs/keystore-guide.md) §2의 keytool export 방식으로 수동 등록.

## D. 정책 → 앱 콘텐츠

각 항목을 본 저장소의 답변 초안에서 복사해 채웁니다.

- [ ] **[사용자]** **개인정보처리방침**: URL 입력 = `https://jeiel85.github.io/dockmode-android/privacy/`
- [ ] **[사용자]** **앱 액세스 권한**: 모든 기능이 권한·결제·로그인 없이 사용 가능. 별도 자격 증명 불필요.
- [ ] **[사용자]** **광고**: "이 앱에는 광고가 포함되어 있지 않습니다"
- [ ] **[사용자]** **콘텐츠 등급**: 카테고리 = `유틸리티, 생산성, 통신 또는 기타`. 설문 답변은 [play_store/listing/content-rating-answers.md](listing/content-rating-answers.md) 그대로.
- [ ] **[사용자]** **대상 사용자 및 콘텐츠**: 13세 이상, 13세 미만 사용자 의도 없음. ([category-and-audience.md](listing/category-and-audience.md))
- [ ] **[사용자]** **뉴스 앱 / 정부 앱 / COVID-19 추적**: 모두 "아니요"
- [ ] **[사용자]** **데이터 보안**: 데이터 수집 = "아니요". 자세한 답변은 [data-safety-form.md](listing/data-safety-form.md).

## E. 스토어 등록정보 → 기본 등록정보

- [ ] **[사용자]** **앱 이름** (50자): `DockMode - 거치대 시계 & 일정`
- [ ] **[사용자]** **짧은 설명** (80자): [ko-KR/short-description.txt](listing/ko-KR/short-description.txt) 내용 복사
- [ ] **[사용자]** **전체 설명** (4000자): [ko-KR/long-description.txt](listing/ko-KR/long-description.txt) 내용 복사
- [ ] **[사용자]** **앱 아이콘** (512×512): **미준비** — 임시로 어댑티브 아이콘에서 추출하거나, 정식 디자인 작업이 끝난 후 업로드
- [ ] **[사용자]** **피처 그래픽** (1024×500): **미준비** — 정식 디자인 작업 필요
- [ ] **[사용자]** **휴대전화 스크린샷** (최소 2장): [play_store/screenshots/README.md](screenshots/README.md)의 권장 순서대로 5장 업로드
  1. `01-home.png` (HomeScreen)
  2. `03-standby-minimal.png` (야간 미니멀)
  3. `04-standby-digital.png` (디지털 + 다음 일정)
  4. `05-standby-calendar.png` (캘린더 중심)
  5. `02-settings.png` (설정)

## F. 스토어 등록정보 → 영어 (en-US) 추가

- [ ] **[사용자]** 좌측 **언어 추가** → English (United States)
- [ ] **[사용자]** 영어 짧은 설명: [en-US/short-description.txt](listing/en-US/short-description.txt)
- [ ] **[사용자]** 영어 전체 설명: [en-US/long-description.txt](listing/en-US/long-description.txt)
- [ ] **[사용자]** 스크린샷은 한국어 등록과 동일한 5장 재사용 (Play Console은 언어별 별도 업로드)

## G. 첫 AAB 빌드 + 업로드

- [ ] **[사용자]** `app/build.gradle.kts`에서 `versionName=0.1.0`, `versionCode=1` 확인 (이미 그렇게 설정됨).
- [ ] **[자동]** `main`에 변경을 푸시하면 CI가 ktlint/detekt/test/assembleDebug/bundleRelease 수행. 시크릿이 등록되어 있으면 서명된 AAB가 자동 생성됨.
- [ ] **[사용자]** 첫 출시는 태그 푸시(`git tag -a v0.1.0 -m "DockMode v0.1.0" && git push origin v0.1.0`)로 GitHub Release를 자동 생성하고 AAB를 다운로드.
- [ ] **[사용자]** Play Console → **출시 → 테스트 → 내부 테스트** → **새 버전 만들기** → AAB 업로드.
- [ ] **[사용자]** 출시 노트는 [play_store/release_notes/v0.1.0.txt](release_notes/v0.1.0.txt) 내용 복사.
- [ ] **[사용자]** 내부 테스터 그룹에 본인 Google 계정 추가.
- [ ] **[사용자]** **검토 시작** → 내부 테스트 트랙으로 출시. 발급된 테스트 링크로 본인 기기 설치 검증.

## H. 단계적 승격

- [ ] **[사용자]** 내부 테스트에서 핵심 기능 동작 확인 (HomeScreen / Settings / Standby 3종 / DreamService) 후 비공개 테스트(Closed Testing) 트랙으로 승격.
- [ ] **[사용자]** 비공개 테스트 통과 후 공개 테스트(Open Testing) 또는 프로덕션(Production)으로 단계적 승격.
- [ ] **[사용자]** 프로덕션 출시 전 미준비 자산(정식 앱 아이콘, 피처 그래픽) 완성.

## I. 출시 후

- [ ] **[참고]** mapping.txt는 CI가 자동 업로드한 GitHub Release 첨부 파일에서 다운로드하여 Play Console의 ProGuard 매핑 업로드 영역에 별도 등록.
- [ ] **[참고]** 매 릴리즈마다 [RELEASE.md](../RELEASE.md) §4 절차로 새 태그를 만들면 자동 빌드/Release 생성. AAB와 출시 노트만 Play Console에 업로드.

---

## 진행 상황 요약 (저장소 측 완료 항목)

저장소가 끝낸 일:

- [x] 코드 식별값 확정 (`io.jeiel85.dockmode`)
- [x] 한국어/영어 메타데이터 (짧은·긴 설명, 카테고리, 데이터 보안, 콘텐츠 등급) 초안
- [x] 개인정보 처리방침 공개 URL 정비
- [x] 키스토어 / Play App Signing 가이드
- [x] 환경변수 기반 release signingConfig + CI 시크릿 디코드 단계
- [x] 폰 스크린샷 5종 (실기기 캡처)
- [x] 출시 노트 v0.1.0 한·영

남은 일은 모두 사용자 액션이며 위 A~I에 정리되어 있습니다.
