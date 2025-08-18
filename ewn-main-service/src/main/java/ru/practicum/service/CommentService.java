package ru.practicum.service;

import ru.practicum.dto.CommentDto;
import ru.practicum.dto.NewCommentDto;
import ru.practicum.model.Comment;

import java.util.List;

public interface CommentService {

    CommentDto saveComment(NewCommentDto newCommentDto, Integer userId, Integer eventId);

    List<CommentDto> getAllComments(Integer eventId, Integer from, Integer size);

    CommentDto updateComment(NewCommentDto newCommentDto, Integer commentId);

    void deleteComment(Integer eventId, Integer commentId);

    CommentDto getCommentDtoById(Integer commentId);

    Comment getCommentById(Integer commentId);
}
