# RELEASE.md

# DockMode 릴리즈 및 배포 절차

## 1. 버전 정책

- SemVer 형식: `vX.Y.Z`
- Android `versionName`은 태그 버전과 일치해야 한다.
- Android `versionCode`는 이전 릴리즈보다 증가해야 한다.

## 2. 릴리즈 전 로컬 검증

```bash
./gradlew ktlintCheck
./gradlew detekt
./gradlew testDebugUnitTest
./gradlew assembleDebug
./gradlew bundleRelease
```

실행하지 못한 명령은 성공으로 기록하지 말고 `HISTORY.md`에 실패 사유를 기록한다.

## 3. 산출물

| 산출물 | 목적 |
|---|---|
| Debug APK | 내부 설치 테스트 |
| Release AAB | Play Store 업로드 |
| mapping.txt | R8/ProGuard 디버깅 |
| GitHub Release note | 공개 릴리즈 설명 |
| Play Store release note | 스토어 노출 문구 |

## 4. GitHub Actions 확인

```bash
gh run list --limit 10
gh run view <RUN_ID> --log-failed
gh release view vX.Y.Z
```

확인 항목:

- CI 성공 여부
- APK/AAB 산출물 존재 여부
- 파일 크기가 0이 아닌지
- 릴리즈 노트와 CHANGELOG 일치 여부
- 태그가 버전 변경 커밋을 가리키는지

## 5. Play Store 체크리스트

- [ ] 앱명, 패키지명, 아이콘, 스크린샷이 실제 앱과 일치
- [ ] 광고 없음 상태가 실제 빌드와 일치
- [ ] 네트워크 권한 없음 상태가 실제 Manifest와 일치
- [ ] 캘린더 권한 사용 이유가 스토어 설명과 개인정보 안내에 반영
- [ ] Release AAB 생성 커밋과 GitHub Release 태그 일치
- [ ] mapping.txt 보존
- [ ] 실제 기기에서 StandbyActivity와 DreamService 수동 검증

## 6. 태그 생성

```bash
git status
grep -R "versionName\|versionCode" app/build.gradle.kts
git tag vX.Y.Z
git push origin vX.Y.Z
```

태그 생성 전 `CHANGELOG.md`, `HISTORY.md`, `RELEASE.md`가 실제 변경 내용과 일치하는지 확인한다.
