@echo off
REM PIV Test Data Software - Windows Launcher

setlocal EnableDelayedExpansion

set SCRIPT_DIR=%~dp0
set BINARIES_DIR=%SCRIPT_DIR%Binaries

echo PIV Test Data Software Launcher
echo ================================

:menu
echo.
echo Select an option:
echo 1. Run JPIV Test Data Generator
echo 2. Run PIV Data Loader
echo 3. Setup Dependencies
echo 4. Exit
echo.
set /p choice="Enter your choice (1-4): "

if "%choice%"=="1" goto run_jpiv
if "%choice%"=="2" goto run_loader
if "%choice%"=="3" goto setup_deps
if "%choice%"=="4" goto end
echo Invalid choice. Please try again.
goto menu

:run_jpiv
echo Launching JPIV Test Data Generator...
cd /d "%SCRIPT_DIR%"
call run-jpiv.sh
goto menu

:run_loader
echo Launching PIV Data Loader...
cd /d "%BINARIES_DIR%"
start "" "PIV Data Loader.exe"
goto menu

:setup_deps
echo Running dependency setup...
cd /d "%SCRIPT_DIR%"
call setup-dependencies.sh
echo.
echo Dependencies setup complete!
pause
goto menu

:end
echo Goodbye!
exit /b 0