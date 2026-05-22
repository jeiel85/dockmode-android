---
title: DockMode 앱명 / 상표 사전 리서치
description: Play Store 출시 전 앱명 충돌·상표 위험·차별화 키워드 검토 메모.
permalink: /branding-research/
---

# DockMode 앱명 / 상표 사전 리서치

> 작성일: 2026-05-23. 정식 상표 검색·법률 자문은 별도 절차로 진행하며, 본 문서는 의사결정 보조 자료입니다. 최종 결정은 사용자(및 필요 시 변호사/변리사)가 수행합니다. 참고: [ADR-004](../DECISIONS.md).

## 1. 결론 요약

- **Play Store 정확명 `DockMode` 충돌**: 발견되지 않음 (2026-05-23 시점 영문 검색 기준).
- **상표 등록 위험**: USPTO 자동 검색은 결과를 반환하지 않았으나 별도 [USPTO TSDR](https://tsdr.uspto.gov/) + 한국 [KIPRIS](http://www.kipris.or.kr/) 직접 조회가 필요. 영문 단어 합성어로 누군가 선등록했을 가능성 배제 불가.
- **카테고리 내 강력 경쟁자**: `DockScreen - StandBy Mode`, `StandBy Mode: Clock & Widgets`, `Dock Station Digital Clock` 등 다수. 앱명만으로는 차별화 어려움 → 부제(subtitle)로 보강 권장.

## 2. Play Store 검색 결과 (2026-05-23)

쿼리: `"DockMode" site:play.google.com`, `"Dock Mode" Android app Play Store clock charging dashboard`

| 앱 | 패키지 | 컨셉 | DockMode와의 충돌 |
|---|---|---|---|
| [DockScreen - StandBy Mode](https://play.google.com/store/apps/details?id=com.bytenine.dockscreen) | `com.bytenine.dockscreen` | 충전 중 시계·알림 표시 | **매우 유사한 컨셉** — 핵심 가치 제안 차별화 필요 |
| [StandBy Mode: Clock & Widgets](https://play.google.com/store/apps/details?id=br.com.zetabit.ios_standby) | `br.com.zetabit.ios_standby` | iOS StandBy 미러링 | iOS 미러링 노선이라 DockMode와 디자인 방향 다름 |
| [Dock Station Digital Clock](https://dock-station-digital-clock.en.uptodown.com/android) | (Play 외 배포) | 충전 시 자동 시계 | 이름은 다르나 컨셉 겹침 |
| [Docks](https://play.google.com/store/apps/details?id=com.infinityapps.docks) | `com.infinityapps.docks` | 앱 도크/런처 | 컨셉 무관 |
| [Dock](https://play.google.com/store/apps/details?id=com.mawalog.dock) | `com.mawalog.dock` | 도크 유틸 | 컨셉 무관 |
| [DockUtils](https://play.google.com/store/apps/details?id=com.dhx.dockutils) | `com.dhx.dockutils` | 도크 유틸 | 컨셉 무관 |

- **정확명 `DockMode`로 등록된 앱은 발견되지 않음.**
- 단, Play Store 검색은 부분 일치도 노출하므로, 사용자가 "DockMode"로 검색했을 때 위 경쟁 앱들이 상위 노출될 가능성 있음.

## 3. 상표 등록 사전 점검 (필요)

검색만으로는 미확정. 출시 전 다음 데이터베이스를 사용자가 직접 조회:

- 미국: [USPTO TSDR](https://tsdr.uspto.gov/) — Mark: `DockMode`, Class 9(소프트웨어) / Class 42(SaaS)
- 한국: [KIPRIS](http://www.kipris.or.kr/) — 상표 검색 → `DockMode`, `독모드`
- EU: [EUIPO eSearch](https://www.tmdn.org/tmview/) — `DockMode`, Nice Class 9 / 42
- 일본: [J-PlatPat](https://www.j-platpat.inpit.go.jp/) — `DockMode`

`Dock`만 단독 등록된 사례는 매우 흔하므로 합성어 `DockMode`도 등록되어 있을 수 있습니다. 결과에 따라:

- (안전) DockMode 등록 사례 없음 → 그대로 진행, 필요 시 본인 명의 상표 출원 검토
- (충돌) DockMode 또는 매우 유사한 등록상표 존재 → 후보명으로 전환

## 4. 후보명 (충돌 시 fallback)

차별화 + 한국어/영어 양쪽에서 자연스러운 후보를 미리 준비. 채택 시 [PROJECT_ID.md](../PROJECT_ID.md), [AGENTS.md](../AGENTS.md), `app/build.gradle.kts`의 `applicationId`, 모든 리소스를 동시 갱신해야 합니다.

| 후보 | 컨셉 | 비고 |
|---|---|---|
| `DeskDock` | 책상 거치 강조 | Play Store 내 "DeskDock" 동명 앱(USB 연결 도구) 존재 가능성 — 사전 검색 필요 |
| `StandClock` | 거치 + 시계 | 일반명이라 약함, 상표 등록 어려움 |
| `DockGlance` | 충전 중 한눈에 | 합성어, 독창성 중간 |
| `BedsideDock` | 침대맡 거치 강조 | 사용 맥락 명확 |
| `JeielDock` | 개발자명 prefix | 정체성 분명, 상표 충돌 위험 최소, 브랜드 약함 |

## 5. 보조 카피 / 부제 (앱명과 함께 표시)

앱명 자체 변경 없이도 Play Store **앱 이름(50자)** 영역에서 부제로 차별화 가능:

- `DockMode — 거치대 시계 & 일정`
- `DockMode: Standby Clock & Calendar`
- `DockMode - 야간 거치 시계`

> Play Console 정책상 앱 이름에 키워드를 과도하게 끼워 넣는 keyword stuffing은 거부 사유가 됩니다. 부제는 1개 정도, 자연스러운 한 문장으로 유지하는 것이 안전합니다.

## 6. 권장 진행 방향

1. (사용자) 위 §3 데이터베이스에서 `DockMode` / `독모드` 충돌 여부 확인 — **출시 전 필수**.
2. 충돌 없으면 `DockMode`로 진행. [ADR-004](../DECISIONS.md) 후속 결정으로 "정식 명칭 승격" ADR-010을 추가.
3. 충돌이 있으면 §4 후보 중 사용자가 선택 → 모든 식별값/리소스/문서 일괄 갱신.
4. Play Console 앱 이름은 `DockMode - 거치대 시계 & 일정` 형태로 부제를 붙여 검색 노출 보강.
5. 본인 명의 상표 등록은 출시 후 사용자 의사 결정에 따라 별도 절차.

## Sources

- [DockScreen - StandBy Mode (Play Store)](https://play.google.com/store/apps/details?id=com.bytenine.dockscreen)
- [StandBy Mode: Clock & Widgets (Play Store)](https://play.google.com/store/apps/details?id=br.com.zetabit.ios_standby)
- [Dock Station Digital Clock (Uptodown)](https://dock-station-digital-clock.en.uptodown.com/android)
- [USPTO Trademark Status & Document Retrieval](https://tsdr.uspto.gov/)
- [KIPRIS 한국특허정보원](http://www.kipris.or.kr/)
- [EUIPO eSearch](https://www.tmdn.org/tmview/)
- [J-PlatPat (Japan)](https://www.j-platpat.inpit.go.jp/)
