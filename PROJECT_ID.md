# PROJECT_ID.md

# DockMode 프로젝트 식별값

이 문서는 에이전트가 저장소 생성, 패키지 설정, 브랜치/이슈명, 릴리즈 산출물 이름을 일관되게 사용할 수 있도록 하는 기준 문서입니다.

## 확정 기준값

| 항목 | 값 | 용도 |
|---|---|---|
| Project Name | `DockMode` | 앱 작업명, 문서 제목, 사용자 노출 이름 |
| Project Code | `DKM-ANDROID` | 이슈 prefix, 작업 로그, 내부 문서 태그 |
| Repository ID | `dockmode-android` | GitHub 저장소 slug |
| Repository URL | `https://github.com/jeiel85/dockmode-android.git` | Git remote origin 기본값 |
| Main Branch | `main` | 기본 브랜치 |
| Android Application ID | `io.jeiel85.dockmode` | Android 설치/배포 식별자 |
| Android Namespace | `io.jeiel85.dockmode` | Gradle namespace |
| Kotlin Package Path | `app/src/main/java/io/jeiel85/dockmode` | 소스 루트 패키지 경로 |
| Artifact Prefix | `dockmode` | APK/AAB/릴리즈 산출물 접두어 |

## 이름 선정 이유

- `dockmode-android`는 앱의 핵심 사용 상황인 충전 거치대(dock)와 Android 전용 구현 범위를 함께 드러냅니다.
- `DockMode`는 iPhone StandBy 명칭을 직접 사용하지 않으면서도 제품 정체성을 설명합니다.
- `io.jeiel85.dockmode`는 GitHub 사용자명 기반의 고유성이 높은 Android 식별자입니다.
- `DKM-ANDROID`는 문서, 이슈, 브랜치, 릴리즈 기록에서 사람이 읽기 쉬운 내부 프로젝트 코드입니다.

## 에이전트 적용 규칙

- 새 Android 프로젝트를 만들 때 `namespace`와 `applicationId`는 모두 `io.jeiel85.dockmode`로 설정합니다.
- 저장소를 초기화할 때 원격 origin은 `https://github.com/jeiel85/dockmode-android.git`로 설정합니다.
- GitHub Issue 제목이나 브랜치명에 프로젝트 구분이 필요하면 `DKM-ANDROID`를 사용합니다.
- 빌드 산출물 이름은 가능하면 `dockmode-debug.apk`, `dockmode-release.aab`처럼 `dockmode` 접두어를 사용합니다.
- Play Store 출시 전에는 앱명 `DockMode`의 상표, 중복 앱명, 아이콘/화면 유사성 검토를 별도 이슈로 진행합니다.
