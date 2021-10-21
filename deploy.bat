
@ECHO OFF

REM deploy.bat
REM
REM Copyright (c) 2020-2021 RHEA System S.A.
REM
REM Author: Sam Gerené, Alex Vorobiev, Nathanael Smiechowski 
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

echo ^=================================================================================
echo ^=                   ALMOST-AUTO-DEPLOY for the DEH-MDSYSML Adapter              =
echo ^=                                                                               =
echo ^= \-w ===^> Adds a pause for Eclipse generation of plugin classes                =
echo ^= \-p ===^> Generate DEH-MDSYSML plugin                                          =
echo ^= \-i ===^> Install the generated DEH-MDSYSML plugin                             =
echo ^= \-d ===^> Run Cameo                                                            =
echo ^=================================================================================

if /I "%1" == "-p" goto PackPlugin
if /I "%1" == "-i" goto Install
if /I "%1" == "-d" goto RunCameo

REM 1. Set the class path
REM set CLASSPATH=C:\CODE\DEHP\DEH-MDSYSML\lib\;bin\;%USERPROFILE%\.m2\repository;C:\CODE\DEHP\DEH-CommonJ;
REM setx -m CLASSPATH "\lib\;C:\Users\nsmiechowski\.m2\repository;C:\CODE\DEHP\DEH-CommonJ"

REM 2. Pack the Common library if any changes done to it
echo.
echo ==================================^> call mvn package DEH-CommonJ
echo ===============================================================^>
echo.

call mvn package -Dmaven.test.skip=true
echo Exit Code = %ERRORLEVEL%
if not "%ERRORLEVEL%" == "0" exit /b

REM 3. Install the generated Jar into the local maven cache
echo.
echo ==================================^> call mvn install
echo ===============================================================^>
echo.
call mvn install:install-file -Dfile=target\DEHCommonJ.jar -DgroupId=com.rheagroup -DartifactId=DEHCommonJ -Dversion=1.0.0  -Dpackaging=jar -DgeneratePom=true

REM 4. Install required libraries
REM    - Copy then past all the Jar file located in ```{your Cameo/MagicDraw installation path}\lib\``` to ```{The location of the DEH-MDSYSML project repository}\lib\```.

REM 5. Make sure the DEH-MDSYSML compiles in eclipse
echo.
echo ==================================^> Compile DEH-MDSYSML plugin in Eclipse
echo ===============================================================^>
echo.

if /I "%1" == "-w" (
    set /p="IMPORTANT Please recompile the DEH-MDSYSML plugin in Eclipse. -- Press ENTER when done --"
) else (
    echo Skipped
)

:PackPlugin
REM 6. Pack the plugin
cd ..\DEH-MDSYSML\

echo.
echo ==================================^> call mvn package DEH-MDSYSML
echo ===============================================================^>
echo.

call mvn package -Dmaven.test.skip=true
echo Exit Code = %ERRORLEVEL%
if not "%ERRORLEVEL%" == "0" GOTO ExitStatement

:Install
REM 7. Install the plugin
echo.
echo ==================================^> Install the plugin
echo ===============================================================^>
echo.

SET COPYCMD=/Y && move /Y "target\DEHMDSYSMLPlugin.jar" "C:\Program Files\Cameo Systems Modeler Demo\plugins\com.rheagroup.dehmdsysml"
echo Exit Code = %ERRORLEVEL%
if not "%ERRORLEVEL%" == "0" GOTO ExitStatement

:RunCameo
REM 8. Run Cameo
echo.
echo ==================================^> Run Cameo
echo ===============================================================^>
echo.
call "C:\Program Files\Cameo Systems Modeler Demo\bin\csm.exe"

GOTO ExitStatement

:ExitStatement
cd ..\DEH-CommonJ\
echo deploy.bat is done