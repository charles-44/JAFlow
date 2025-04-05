@echo off
setlocal ENABLEDELAYEDEXPANSION

REM Vérifie si un argument a été fourni
if "%~1"=="" goto :noarg

REM Vérifie l’existence du fichier JAR
if not exist ".\target\shell-tools-1.0-SNAPSHOT-jar-with-dependencies.jar" (
    echo Le fichier JAR n'existe pas, construction avec Maven...
    mvn clean package
)

REM Si l’argument est rebuild, on force le clean/package
if /i "%~1"=="rebuild" (
    echo Reconstruction du projet avec Maven...
    mvn clean package
    goto :eof
)

REM Traitement normal : construire le nom de la classe
set "ARG=%~1"
set "FIRST_LETTER=%ARG:~0,1%"
set "REMAINDER=%ARG:~1%"

REM Convertir la première lettre en majuscule (si possible)
set "FIRST_LETTER_UC=%FIRST_LETTER%"
for %%A in (A B C D E F G H I J K L M N O P Q R S T U V W X Y Z) do (
    if /I "%FIRST_LETTER%"=="%%A" set "FIRST_LETTER_UC=%%A"
)

set "CLASS_NAME=org.scem.command.%FIRST_LETTER_UC%%REMAINDER%Commands"

REM Récupère tous les paramètres sauf le premier
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
echo Lancement de la commande avec la classe : %CLASS_NAME%
echo Paramètres passés à la classe : %ARGS%
echo.

java -cp ".\target\shell-tools-1.0-SNAPSHOT-jar-with-dependencies.jar" %CLASS_NAME% %ARGS%
goto :eof

:noarg
echo.
echo [ERREUR] Argument manquant.
echo Usage: run.cmd [rebuild|<commandName> [args...]]
exit /b 1
