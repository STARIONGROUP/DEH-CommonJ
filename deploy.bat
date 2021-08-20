
@ECHO OFF

echo ^=================================================================================
echo ^=                   ALMOST-AUTO-DEPLOY for the DEH-MDSYSML Adapter              =
echo ^=                                                                               =
echo ^= \-p ===> Generate DEH-MDSYSML plugin                                          =
echo ^= \-i ===> Install the generated DEH-MDSYSML plugin                             =
echo ^=================================================================================

if /I "%1" == "-p" goto PackPlugin
if /I "%1" == "-i" goto Install

REM 1. Set the class path
REM set CLASSPATH=C:\CODE\DEHP\DEH-MDSYSML\lib\;bin\;%USERPROFILE%\.m2\repository;C:\CODE\DEHP\DEH-CommonJ;
REM setx -m CLASSPATH "\lib\;C:\Users\nsmiechowski\.m2\repository;C:\CODE\DEHP\DEH-CommonJ"

REM 2. Pack the Common library if any changes done to it
echo.
echo ==================================^> call mvn package DEH-CommonJ
echo ===============================================================^>
echo.

call mvn package
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
echo ==================================^> Compiler DEH-MDSYSML plugin in Eclipse
echo ===============================================================^>
echo.

set /p="IMPORTANT Please recompile the DEH-MDSYSML plugin in Eclipse. -- Press ENTER when done --"

:PackPlugin
REM 6. Pack the plugin
cd ..\DEH-MDSYSML\

echo.
echo ==================================^> call mvn package DEH-MDSYSML
echo ===============================================================^>
echo.

call mvn package
echo Exit Code = %ERRORLEVEL%
if not "%ERRORLEVEL%" == "0" GOTO ExitStatement

:Install
REM 7. Install the plugin
echo.
echo ==================================^> Install the plugin
echo ===============================================================^>
echo.

SET COPYCMD=/Y && move /Y "target\DEHMDSYSMLPlugin.jar" "C:\Program Files\Cameo Systems Modeler Demo\plugins\com.rheagroup.dehmdsysml"
GOTO ExitStatement

:ExitStatement
cd ..\DEH-CommonJ\
echo deploy.bat is done