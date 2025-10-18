# Simple PowerShell script to run the Java Swing Calculator
param([string]$Action = "run")

Write-Host "CalculatoR Runner" -ForegroundColor Green
Write-Host "============================" -ForegroundColor Green

# Check prerequisites
try {
    mvn -version | Out-Null
    Write-Host "Maven: OK" -ForegroundColor Green
} catch {
    Write-Host "Maven: NOT FOUND" -ForegroundColor Red
    exit 1
}

try {
    java -version | Out-Null
    Write-Host "Java: OK" -ForegroundColor Green
} catch {
    Write-Host "Java: NOT FOUND" -ForegroundColor Red
    exit 1
}

Write-Host ""

if ($Action -eq "run") {
    Write-Host "Starting Calculator..." -ForegroundColor Cyan
    if (-not (Test-Path "target/classes")) {
        Write-Host "Building project..." -ForegroundColor Yellow
        mvn clean compile
    }
    mvn exec:java
}
elseif ($Action -eq "test") {
    Write-Host "Running Tests..." -ForegroundColor Cyan
    mvn test
}
elseif ($Action -eq "build") {
    Write-Host "Building Project..." -ForegroundColor Cyan
    mvn clean compile
}
elseif ($Action -eq "clean") {
    Write-Host "Cleaning Project..." -ForegroundColor Cyan
    mvn clean
}
elseif ($Action -eq "package") {
    Write-Host "Packaging Project..." -ForegroundColor Cyan
    mvn clean package
    if ($LASTEXITCODE -eq 0) {
        Write-Host "JAR created: target/calculator-app-1.0.0.jar" -ForegroundColor Green
    }
}
else {
    Write-Host "Usage: .\run-calculator.ps1 [run|test|build|clean|package]" -ForegroundColor Yellow
    Write-Host "Default: run" -ForegroundColor White
}

Write-Host "Done!" -ForegroundColor Green