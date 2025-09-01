package com.example.project.dto.vo;

import lombok.Data;
import java.time.LocalDateTime;

/** 用户返回对象 */
@Data
public class UserVO {
    private Long id;
    private String username;
    private String password;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}