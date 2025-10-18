@echo off
REM Batch script to run the Java Swing Calculator
REM Author: Calculator Project
REM Description: Simple batch script for running the calculator application

setlocal enabledelayedexpansion

echo.
echo ╔══════════════════════════════════════════════════════════════╗
echo ║                Java Swing Calculator Runner                 ║
echo ║                                                              ║
echo ║  A comprehensive calculator with simple, scientific, and    ║
echo ║  graphing capabilities built with Java Swing                ║
echo ╚══════════════════════════════════════════════════════════════╝
echo.

REM Check if Maven is available
mvn -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Maven not found. Please install Maven and add it to your PATH.
    pause
    exit /b 1
)

REM Check if Java is available
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Java not found. Please install Java 17 or higher and add it to your PATH.
    pause
    exit /b 1
)

echo ✅ Prerequisites check passed!
echo.

REM Check if project is built
if not exist "target\classes" (
    echo 🔨 Building project first...
    mvn clean compile
    if %errorlevel% neq 0 (
        echo ❌ Build failed!
        pause
        exit /b 1
    )
    echo ✅ Build completed!
    echo.
)

echo 🚀 Starting Java Swing Calculator...
echo The calculator window should open shortly...
echo Press Ctrl+C to stop the application
echo.

REM Run the application
mvn exec:java

if %errorlevel% neq 0 (
    echo ❌ Failed to start calculator!
    pause
    exit /b 1
)

echo.
echo 🏁 Calculator application closed.
pause
