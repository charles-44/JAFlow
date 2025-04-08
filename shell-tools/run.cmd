@echo off
setlocal ENABLEDELAYEDEXPANSION

REM Check if an argument was provided
if "%~1"=="" goto :noarg

REM Check if the JAR file exists
if not exist ".\target\shell-tools-1.0-SNAPSHOT-jar-with-dependencies.jar" (
    echo JAR file not found, building with Maven...
    mvn clean package -DskipTests
)

REM If the argument is "rebuild", force rebuild
if /i "%~1"=="rebuild" (
    echo Rebuilding the project with Maven...
    mvn clean package -DskipTests
    goto :eof
)

REM Normal execution: compute the class name
set "ARG=%~1"
set "FIRST_LETTER=%ARG:~0,1%"
set "REMAINDER=%ARG:~1%"

REM Convert the first letter to uppercase (if possible)
set "FIRST_LETTER_UC=%FIRST_LETTER%"
for %%A in (A B C D E F G H I J K L M N O P Q R S T U V W X Y Z) do (
    if /I "%FIRST_LETTER%"=="%%A" set "FIRST_LETTER_UC=%%A"
)

set "CLASS_NAME=org.scem.command.%FIRST_LETTER_UC%%REMAINDER%Commands"

REM Get all arguments except the first one
set "ARGS="
set i=2
:loop
call set arg=%%~%i%%
if defined arg (
    set "ARGS=!ARGS! !arg!"
    set /a i+=1
    goto :loop
)

echo.
echo Running command with class: %CLASS_NAME%
echo Arguments passed to the class: %ARGS%
echo.

if exist ".\shell-tools-1.0-SNAPSHOT-jar-with-dependencies.jar" (
    rm .\shell-tools-1.0-SNAPSHOT-jar-with-dependencies.jar
)

cp ".\target\shell-tools-1.0-SNAPSHOT-jar-with-dependencies.jar" ".\shell-tools-1.0-SNAPSHOT-jar-with-dependencies.jar"
java -cp ".\shell-tools-1.0-SNAPSHOT-jar-with-dependencies.jar" %CLASS_NAME% %ARGS%
rm .\shell-tools-1.0-SNAPSHOT-jar-with-dependencies.jar
goto :eof

:noarg
echo.
echo [ERROR] Missing argument.
echo Usage: run.cmd [rebuild|<commandName> [args...]]
exit /b 1
