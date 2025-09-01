package com.example.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.project.common.ex.BusinessException;
import com.example.project.dto.form.NoteCreateForm;
import com.example.project.dto.form.NoteUpdateForm;
import com.example.project.dto.vo.NoteVO;
import com.example.project.entity.Note;
import com.example.project.mapper.NoteMapper;
import com.example.project.service.NoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoteServiceImpl implements NoteService {
    private final NoteMapper noteMapper;
    private final com.example.project.service.llm.DeepSeekClient deepSeekClient;

    @Override
    public NoteVO createNote(Long userId, NoteCreateForm form) {
        Note note = new Note();
        note.setUserId(userId);
        note.setTitle(form.getTitle());
        note.setContent(form.getContent());
        note.setCreateTime(LocalDateTime.now());
        note.setUpdateTime(LocalDateTime.now());
        
        noteMapper.insert(note);
        
        return convertToVO(note);
    }

    @Override
    public NoteVO updateNote(Long userId, NoteUpdateForm form) {
        // 检查笔记是否存在且属于当前用户
        Note note = noteMapper.selectOne(new LambdaQueryWrapper<Note>()
                .eq(Note::getId, form.getId())
                .eq(Note::getUserId, userId));
        
        if (note == null) {
            throw new BusinessException("笔记不存在或无权限修改");
        }
        
        note.setTitle(form.getTitle());
        note.setContent(form.getContent());
        note.setUpdateTime(LocalDateTime.now());
        
        noteMapper.updateById(note);
        
        return convertToVO(note);
    }

    @Override
    public void deleteNote(Long userId, Long noteId) {
        // 检查笔记是否存在且属于当前用户
        Note note = noteMapper.selectOne(new LambdaQueryWrapper<Note>()
                .eq(Note::getId, noteId)
                .eq(Note::getUserId, userId));
        
        if (note == null) {
            throw new BusinessException("笔记不存在或无权限删除");
        }
        
        noteMapper.deleteById(noteId);
    }

    @Override
    public List<NoteVO> getUserNotes(Long userId) {
        List<Note> notes = noteMapper.selectList(new LambdaQueryWrapper<Note>()
                .eq(Note::getUserId, userId)
                .orderByDesc(Note::getUpdateTime));
        
        return notes.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    public NoteVO getNoteById(Long userId, Long noteId) {
        Note note = noteMapper.selectOne(new LambdaQueryWrapper<Note>()
                .eq(Note::getId, noteId)
                .eq(Note::getUserId, userId));
        
        if (note == null) {
            throw new BusinessException("笔记不存在或无权限查看");
        }
        
        return convertToVO(note);
    }

    @Override
    public String summarizeNote(Long userId, Long noteId) {
        Note note = noteMapper.selectOne(new LambdaQueryWrapper<Note>()
                .eq(Note::getId, noteId)
                .eq(Note::getUserId, userId));
        if (note == null) {
            throw new BusinessException("笔记不存在或无权限查看");
        }
        String prompt = "请用中文精炼总结以下笔记的要点，50-100字：\n" + note.getContent();
        try {
            return deepSeekClient.summarize(prompt);
        } catch (Exception e) {
            log.error("DeepSeek 提炼失败", e);
            throw new BusinessException("大模型提炼失败，请稍后重试");
        }
    }
    
    private NoteVO convertToVO(Note note) {
        NoteVO vo = new NoteVO();
        BeanUtils.copyProperties(note, vo);
        return vo;
    }
}
