# RELEASE.md

# DockMode 릴리즈 및 배포 절차

## 1. 버전 정책

- SemVer 형식: `vX.Y.Z`
- Android `versionName`은 태그 버전과 일치해야 한다.
- Android `versionCode`는 이전 릴리즈보다 증가해야 한다.
- 버전 관련 파일은 [AGENTS.md](AGENTS.md) §20의 *Version Files* 항목을 모두 동시에 갱신한다.

## 2. 릴리즈 전 로컬 검증

```bash
./gradlew ktlintCheck
./gradlew detekt
./gradlew testDebugUnitTest
./gradlew assembleDebug
./gradlew bundleRelease
```

실행하지 못한 명령은 성공으로 기록하지 말고 `HISTORY.md`에 실패 사유를 기록한다. 로컬에 Gradle/Android SDK가 없는 환경에서는 GitHub Actions 결과로 대체 검증한다.

## 3. 산출물

CI는 태그 푸시 시 다음 파일을 자동으로 GitHub Release에 첨부한다.

| 산출물 (파일명) | 목적 |
|---|---|
| `dockmode-vX.Y.Z-debug.apk` | 내부 설치 테스트용 디버그 APK |
| `dockmode-vX.Y.Z-release.aab` | Play Store 업로드용 (현재 unsigned — §7 참고) |
| `dockmode-vX.Y.Z-mapping.txt` | R8 deobfuscation / Play Console 업로드용 |
| `docs/releases/vX.Y.Z.md` | GitHub Release 본문 (수기) |
| `play_store/release_notes/vX.Y.Z.txt` | Play Console "What's new" (수기) |

CI의 `Collect release artifacts` 단계가 빌드 산출물을 위 파일명으로 정규화한 뒤 `release-assets/` 폴더에 모은다.

## 4. 새 버전 만들기 (태그 트리거 자동 릴리즈)

1. 코드/리소스/문서 변경을 모두 `main`에 푸시하고, 이전 단계의 CI가 성공했는지 확인한다.
2. 버전 파일 동기화 (현재는 `app/build.gradle.kts`의 `versionName`과 `versionCode`).

   ```bash
   grep -nE "versionName|versionCode" app/build.gradle.kts
   ```

3. (선택) Release 본문을 직접 쓰려면 [docs/releases/vX.Y.Z.md](docs/releases/) 작성. 형식은 [docs/releases/README.md](docs/releases/README.md) 참고.
4. (선택) Play Store에 올릴 거라면 [play_store/release_notes/vX.Y.Z.txt](play_store/release_notes/) 작성. 형식은 [play_store/release_notes/README.md](play_store/release_notes/README.md) 참고.
5. `CHANGELOG.md`와 `HISTORY.md`를 갱신하고 커밋·푸시.
6. 태그 생성 및 푸시.

   ```bash
   git tag -a vX.Y.Z -m "DockMode vX.Y.Z"
   git push origin vX.Y.Z
   ```

7. 태그 푸시가 Android CI를 다시 실행하며 다음을 수행한다.
   - Debug APK / Release AAB / mapping.txt 빌드
   - 산출물에 태그가 들어간 파일명으로 복사
   - `docs/releases/<tag>.md`가 있으면 그 내용을 Release 본문으로, 없으면 GitHub 자동 changelog 사용
   - 산출물을 GitHub Release에 첨부

## 4-1. 바탕화면으로 산출물 내보내기

같은 머신에서 관리하는 다른 Android 프로젝트(markleaf, lumina-daily 등)와 동일한 규칙으로 사용자 바탕화면에 릴리즈 묶음을 둔다. Play Store 업로드 시 빠르게 끌어다 쓰기 위한 용도다.

규칙:

| 파일명 | 출처 |
|---|---|
| `dockmode-vX.Y.Z.aab` | GitHub Release의 `dockmode-vX.Y.Z-release.aab`를 이름만 단순화해서 복사 |
| `dockmode-vX.Y.Z-release-notes.txt` | 저장소의 `play_store/release_notes/vX.Y.Z.txt`를 그대로 복사 |

자동화:

```powershell
# 최신 릴리즈 자동 감지
powershell -ExecutionPolicy Bypass -File scripts\export-release-to-desktop.ps1

# 특정 태그 지정
powershell -ExecutionPolicy Bypass -File scripts\export-release-to-desktop.ps1 -Tag v0.1.0
```

스크립트 동작:

1. `gh release download <tag> --pattern dockmode-<tag>-release.aab`로 AAB 다운로드
2. 바탕화면(`[Environment]::GetFolderPath('Desktop')`)에 `dockmode-<tag>.aab` 로 복사
3. `play_store/release_notes/<tag>.txt`를 바탕화면에 `dockmode-<tag>-release-notes.txt` 로 복사

체크:

- [ ] 바탕화면 AAB 파일 크기 > 0
- [ ] 바탕화면 release notes에 `<ko-KR>`, `<en-US>` 블록 모두 포함

> **참고**: 스크립트는 로컬 보조 도구다. CI는 사용자 바탕화면에 접근할 수 없으므로 이 단계는 사람이 실행한다. mapping.txt가 추가로 필요한 경우 `gh release download <tag> --pattern 'dockmode-*-mapping.txt'`로 별도로 받는다.

## 5. GitHub Actions와 Release 확인

```bash
gh run list --limit 10
gh run watch <RUN_ID> --exit-status
gh run view <RUN_ID> --log-failed
gh release view vX.Y.Z
gh release download vX.Y.Z --pattern 'dockmode-*-release.aab'
```

확인 항목:

- [ ] CI 모든 단계 성공
- [ ] Release에 APK/AAB/mapping 첨부 확인
- [ ] 각 파일 크기가 0이 아니고 정상 다운로드되는지
- [ ] Release 본문이 `docs/releases/<tag>.md` 또는 자동 changelog로 채워졌는지
- [ ] 태그가 의도한 커밋을 가리키는지
- [ ] `CHANGELOG.md`와 Release 노트가 모순되지 않는지

## 6. Play Store 체크리스트

- [ ] 앱명, 패키지명, 아이콘, 스크린샷이 실제 앱과 일치 (앱 아이콘은 현재 임시 어댑티브 아이콘 → 정식 디자인 필요)
- [ ] 광고 없음 상태가 실제 빌드와 일치 (광고 SDK 미포함 확인)
- [ ] 네트워크 권한 없음 상태가 실제 Manifest와 일치 (`uses-permission`은 `READ_CALENDAR`만 존재)
- [ ] 캘린더 권한 사용 이유가 스토어 설명과 개인정보 안내에 반영
- [ ] Release AAB 생성 커밋과 GitHub Release 태그 일치
- [ ] mapping.txt 보존 (Play Console에 별도 업로드)
- [ ] 실제 기기에서 StandbyActivity와 DreamService 수동 검증
- [ ] 릴리즈 서명 설정 확인 (현재 저장소에는 keystore가 없으며, CI에서 unsigned AAB가 생성됨 → 실제 업로드 전 Play App Signing 구성 필요)
- [ ] Play Store 출시 전 앱명 `DockMode` 상표/중복 검토 (TODO, ADR-004 참조)

## 7. 현재 빌드 상태 메모

- v0.1.0 시점 `versionName=0.1.0`, `versionCode=1`.
- 릴리즈 키스토어가 저장소에 없으므로 `bundleRelease`는 **unsigned AAB**를 생성한다. Play Store 업로드 전 키스토어 구성과 서명 설정이 별도 작업으로 필요하다.
- CI 워크플로는 `permissions: contents: write`로 Release 생성 권한을 가진다. 시크릿 추가는 필요 없다 (`GITHUB_TOKEN` 자동 주입).

## 8. 롤백 / 잘못된 태그 정리

태그를 잘못 푼 경우:

1. GitHub Release를 삭제한다 (`gh release delete vX.Y.Z --yes`).
2. 원격 태그를 삭제한다 (`git push origin :refs/tags/vX.Y.Z`). **사용자 승인 필수** ([AGENTS.md](AGENTS.md) §9).
3. 로컬 태그를 삭제한다 (`git tag -d vX.Y.Z`).
4. 문제를 수정한 뒤 동일한 절차로 다시 태그를 만든다.
