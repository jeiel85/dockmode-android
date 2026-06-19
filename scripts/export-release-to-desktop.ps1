<#
.SYNOPSIS
    DockMode 릴리즈 산출물(AAB + Play Store 노트)을 사용자의 바탕화면에 복사합니다.

.DESCRIPTION
    로컬 Gradle release bundle 산출물을 바탕화면에 `dockmode-<tag>.aab` 로 저장하고,
    저장소의 `play_store/release_notes/<tag>.txt`를 `dockmode-<tag>-release-notes.txt` 로 복사합니다.

    같은 머신에 있는 다른 Android 프로젝트(markleaf, lumina-daily 등)의 바탕화면 배치 규칙을 따릅니다.

.PARAMETER Tag
    릴리즈 태그 (예: v0.1.0). 생략하면 가장 최신 태그를 자동 탐지합니다.

.PARAMETER Repo
    이전 버전 호환용 매개변수입니다. GitHub Release에서 AAB를 받지 않으므로 사용하지 않습니다.

.PARAMETER OutDir
    저장 경로. 기본값은 현재 사용자의 바탕화면 (`[Environment]::GetFolderPath('Desktop')`).

.EXAMPLE
    powershell -File scripts\export-release-to-desktop.ps1 -Tag v0.1.0

.EXAMPLE
    powershell -File scripts\export-release-to-desktop.ps1
    # 가장 최신 태그 자동 사용
#>

[CmdletBinding()]
param(
    [Parameter()]
    [string]$Tag,

    [Parameter()]
    [string]$Repo = 'jeiel85/dockmode-android',

    [Parameter()]
    [string]$OutDir = ([Environment]::GetFolderPath('Desktop'))
)

$ErrorActionPreference = 'Stop'

$projectRoot = Split-Path -Parent $PSScriptRoot

if (-not $Tag) {
    Write-Host "태그가 지정되지 않아 로컬 Git 태그를 탐지합니다..."
    $Tag = (& git -C $projectRoot describe --tags --abbrev=0)
    if (-not $Tag) {
        throw "로컬 Git 태그가 없습니다. 먼저 'git tag -a vX.Y.Z -m ...'로 태그를 준비하세요."
    }
    Write-Host "감지된 태그: $Tag"
}

if ($Tag -notmatch '^v\d+\.\d+\.\d+') {
    throw "태그 형식이 올바르지 않습니다: $Tag. SemVer 형식 'vX.Y.Z'를 사용하세요."
}

$notesSrc = Join-Path $projectRoot "play_store\release_notes\$Tag.txt"
if (-not (Test-Path $notesSrc)) {
    throw "Play Store 릴리즈 노트를 찾을 수 없습니다: $notesSrc. 먼저 play_store/release_notes/$Tag.txt를 작성하세요."
}

if (-not (Test-Path $OutDir)) {
    throw "출력 디렉터리를 찾을 수 없습니다: $OutDir"
}

$bundleDir = Join-Path $projectRoot "app\build\outputs\bundle\release"
$aabSrc = Get-ChildItem -Path $bundleDir -Filter "*.aab" -File -ErrorAction SilentlyContinue |
    Sort-Object LastWriteTime -Descending |
    Select-Object -First 1

if (-not $aabSrc) {
    $missingSigningEnv = @(
        'DOCKMODE_KEYSTORE_PATH',
        'DOCKMODE_KEYSTORE_PASSWORD',
        'DOCKMODE_KEY_PASSWORD',
        'DOCKMODE_KEY_ALIAS'
    ) | Where-Object { [string]::IsNullOrWhiteSpace([Environment]::GetEnvironmentVariable($_)) }

    if ($missingSigningEnv.Count -gt 0) {
        Write-Warning "릴리즈 서명 환경변수가 비어 있습니다: $($missingSigningEnv -join ', '). 서명된 Play Console AAB가 필요하면 먼저 설정하세요."
    }

    Write-Host "[1/3] 로컬 release AAB 빌드 중..."
    Push-Location $projectRoot
    try {
        & .\gradlew.bat bundleRelease
        if ($LASTEXITCODE -ne 0) {
            throw "Gradle bundleRelease 실패"
        }
    } finally {
        Pop-Location
    }

    $aabSrc = Get-ChildItem -Path $bundleDir -Filter "*.aab" -File -ErrorAction SilentlyContinue |
        Sort-Object LastWriteTime -Descending |
        Select-Object -First 1
}

if (-not $aabSrc) {
    throw "Release AAB를 찾을 수 없습니다: $bundleDir"
}

$aabDst = Join-Path $OutDir "dockmode-$Tag.aab"
$notesDst = Join-Path $OutDir "dockmode-$Tag-release-notes.txt"

Write-Host "[1/3] Play Console AAB 준비: $($aabSrc.FullName)"
Write-Host "[2/3] 바탕화면으로 AAB 복사: $aabDst"
Copy-Item -Path $aabSrc.FullName -Destination $aabDst -Force

Write-Host "[3/3] 바탕화면으로 Play Store 노트 복사: $notesDst"
Copy-Item -Path $notesSrc -Destination $notesDst -Force

$result = Get-Item $aabDst, $notesDst | Select-Object Name, Length, LastWriteTime
$result | Format-Table -AutoSize

Write-Host ""
Write-Host "완료: dockmode $Tag 산출물이 바탕화면에 준비되었습니다." -ForegroundColor Green
Write-Host "  • AAB:   $aabDst"
Write-Host "  • 노트:  $notesDst"
