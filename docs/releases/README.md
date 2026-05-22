# Release notes

GitHub Release 본문(release body)을 손으로 쓰고 싶을 때 사용합니다.

## 흐름

1. 코드 작업 완료 후 `main`에 푸시 → Android CI가 lint/test/build를 검증.
2. (선택) **`docs/releases/vX.Y.Z.md`** 파일을 미리 작성해 둡니다. 작성하지 않으면 GitHub 자동 생성 changelog(이전 태그 이후 커밋 목록)가 본문으로 쓰입니다.
3. (선택) Play Store용 본문이 필요하면 [../../play_store/release_notes/](../../play_store/release_notes/)에 `vX.Y.Z.txt`도 함께 작성합니다.
4. 버전 변경 사항을 모두 푸시한 뒤 태그를 만듭니다.

   ```bash
   git tag -a vX.Y.Z -m "DockMode vX.Y.Z"
   git push origin vX.Y.Z
   ```

5. 태그 푸시가 다음을 자동으로 수행합니다.
   - Android 빌드 (Debug APK + Release AAB + R8 mapping)
   - 산출물에 태그 이름이 들어간 파일명으로 정규화
     (`dockmode-vX.Y.Z-debug.apk`, `dockmode-vX.Y.Z-release.aab`, `dockmode-vX.Y.Z-mapping.txt`)
   - GitHub Release 생성 또는 갱신
   - 본 문서가 있으면 그 내용을 Release 본문으로 사용
   - 없으면 GitHub 자동 changelog 사용

## 파일명 규칙

- 정확히 태그 이름과 일치: `v0.1.0.md`, `v1.0.0.md`
- 접두사 `v` 포함, 마침표 `.` 그대로

## 형식 (template)

```markdown
## 🛏️ vX.Y.Z — 한 줄 부제

### 🎯 핵심 변경

(2~3줄 요약)

### 🆕 추가
- 새 기능 1
- 새 기능 2

### 🛠 변경 / 수정
- 동작 변경 또는 수정 1
- 동작 변경 또는 수정 2

### 🧹 정리 / 문서 / 빌드
- 내부 정리, 문서 갱신, CI 변경 등

### ✅ 검증
- 로컬:
- CI:
- 실기기:

### 다운로드
- 📦 Play Store 업로드용: `dockmode-vX.Y.Z-release.aab`
- 🤖 Android 설치 APK: `dockmode-vX.Y.Z-debug.apk`
- 🗺️ R8 deobfuscation: `dockmode-vX.Y.Z-mapping.txt`
```

## 정책

- **반드시 한 줄 부제**가 있는 제목으로 작성합니다 (`vX.Y.Z — 주제`).
- 본문 언어는 한국어 기본, 필요 시 영어 병기 가능.
- 검증하지 않은 항목은 성공으로 기록하지 않습니다 (`HISTORY.md`와 일관성 유지).
- Debug APK는 디버깅용으로만 사용합니다. 실제 사용자 배포는 서명된 Release AAB로 합니다 (현재 서명 미설정 → [RELEASE.md](../../RELEASE.md) 참조).
- 다운로드 섹션은 매 릴리즈 동일 (산출물 구성이 동일하므로).

## GitHub Release 노트와 Play Store 노트의 관계

| 위치 | 용도 | 형식 |
|---|---|---|
| `docs/releases/vX.Y.Z.md` | GitHub Release 본문 | 마크다운, 한국어 풍부한 본문 |
| `play_store/release_notes/vX.Y.Z.txt` | Play Console "What's new" | 평문, BCP-47 언어 태그, 언어당 500자 제한 |

같은 변경 사항이지만 형식·길이·언어가 다릅니다. 두 파일 다 함께 유지합니다.
