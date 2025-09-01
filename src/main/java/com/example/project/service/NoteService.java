package com.example.project.service;

import com.example.project.dto.form.NoteCreateForm;
import com.example.project.dto.form.NoteUpdateForm;
import com.example.project.dto.vo.NoteVO;
import java.util.List;

public interface NoteService {
    /**
     * 创建笔记
     */
    NoteVO createNote(Long userId, NoteCreateForm form);
    
    /**
     * 更新笔记
     */
    NoteVO updateNote(Long userId, NoteUpdateForm form);
    
    /**
     * 删除笔记
     */
    void deleteNote(Long userId, Long noteId);
    
    /**
     * 获取用户的所有笔记
     */
    List<NoteVO> getUserNotes(Long userId);
    
    /**
     * 根据ID获取笔记
     */
    NoteVO getNoteById(Long userId, Long noteId);

    /**
     * 使用 DeepSeek 对笔记内容进行提炼
     */
    String summarizeNote(Long userId, Long noteId);
}
