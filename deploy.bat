@echo off
set APP_NAME=ETU003366
set SRC_DIR=src\main\java
set WEB_DIR=src\main\webapp
set BUILD_DIR=build
set LIB_DIR=lib
REM set TOMCAT_WEBAPPS=C:\apache-tomcat-9.0.89\webapps
set TOMCAT_WEBAPPS=C:\apache-tomcat-10.1.28\webapps
REM set TOMCAT_WEBAPPS=C:\apache-tomcat-9.0.82\webapps
set SERVLET_API_JAR=%LIB_DIR%\servlet-api.jar
set MYSQL_CONNECTOR_JAR=%LIB_DIR%\mysql-connector-j-9.1.0.jar

REM Suppression et recréation du dossier temporaire
if exist %BUILD_DIR% rmdir /s /q %BUILD_DIR%
mkdir %BUILD_DIR%\WEB-INF\classes

REM Compilation des fichiers Java avec le JAR des Servlets
dir /s /b %SRC_DIR%\*.java > sources.txt
REM javac -cp %SERVLET_API_JAR% -d %BUILD_DIR%\WEB-INF\classes @sources.txt
javac -cp "%SERVLET_API_JAR%;%MYSQL_CONNECTOR_JAR%" -d "%BUILD_DIR%\WEB-INF\classes" @sources.txt
del sources.txt

REM Copier les fichiers web
xcopy %WEB_DIR% %BUILD_DIR% /E /I /Y

REM Création du fichier .war dans le dossier build
cd %BUILD_DIR%
jar -cvf %APP_NAME%.war *
cd ..

REM Déploiement vers Tomcat
copy %BUILD_DIR%\%APP_NAME%.war %TOMCAT_WEBAPPS%

echo Déploiement terminé. Redémarrez Tomcat si nécessaire.