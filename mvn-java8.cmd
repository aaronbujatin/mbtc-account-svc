@echo off
rem Runs the Maven wrapper with JDK 8 for this project only.
rem Global JAVA_HOME is left untouched.
setlocal

for /d %%D in ("%LOCALAPPDATA%\Programs\Eclipse Adoptium\jdk8u*") do set "JAVA_HOME=%%~fD"

if not defined JAVA_HOME (
    echo JDK 8 not found under "%LOCALAPPDATA%\Programs\Eclipse Adoptium".
    exit /b 1
)

set "PATH=%JAVA_HOME%\bin;%PATH%"
call "%~dp0mvnw.cmd" %*
