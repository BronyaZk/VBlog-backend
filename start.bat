@echo off
echo 启动Notebook后端服务...
echo.

REM 检查Java是否安装
java -version >nul 2>&1
if errorlevel 1 (
    echo 错误: 未找到Java，请先安装Java 17或更高版本
    pause
    exit /b 1
)

REM 检查Maven是否安装
mvn -version >nul 2>&1
if errorlevel 1 (
    echo 错误: 未找到Maven，请先安装Maven
    pause
    exit /b 1
)

echo 正在编译和启动项目...
echo.

REM 清理并编译项目
mvn clean compile

REM 启动Spring Boot应用
mvn spring-boot:run

pause
