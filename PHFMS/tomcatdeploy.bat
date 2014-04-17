@echo off
SET TOMCAT_HOME=D:\apache-tomcat-7.0.41
rm -r %TOMCAT_HOME%\webapps\ArhaticYoga\META-INF
rm -r %TOMCAT_HOME%\webapps\ArhaticYoga\resources
rm -r %TOMCAT_HOME%\webapps\ArhaticYoga\WEB-INF

xcopy .\target\ArhaticYoga %TOMCAT_HOME%\webapps\ArhaticYoga /s /e /q