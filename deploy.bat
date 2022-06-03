
@ECHO OFF

REM deploy.bat
REM
REM Copyright (c) 2020-2021 RHEA System S.A.
REM
REM Author: Sam Gerene, Alex Vorobiev, Nathanael Smiechowski 
REM
REM This file is part of DEH-CommonJ
REM
REM The DEH-CommonJ is free software; you can redistribute it and/or
REM modify it under the terms of the GNU Lesser General Public
REM License as published by the Free Software Foundation; either
REM version 3 of the License, or (at your option) any later version.
REM
REM The DEH-CommonJ is distributed in the hope that it will be useful,
REM but WITHOUT ANY WARRANTY; without even the implied warranty of
REM MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
REM Lesser General Public License for more details.
REM
REM You should have received a copy of the GNU Lesser General Public License
REM along with this program; if not, write to the Free Software Foundation,
REM Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.

echo ^==========================================================================================================
echo ^=                   DEHCommonJ builder                                                                   =            
echo ^=  example of use:                                                                                       =
echo ^= deploy.bat                // Uses the pom version number                                               =
echo ^= deploy.bat 1.7.0          // Updates the pom version number if different                               =
echo ^= deploy.bat ../lib/ 1.7.0  // Same as above and copy the generated Jar to specified the directory       =
echo ^==========================================================================================================

set shouldCopy=false

for /f "tokens=2 delims=<version>" %%a in ('type pom.xml^|find "<version>"') do (
  set pomVersion=%%a & goto :continue
)
:continue
set pomVersion=%pomVersion: =%

call :SetVersion %1
call :SetVersion %2

if exist %1 (set shouldCopy=true)
if "%version%" == "" set version=%pomVersion%

if %pomVersion% NEQ %version% powershell -Command " [regex]$pattern='(\<version\>)(%pomVersion%)(\<\/version\>)'; $pattern.replace((gc pom.xml),'<version>%version%$3',1) | set-content pom.xml"

:BUILD
echo.
echo ==================================^> Rebuilding the DEH-CommonJ library VERSION %version%
echo ===============================================================^>
echo.

call mvn package -Dmaven.test.skip=true
echo Exit Code = %ERRORLEVEL%
if not "%ERRORLEVEL%" == "0" goto QUIT

echo.
echo ==================================^> Installing the DEH-CommonJ library in local Maven repository
echo ===============================================================^>
echo.

call mvn install:install-file -Dfile=target\DEHCommonJ.jar -DgroupId=com.rheagroup -DartifactId=DEHCommonJ -Dversion=%version%  -Dpackaging=jar -DgeneratePom=true
echo Exit Code = %ERRORLEVEL%
if not "%ERRORLEVEL%" == "0" goto QUIT
echo DEHCommonJ library has been built

if shouldCopy == false goto QUIT

:copy
echo.
echo ==================================^> Copying files to the specified directory
echo ===============================================================^>
echo.
set "basePath=%HOMEPATH%\.m2\repository\com\rheagroup\DEHCommonJ\%version%\"
set "pathToNewBuild=%basePath%DEHCommonJ-%version%.jar"
set "pathToSources=target\DEHCommonJ-%version%-sources.jar"

call xcopy /y %pathToSources% %1
call xcopy /y %pathToNewBuild% %1
echo Exit Code = %ERRORLEVEL%
if not "%ERRORLEVEL%" == "0" goto QUIT

:QUIT
set version=
set pomVersion=
set shouldCopy=
goto :eof

:SetVersion
echo %~1%|findstr /i /r [A-z] >nul 2>&1
if errorlevel 1 if not errorlevel 2 set version=%~1%