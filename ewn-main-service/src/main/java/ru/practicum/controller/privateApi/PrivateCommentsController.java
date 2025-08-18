package ru.practicum.controller.privateApi;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.CommentDto;
import ru.practicum.dto.NewCommentDto;
import ru.practicum.service.CommentService;

@RestController
@RequestMapping("users/{userId}/events/{eventId}/comments")
@RequiredArgsConstructor
@Slf4j
public class PrivateCommentsController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentDto> saveComment(@Valid @RequestBody NewCommentDto newCommentDto,
                                                  @PathVariable Integer userId,
                                                  @PathVariable Integer eventId) {
        CommentDto commentDto = commentService.saveComment(newCommentDto, userId, eventId);
        log.info("Комментарий добавлен");
        return ResponseEntity.status(HttpStatus.CREATED).body(commentDto);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@Valid @RequestBody NewCommentDto newCommentDto,
                                                    @PathVariable Integer commentId) {
        CommentDto commentDto = commentService.updateComment(newCommentDto, commentId);
        log.info("Комментарий обновлен");
        return ResponseEntity.ok(commentDto);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer eventId,
                                              @PathVariable Integer commentId) {
        commentService.deleteComment(eventId, commentId);
        log.info("Комментарий удален");
        return ResponseEntity.noContent().build();
    }
}