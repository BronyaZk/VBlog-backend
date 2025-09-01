package com.example.project.controller;

import com.example.project.common.Result;
import com.example.project.dto.form.NoteCreateForm;
import com.example.project.dto.form.NoteUpdateForm;
import com.example.project.dto.vo.NoteVO;
import com.example.project.service.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;

    /** 创建笔记 */
    @PostMapping
    public Result<NoteVO> createNote(@RequestHeader("X-User-Id") Long userId,
                                    @Valid @RequestBody NoteCreateForm form) {
        return Result.ok(noteService.createNote(userId, form));
    }

    /** 更新笔记 */
    @PutMapping
    public Result<NoteVO> updateNote(@RequestHeader("X-User-Id") Long userId,
                                    @Valid @RequestBody NoteUpdateForm form) {
        return Result.ok(noteService.updateNote(userId, form));
    }

    /** 删除笔记 */
    @DeleteMapping("/{noteId}")
    public Result<Void> deleteNote(@RequestHeader("X-User-Id") Long userId,
                                  @PathVariable Long noteId) {
        noteService.deleteNote(userId, noteId);
        return Result.ok();
    }

    /** 获取用户的所有笔记 */
    @GetMapping
    public Result<List<NoteVO>> getUserNotes(@RequestHeader("X-User-Id") Long userId) {
        return Result.ok(noteService.getUserNotes(userId));
    }

    /** 根据ID获取笔记 */
    @GetMapping("/{noteId}")
    public Result<NoteVO> getNoteById(@RequestHeader("X-User-Id") Long userId,
                                     @PathVariable Long noteId) {
        return Result.ok(noteService.getNoteById(userId, noteId));
    }

    /** 提炼/总结笔记（DeepSeek） */
    @PostMapping("/{noteId}/summarize")
    public Result<String> summarize(@RequestHeader("X-User-Id") Long userId,
                                    @PathVariable Long noteId) {
        return Result.ok(noteService.summarizeNote(userId, noteId));
    }
}
