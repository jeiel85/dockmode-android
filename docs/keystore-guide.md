---
title: 업로드 키스토어 / Play App Signing 가이드
description: DockMode AAB를 서명하고 Play Console에 처음 업로드하기 위한 단계별 절차입니다.
permalink: /keystore-guide/
---

# 업로드 키스토어 / Play App Signing 가이드

이 문서는 DockMode를 Play Store에 처음 출시할 때 한 번 수행하는 절차입니다. 이후 일반 릴리즈 절차는 [RELEASE.md](https://github.com/jeiel85/dockmode-android/blob/main/RELEASE.md)를 따릅니다.

## 한눈에 보기

```
[사용자] keytool로 업로드 키스토어 생성 → 안전한 곳에 보관
[사용자] Play Console에서 앱 만들고 Play App Signing 옵트인 + 업로드 인증서 등록
[사용자] GitHub Actions Secrets에 키스토어 4종 등록
[CI]    push/tag 푸시 시 시크릿이 있으면 자동 서명된 AAB 생성
[사용자] AAB를 Play Console 내부 테스트 트랙에 업로드
```

## 1. 업로드 키스토어 생성 (사용자 로컬, 1회)

**중요**: 이 키스토어는 Play App Signing의 **업로드 키**로만 사용됩니다. Play App Signing을 옵트인하면 실제 앱 서명 키는 Google이 관리하므로, 업로드 키 분실 시 Google 지원으로 재발급이 가능합니다. 그래도 키스토어와 비밀번호는 **분실하지 않도록 안전한 곳에 별도 백업**하세요.

### 1-1. keytool 실행

```powershell
# 사용자가 직접 실행. 비밀번호 4종(스토어/키)을 안전한 비밀번호 관리자에 보관할 것.
$keystorePath = "$env:USERPROFILE\dockmode-upload.jks"
keytool -genkeypair `
  -v `
  -keystore $keystorePath `
  -alias dockmode-upload `
  -keyalg RSA `
  -keysize 4096 `
  -validity 36500 `
  -storetype JKS
```

질문에 답할 때 권장값:

| 항목 | 권장값 |
|---|---|
| Keystore password | 강력한 임의 문자열, 비밀번호 관리자에 저장 |
| Key password | Keystore password와 동일하게 두면 운영 단순 |
| First and Last Name | 본인 또는 조직 이름 |
| Organization Unit | (선택) |
| Organization | (선택) |
| City, State, Country | 거주지/사업장 정보 |

검증:

```powershell
keytool -list -v -keystore $keystorePath -alias dockmode-upload
```

SHA-1 / SHA-256 fingerprint가 출력되면 정상.

### 1-2. 백업

- 키스토어 파일과 비밀번호 4종(스토어/키)을 **최소 2곳**에 분산 보관: 비밀번호 관리자(1Password, Bitwarden 등) + 오프라인 백업(암호화된 USB 등).
- 저장소에 절대 커밋하지 않습니다. `.gitignore`에 `*.jks`, `*.keystore`가 포함되어 있는지 확인.

## 2. Play Console에서 앱 생성 + Play App Signing 옵트인 (사용자, 1회)

1. <https://play.google.com/console>에 본인 개발자 계정으로 로그인.
2. 좌측 상단 **앱 만들기**.
3. 입력:
   - **앱 이름**: `DockMode`
   - **기본 언어**: 한국어 (`ko-KR`)
   - **앱 또는 게임**: 앱
   - **무료 또는 유료**: 무료
   - **선언**: 약관 / 미국 수출 법규 / Play 앱 정책 모두 체크
4. **앱 만들기** 클릭.
5. 좌측 메뉴 **출시 → 설정 → 앱 무결성** 진입.
6. **앱 서명** 섹션에서 **Google이 앱 서명 키 관리** 옵션 선택 (Play App Signing 옵트인).
7. **업로드 키 인증서**를 등록할 차례에서 두 가지 옵션이 있습니다.
   - (A) **이번에는 일단 첫 AAB 업로드 시 자동 등록** — 권장. 1단계에서 만든 키스토어로 서명된 첫 AAB를 업로드하면 자동으로 업로드 키로 등록됩니다.
   - (B) 수동 등록: keytool로 인증서를 export하여 업로드.
     ```powershell
     keytool -export -rfc `
       -keystore $env:USERPROFILE\dockmode-upload.jks `
       -alias dockmode-upload `
       -file dockmode-upload-cert.pem
     ```
     생성된 `dockmode-upload-cert.pem`을 Play Console에 업로드.

## 3. GitHub Actions Secrets 등록 (사용자, 1회)

Settings → Secrets and variables → Actions → **New repository secret** 으로 다음 4개를 등록합니다.

| 시크릿 이름 | 값 |
|---|---|
| `DOCKMODE_KEYSTORE_BASE64` | 키스토어 파일을 base64로 인코딩한 문자열 |
| `DOCKMODE_KEYSTORE_PASSWORD` | keystore password |
| `DOCKMODE_KEY_PASSWORD` | key password |
| `DOCKMODE_KEY_ALIAS` | `dockmode-upload` (1단계 alias 값) |

키스토어 base64 인코딩:

```powershell
$bytes = [System.IO.File]::ReadAllBytes("$env:USERPROFILE\dockmode-upload.jks")
[Convert]::ToBase64String($bytes) | Set-Clipboard
# 클립보드에 복사된 문자열을 DOCKMODE_KEYSTORE_BASE64 시크릿 값으로 붙여넣기
```

검증 (선택, 디코딩이 원본과 같은지 확인):

```powershell
$b64 = Get-Clipboard
[System.IO.File]::WriteAllBytes("$env:TEMP\dockmode-decode.jks",[Convert]::FromBase64String($b64))
keytool -list -v -keystore $env:TEMP\dockmode-decode.jks -alias dockmode-upload
Remove-Item $env:TEMP\dockmode-decode.jks
```

## 4. CI 워크플로 동작 (자동)

`app/build.gradle.kts`의 release `signingConfig`는 다음 환경변수가 모두 있을 때만 적용됩니다.

- `DOCKMODE_KEYSTORE_PATH`
- `DOCKMODE_KEYSTORE_PASSWORD`
- `DOCKMODE_KEY_PASSWORD`
- `DOCKMODE_KEY_ALIAS`

GitHub Actions 워크플로는 3단계의 시크릿이 등록되어 있으면 자동으로:

1. `DOCKMODE_KEYSTORE_BASE64`를 디코드해 임시 파일로 저장
2. 위 4종 환경변수를 채워 `./gradlew bundleRelease` 실행
3. 산출물(`dockmode-vX.Y.Z-release.aab`)을 GitHub Release에 첨부

시크릿이 없으면 release AAB는 unsigned로 빌드됩니다 (현재 동작 유지). 이 경우 Play Console 업로드 전 사용자가 로컬에서 jarsigner / apksigner로 직접 서명해야 합니다.

## 5. 첫 AAB 업로드 (사용자, 매 출시)

1. CI가 만든 서명된 AAB를 다운로드 (`gh release download vX.Y.Z --pattern 'dockmode-*-release.aab'`).
   - 또는 [scripts/export-release-to-desktop.ps1](https://github.com/jeiel85/dockmode-android/blob/main/scripts/export-release-to-desktop.ps1)로 바탕화면에 내보내기.
2. Play Console → **출시 → 테스트 → 내부 테스트** → **새 버전 만들기**.
3. AAB 업로드. 첫 업로드 시 Play App Signing이 업로드 키 인증서를 자동 등록.
4. 출시 노트는 [play_store/release_notes/vX.Y.Z.txt](https://github.com/jeiel85/dockmode-android/tree/main/play_store/release_notes)에서 복사.
5. 내부 테스터 그룹 추가 (본인 Google 계정만 등록해도 충분).
6. **검토 시작** → 내부 테스트 트랙으로 출시.
7. 테스트 링크가 발급되면 본인 기기에서 설치 후 동작 확인.

## 6. 첫 AAB 검증이 끝난 뒤 (사용자)

- 비공개 테스트 → 공개 테스트 → 프로덕션 순으로 단계적으로 승격합니다.
- 프로덕션 출시 전 [play_store/listing/](https://github.com/jeiel85/dockmode-android/tree/main/play_store/listing) 의 메타데이터, 스크린샷, 데이터 보안/콘텐츠 등급 양식을 모두 채웠는지 확인합니다.

## 7. 분실 시 대응

| 상황 | 대응 |
|---|---|
| 업로드 키 분실 | Play Console의 **앱 무결성 → 업로드 키 재설정 요청**으로 Google에 새 업로드 키 등록 요청. 본인 인증과 며칠 대기. |
| 앱 서명 키 분실 | Play App Signing을 옵트인했다면 Google이 보관 중이라 문제 없음. |
| 시크릿 노출 | GitHub Settings에서 즉시 회전(rotate). 새 키스토어 생성 후 시크릿 4종 갱신. Play Console에 새 업로드 키 등록. |
