package com.example.project.service;

import com.example.project.dto.form.UserCreateForm;
import com.example.project.dto.form.UserLoginForm;
import com.example.project.dto.vo.UserLoginVO;
import com.example.project.dto.vo.UserVO;

public interface UserService {
    /**
     * 用户登录
     */
    UserLoginVO login(UserLoginForm form);
    
    /**
     * 用户注册
     */
    UserVO register(UserCreateForm form);
}
