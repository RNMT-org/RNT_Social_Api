@echo off
REM RNT Code Generator - Cross-Platform Maven-Based Tool

echo ================================================================
echo      RNT Social API - Code Generator (Maven-Based)
echo ================================================================
echo.

REM Check if Maven is installed
call mvn --version >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Maven is not installed!
    echo Please install Maven from: https://maven.apache.org/download.cgi
    pause
    exit /b 1
)

for /f "tokens=*" %%i in ('mvn --version ^| findstr /C:"Apache Maven"') do set MAVEN_VERSION=%%i
echo [OK] %MAVEN_VERSION%
echo.

REM Check if XML folder exists
if not exist "src\main\resources\generate-xmls" (
    echo ERROR: XML folder not found!
    echo Expected location: src\main\resources\generate-xmls\
    pause
    exit /b 1
)

REM Count XML files
set XML_COUNT=0
for %%f in (src\main\resources\generate-xmls\*.xml) do set /a XML_COUNT+=1

if %XML_COUNT%==0 (
    echo ERROR: No XML files found in generate-xmls folder!
    echo Please add entity XML files to: src\main\resources\generate-xmls\
    pause
    exit /b 1
)

echo [OK] Found %XML_COUNT% XML entity file(s)
echo.

REM List XML files
echo XML Files to process:
for %%f in (src\main\resources\generate-xmls\*.xml) do echo    - %%~nxf
echo.

REM Ask for confirmation
echo WARNING: This will generate code for all entities
echo          Existing files may be overwritten!
echo.
set /p confirm="Do you want to continue? (Y/N): "
if /i not "%confirm%"=="Y" (
    echo Cancelled by user
    pause
    exit /b 0
)

echo.
echo Starting code generation using Maven...
echo ================================================================
echo.

REM Run Maven with generate profile
call mvn clean compile -Pgenerate -DxmlFolderPath=generate-xmls

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ================================================================
    echo                    SUCCESS!
    echo ================================================================
    echo.
    echo Generated files location:
    echo   src\main\java\ir\rayanovinmt\rnt_social_api\{entityName}\
    echo.
    echo Next steps:
    echo   1. Review the generated code in your IDE
    echo   2. Run: mvn spring-boot:run
    echo   3. Test endpoints at: http://localhost:8080/swagger-ui.html
    echo.
    echo Tip: Use 'git diff' to see what changed
    echo.
) else (
    echo.
    echo ================================================================
    echo                    FAILED!
    echo ================================================================
    echo.
    echo Check the error messages above for details.
    echo.
    echo Common issues:
    echo   - Invalid XML syntax
    echo   - Missing required fields
    echo   - Duplicate entity names
    echo   - Invalid field types
    echo.
)

pause
