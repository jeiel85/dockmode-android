---
title: DockMode
description: Android 충전 거치대에서 시간, 날짜, 오늘 일정을 보여 주는 로컬 우선 대시보드 앱입니다.
---

<div class="page-shell">
  <section class="hero">
    <div class="hero-inner">
      <div class="topbar">
        <a class="brand" href="{{ '/' | relative_url }}">
          <img src="{{ '/assets/dockmode-icon-512.png' | relative_url }}" alt="">
          <span>DockMode</span>
        </a>
        <nav class="nav" aria-label="주요 링크">
          <a href="https://github.com/jeiel85/dockmode-android">GitHub</a>
          <a href="https://github.com/jeiel85/dockmode-android/releases">Release</a>
          <a href="{{ '/privacy/' | relative_url }}">Privacy</a>
        </nav>
      </div>

      <p class="eyebrow">Android dock clock and calendar</p>
      <h1>DockMode</h1>
      <p class="hero-copy">
        충전 거치대에 올려 둔 Android 기기를 책상과 침대 옆에서 읽기 좋은 시계·일정 대시보드로 바꿉니다.
        Kotlin, Jetpack Compose, DreamService 기반의 네이티브 앱이며 네트워크 없이 로컬 캘린더만 사용합니다.
      </p>
      <div class="actions">
        <a class="button primary" href="https://github.com/jeiel85/dockmode-android/releases">릴리즈 보기</a>
        <a class="button" href="https://github.com/jeiel85/dockmode-android">소스 보기</a>
        <a class="button" href="{{ '/privacy/' | relative_url }}">개인정보 처리방침</a>
      </div>
    </div>
  </section>

  <section class="section compact" aria-label="핵심 특징">
    <div class="feature-grid">
      <div class="feature">
        <strong>거치대용 대형 시계</strong>
        <p>가로 전체화면에서 시간, 날짜, 충전 상태를 멀리서도 읽기 쉽게 표시합니다.</p>
      </div>
      <div class="feature">
        <strong>오늘 일정 한눈에</strong>
        <p>사용자가 허용한 경우 Android Calendar Provider에서 오늘 일정과 다음 일정을 읽습니다.</p>
      </div>
      <div class="feature">
        <strong>스크린세이버 모드</strong>
        <p>Android DreamService를 사용해 시스템 표준 충전 대기 화면 흐름에 맞춥니다.</p>
      </div>
      <div class="feature">
        <strong>로컬 우선 개인정보</strong>
        <p>광고, 분석, 로그인, 네트워크 권한 없이 필요한 데이터를 기기 안에서 처리합니다.</p>
      </div>
    </div>
  </section>

  <section class="section" aria-label="화면 미리보기">
    <h2>실제 화면</h2>
    <p class="lead">
      Galaxy S24에서 캡처한 현재 디자인입니다. Play Store 스크린샷과 README 미리보기는 같은 원본을 사용해
      등록정보와 실제 앱 화면이 어긋나지 않도록 관리합니다.
    </p>
    <div class="screens">
      <div>
        <div class="phone-pair">
          <img src="{{ '/screenshots/01-home.png' | relative_url }}" alt="DockMode 홈 화면">
          <img src="{{ '/screenshots/02-settings.png' | relative_url }}" alt="DockMode 설정 화면">
        </div>
        <p class="shot-caption">홈과 설정: 대기 화면 시작, 캘린더 권한, 스크린세이버 안내, 시계 스타일 선택.</p>
      </div>
      <div class="wide-shots">
        <img src="{{ '/screenshots/03-standby-minimal.png' | relative_url }}" alt="DockMode 미니멀 대기 화면">
        <img src="{{ '/screenshots/04-standby-digital.png' | relative_url }}" alt="DockMode 디지털 대기 화면">
        <img src="{{ '/screenshots/05-standby-calendar.png' | relative_url }}" alt="DockMode 캘린더 중심 대기 화면">
        <p class="shot-caption">대기 화면 3종: 야간 미니멀, 디지털 패널, 캘린더 중심 레이아웃.</p>
      </div>
    </div>
  </section>

  <section class="policy">
    <div class="section">
      <h2>권한은 작게, 화면은 분명하게</h2>
      <p class="lead">
        DockMode는 캘린더 표시를 켤 때만 <code>READ_CALENDAR</code> 권한을 요청합니다. 일정 제목과 시간은 화면 표시
        목적의 메모리 상태로만 사용하고, 외부 서버로 전송하지 않습니다. 네트워크 권한, 광고 SDK, 분석 SDK,
        crash reporting SDK는 현재 범위에 포함하지 않습니다.
      </p>
      <div class="actions">
        <a class="button primary" href="{{ '/privacy/' | relative_url }}">개인정보 처리방침 열기</a>
        <a class="button" href="https://github.com/jeiel85/dockmode-android/blob/main/play_store/listing/data-safety-form.md">Data Safety 초안</a>
      </div>
    </div>
  </section>

  <section class="section" aria-label="Play Store 준비 자료">
    <h2>Play Store 준비 자료</h2>
    <div class="asset-grid">
      <div class="asset">
        <img src="{{ '/assets/dockmode-icon-512.png' | relative_url }}" alt="DockMode 앱 아이콘">
        <strong>앱 아이콘</strong>
        <p><code>play_store/graphics/app-icon-512.png</code> · 512 x 512 PNG</p>
      </div>
      <div class="asset">
        <img src="{{ '/assets/dockmode-feature-graphic.png' | relative_url }}" alt="DockMode 피처 그래픽">
        <strong>피처 그래픽</strong>
        <p><code>play_store/graphics/feature-graphic-1024x500.png</code> · 1024 x 500 PNG</p>
      </div>
      <div class="asset">
        <img src="{{ '/screenshots/04-standby-digital.png' | relative_url }}" alt="DockMode Play Store 스크린샷 예시">
        <strong>스크린샷 세트</strong>
        <p><code>play_store/screenshots/</code> · 홈, 설정, 대기 화면 3종</p>
      </div>
    </div>
  </section>

  <section class="section" aria-label="문서 링크">
    <h2>문서</h2>
    <div class="doc-grid">
      <a class="doc-link" href="https://github.com/jeiel85/dockmode-android/blob/main/SPEC.md">
        제품 설계서
        <span>사용자 시나리오, 화면 정의, 성공 기준</span>
      </a>
      <a class="doc-link" href="https://github.com/jeiel85/dockmode-android/blob/main/TECH_SPEC.md">
        기술 설계서
        <span>Kotlin, Compose, Calendar Provider, DreamService 구조</span>
      </a>
      <a class="doc-link" href="https://github.com/jeiel85/dockmode-android/blob/main/RELEASE.md">
        릴리즈 절차
        <span>빌드, 서명, GitHub Release, Play Store 체크리스트</span>
      </a>
    </div>
  </section>

  <footer>
    DockMode는 Apple StandBy 화면을 복제하지 않고, Android 정책과 네이티브 API를 기준으로 만든 독립 앱입니다.
  </footer>
</div>
