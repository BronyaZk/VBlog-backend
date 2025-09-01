#!/bin/bash

echo "启动Notebook后端服务..."
echo

# 检查Java是否安装
if ! command -v java &> /dev/null; then
    echo "错误: 未找到Java，请先安装Java 17或更高版本"
    exit 1
fi

# 检查Maven是否安装
if ! command -v mvn &> /dev/null; then
    echo "错误: 未找到Maven，请先安装Maven"
    exit 1
fi

echo "正在编译和启动项目..."
echo

# 清理并编译项目
mvn clean compile

# 启动Spring Boot应用
mvn spring-boot:run
