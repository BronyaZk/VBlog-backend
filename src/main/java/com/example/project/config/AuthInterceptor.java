package com.example.project.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 对于笔记相关的API，检查是否有用户ID头
        String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/api/notes")) {
            String userId = request.getHeader("X-User-Id");
            if (userId == null || userId.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"code\":401,\"message\":\"缺少用户认证信息\"}");
                return false;
            }
        }
        return true;
    }
}
