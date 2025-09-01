# Notebook API 文档

## 概述
这是一个具有用户登录和笔记管理功能的Notebook后端API。

## 基础信息
- 基础URL: `http://localhost:8080`
- 数据格式: JSON
- 认证方式: 通过请求头 `X-User-Id` 传递用户ID

## 用户管理

### 1. 用户注册
- **URL**: `POST /api/users/register`
- **请求体**:
```json
{
    "username": "用户名",
    "password": "密码"
}
```
- **响应**:
```json
{
    "code": 200,
    "message": "success",
    "data": {
        "id": 1,
        "username": "用户名"
    }
}
```

### 2. 用户登录
- **URL**: `POST /api/users/login`
- **请求体**:
```json
{
    "username": "用户名",
    "password": "密码"
}
```
- **响应**:
```json
{
    "code": 200,
    "message": "success",
    "data": {
        "userId": 1,
        "username": "用户名"
    }
}
```

## 笔记管理

**注意**: 所有笔记相关的API都需要在请求头中包含 `X-User-Id` 来标识当前用户。

### 1. 创建笔记
- **URL**: `POST /api/notes`
- **请求头**: `X-User-Id: {用户ID}`
- **请求体**:
```json
{
    "title": "笔记标题",
    "content": "笔记内容"
}
```
- **响应**:
```json
{
    "code": 200,
    "message": "success",
    "data": {
        "id": 1,
        "title": "笔记标题",
        "content": "笔记内容",
        "createTime": "2024-01-01T10:00:00",
        "updateTime": "2024-01-01T10:00:00"
    }
}
```

### 2. 更新笔记
- **URL**: `PUT /api/notes`
- **请求头**: `X-User-Id: {用户ID}`
- **请求体**:
```json
{
    "id": 1,
    "title": "更新后的标题",
    "content": "更新后的内容"
}
```

### 3. 删除笔记
- **URL**: `DELETE /api/notes/{笔记ID}`
- **请求头**: `X-User-Id: {用户ID}`

### 4. 获取用户所有笔记
- **URL**: `GET /api/notes`
- **请求头**: `X-User-Id: {用户ID}`
- **响应**: 返回该用户的所有笔记列表

### 5. 获取单个笔记
- **URL**: `GET /api/notes/{笔记ID}`
- **请求头**: `X-User-Id: {用户ID}`

## 错误响应格式
```json
{
    "code": 400,
    "message": "错误信息",
    "data": null
}
```

## 常见错误码
- `200`: 成功
- `400`: 请求参数错误
- `401`: 未授权（缺少用户认证信息）
- `500`: 服务器内部错误

## 使用流程
1. 调用注册接口创建用户账号
2. 调用登录接口获取用户ID
3. 在后续的笔记操作中，将用户ID放在 `X-User-Id` 请求头中
4. 使用笔记相关的API进行增删改查操作

## 测试数据
系统预置了两个测试用户：
- 用户名: `admin`, 密码: `123456`
- 用户名: `test`, 密码: `123456`
