$ErrorActionPreference = "Continue"

Clear-Host

Write-Host ""
Write-Host "==========================================" -ForegroundColor Cyan
Write-Host "          SCE WORKSPACE                  " -ForegroundColor Cyan
Write-Host "          Inicio de jornada              " -ForegroundColor Cyan
Write-Host "==========================================" -ForegroundColor Cyan
Write-Host ""

# ==========================================
# CHROME
# ==========================================

Write-Host "[1/3] Chrome" -ForegroundColor Cyan
Write-Host "      WhatsApp Web + ChatGPT" -ForegroundColor DarkGray

try {
    Start-Process "chrome.exe" -ArgumentList @(
        "https://web.whatsapp.com/",
        "https://chatgpt.com/"
    )

    Write-Host "      [OK] Chrome iniciado" -ForegroundColor Green
}
catch {
    Write-Host "      [ERROR] No se pudo abrir Chrome" -ForegroundColor Red
}

Start-Sleep -Seconds 4

# ==========================================
# OUTLOOK
# ==========================================

Write-Host ""
Write-Host "[2/3] Outlook" -ForegroundColor Cyan
Write-Host "      Microsoft Outlook" -ForegroundColor DarkGray

$outlook = Get-Process "OUTLOOK" -ErrorAction SilentlyContinue

if ($null -ne $outlook) {

    Write-Host "      [INFO] Outlook ya estaba abierto" -ForegroundColor Yellow

}
else {

    try {

        Start-Process "outlook.exe"

        Write-Host "      [OK] Outlook iniciado" -ForegroundColor Green

    }
    catch {

        Write-Host "      [ERROR] No se pudo abrir Outlook" -ForegroundColor Red

    }
}

Start-Sleep -Seconds 4

# ==========================================
# TORQUE
# ==========================================

Write-Host ""
Write-Host "[3/3] Excel" -ForegroundColor Cyan
Write-Host "      TORQUE.xlsx" -ForegroundColor DarkGray

$torque = Join-Path $env:USERPROFILE "Desktop\TORQUE.xlsx"

Write-Host "      Buscando:" -ForegroundColor DarkGray
Write-Host "      $torque" -ForegroundColor DarkGray

if (Test-Path $torque) {

    Write-Host "      [OK] Archivo encontrado" -ForegroundColor Green

    try {

        Start-Process $torque

        Start-Sleep -Seconds 4

        Write-Host "      [OK] TORQUE abierto" -ForegroundColor Green

    }
    catch {

        Write-Host "      [ERROR] No se pudo abrir TORQUE" -ForegroundColor Red

    }

}
else {

    Write-Host "      [ERROR] No se encontro TORQUE.xlsx" -ForegroundColor Red

}

# ==========================================
# FINAL
# ==========================================

Write-Host ""
Write-Host "==========================================" -ForegroundColor Cyan
Write-Host "       ENTORNO DE TRABAJO LISTO           " -ForegroundColor Green
Write-Host "==========================================" -ForegroundColor Cyan
Write-Host ""

Write-Host ""
Write-Host "Presiona cualquier tecla para cerrar..." -ForegroundColor DarkGray
Write-Host "Si no haces nada, se cerrara automaticamente en 10 segundos." -ForegroundColor DarkGray
Write-Host ""

$timeout = 10
$endTime = (Get-Date).AddSeconds($timeout)

while ((Get-Date) -lt $endTime) {

    if ([Console]::KeyAvailable) {
        [Console]::ReadKey($true) | Out-Null
        break
    }

    $remaining = [math]::Ceiling(($endTime - (Get-Date)).TotalSeconds)

    Write-Host "`rCerrando en $remaining segundos...   " -NoNewline -ForegroundColor DarkGray

    Start-Sleep -Milliseconds 200
}

Write-Host ""