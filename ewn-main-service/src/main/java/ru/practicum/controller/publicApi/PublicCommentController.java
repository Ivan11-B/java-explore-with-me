package ru.practicum.controller.publicApi;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.CommentDto;
import ru.practicum.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/events/{eventId}/comments")
@RequiredArgsConstructor
@Slf4j
public class PublicCommentController {

    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<List<CommentDto>> getAllComments(@PathVariable Integer eventId,
                                                           @RequestParam(defaultValue = "0") Integer from,
                                                           @RequestParam(defaultValue = "10") Integer size) {
        List<CommentDto> comments = commentService.getAllComments(eventId, from, size);
        log.info("Список коментариев события Id= {}, получен", eventId);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable Integer commentId) {
        CommentDto commentDto = commentService.getCommentDtoById(commentId);
        log.info("Комментарий Id= {}, получен", commentId);
        return ResponseEntity.ok(commentDto);
    }
}