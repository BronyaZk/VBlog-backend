package com.example.project.dto.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NoteUpdateForm {
    @NotNull(message = "笔记ID不能为空")
    private Long id;
    
    @NotBlank(message = "标题不能为空")
    @Size(max = 100, message = "标题长度不能超过100个字符")
    private String title;
    
    @NotBlank(message = "内容不能为空")
    @Size(max = 5000, message = "内容长度不能超过5000个字符")
    private String content;
}
