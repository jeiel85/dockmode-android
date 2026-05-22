<#
.SYNOPSIS
    DockMode 릴리즈 산출물(AAB + Play Store 노트)을 사용자의 바탕화면에 복사합니다.

.DESCRIPTION
    GitHub Release에서 `dockmode-<tag>-release.aab`를 받아 바탕화면에 `dockmode-<tag>.aab` 로 저장하고,
    저장소의 `play_store/release_notes/<tag>.txt`를 `dockmode-<tag>-release-notes.txt` 로 복사합니다.

    같은 머신에 있는 다른 Android 프로젝트(markleaf, lumina-daily 등)의 바탕화면 배치 규칙을 따릅니다.

.PARAMETER Tag
    릴리즈 태그 (예: v0.1.0). 생략하면 가장 최신 태그를 자동 탐지합니다.

.PARAMETER Repo
    GitHub 저장소. 기본값 `jeiel85/dockmode-android`.

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

if (-not (Get-Command gh -ErrorAction SilentlyContinue)) {
    throw "gh CLI를 찾을 수 없습니다. https://cli.github.com/ 에서 설치한 뒤 'gh auth login' 으로 인증하세요."
}

if (-not $Tag) {
    Write-Host "태그가 지정되지 않아 최신 릴리즈를 탐지합니다..."
    $Tag = (& gh release list --repo $Repo --limit 1 --json tagName --jq '.[0].tagName')
    if (-not $Tag) {
        throw "릴리즈가 없습니다. 먼저 'git tag -a vX.Y.Z -m ... && git push origin vX.Y.Z'로 태그를 푸시하세요."
    }
    Write-Host "감지된 태그: $Tag"
}

if ($Tag -notmatch '^v\d+\.\d+\.\d+') {
    throw "태그 형식이 올바르지 않습니다: $Tag. SemVer 형식 'vX.Y.Z'를 사용하세요."
}

$projectRoot = Split-Path -Parent $PSScriptRoot
$notesSrc = Join-Path $projectRoot "play_store\release_notes\$Tag.txt"
if (-not (Test-Path $notesSrc)) {
    throw "Play Store 릴리즈 노트를 찾을 수 없습니다: $notesSrc. 먼저 play_store/release_notes/$Tag.txt를 작성하세요."
}

if (-not (Test-Path $OutDir)) {
    throw "출력 디렉터리를 찾을 수 없습니다: $OutDir"
}

$tmpDir = New-Item -ItemType Directory -Force -Path (Join-Path $env:TEMP "dockmode-release-$Tag") | Select-Object -ExpandProperty FullName
$aabSrc = Join-Path $tmpDir "dockmode-$Tag-release.aab"

Write-Host "[1/3] GitHub Release에서 AAB 다운로드 중..."
& gh release download $Tag --repo $Repo --pattern "dockmode-$Tag-release.aab" --dir $tmpDir --clobber
if ($LASTEXITCODE -ne 0 -or -not (Test-Path $aabSrc)) {
    throw "AAB 다운로드 실패. 릴리즈에 dockmode-$Tag-release.aab가 첨부되어 있는지 확인하세요."
}

$aabDst = Join-Path $OutDir "dockmode-$Tag.aab"
$notesDst = Join-Path $OutDir "dockmode-$Tag-release-notes.txt"

Write-Host "[2/3] 바탕화면으로 AAB 복사: $aabDst"
Copy-Item -Path $aabSrc -Destination $aabDst -Force

Write-Host "[3/3] 바탕화면으로 Play Store 노트 복사: $notesDst"
Copy-Item -Path $notesSrc -Destination $notesDst -Force

$result = Get-Item $aabDst, $notesDst | Select-Object Name, Length, LastWriteTime
$result | Format-Table -AutoSize

Write-Host ""
Write-Host "완료: dockmode $Tag 산출물이 바탕화면에 준비되었습니다." -ForegroundColor Green
Write-Host "  • AAB:   $aabDst"
Write-Host "  • 노트:  $notesDst"
