@echo off
SET TOMCAT_HOME=C:\apache-tomcat-7.0.54

rd /s /q %TOMCAT_HOME%\webapps\ArhaticYoga\META-INF
rd /s /q %TOMCAT_HOME%\webapps\ArhaticYoga\resources
rd /s /q %TOMCAT_HOME%\webapps\ArhaticYoga\WEB-INF

xcopy .\target\ArhaticYoga %TOMCAT_HOME%\webapps\ArhaticYoga /s /e /q