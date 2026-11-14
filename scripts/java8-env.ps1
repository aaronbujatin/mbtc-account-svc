# Sets JAVA_HOME to the local JDK 8 install for the current PowerShell session.
# Usage:  . .\scripts\java8-env.ps1

$root = Join-Path $env:LOCALAPPDATA 'Programs\Eclipse Adoptium'
$jdk8 = Get-ChildItem $root -Directory -Filter 'jdk8u*' -ErrorAction SilentlyContinue |
    Sort-Object Name -Descending |
    Select-Object -First 1

if (-not $jdk8) {
    throw "JDK 8 not found under $root. Install Temurin 8 first."
}

$env:JAVA_HOME = $jdk8.FullName
$env:PATH = "$($jdk8.FullName)\bin;$env:PATH"

Write-Host "JAVA_HOME=$env:JAVA_HOME"
& "$env:JAVA_HOME\bin\java.exe" -version
