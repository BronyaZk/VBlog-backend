package com.example.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.project.common.ex.BusinessException;
import com.example.project.dto.form.UserCreateForm;
import com.example.project.dto.vo.UserLoginVO;
import com.example.project.dto.vo.UserVO;
import com.example.project.entity.User;
import com.example.project.mapper.UserMapper;
import com.example.project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.example.project.dto.form.UserLoginForm;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    @Override
    public UserLoginVO login(UserLoginForm form) {
        // 1. 根据用户名查询用户
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, form.getUsername()));

        // 2. 用户不存在或密码错误
        if (user == null || !form.getPassword().equals(user.getPassword())) {
            throw new BusinessException("用户名或密码错误");
        }

        // 3. 返回登录结果
        UserLoginVO vo = new UserLoginVO();
        vo.setUserId(user.getId());
        vo.setUsername(user.getUsername());
        return vo;
    }
    
    @Override
    public UserVO register(UserCreateForm form) {
        // 1. 检查用户名是否已存在
        User existingUser = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, form.getUsername()));
        
        if (existingUser != null) {
            throw new BusinessException("用户名已存在");
        }
        
        // 2. 创建新用户
        User user = new User();
        user.setUsername(form.getUsername());
        user.setPassword(form.getPassword());
        
        userMapper.insert(user);
        
        // 3. 返回用户信息
        UserVO vo = new UserVO();
        vo.setId(user.getId());
        vo.setUsername(user.getUsername());
        return vo;
    }
}
