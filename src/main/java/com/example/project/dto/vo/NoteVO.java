package com.example.project.dto.vo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class NoteVO {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
