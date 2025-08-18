package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.CommentDto;
import ru.practicum.dto.NewCommentDto;
import ru.practicum.exception.CommentException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.CommentMapper;
import ru.practicum.model.Comment;
import ru.practicum.model.Event;
import ru.practicum.model.User;
import ru.practicum.repository.CommentRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final EventService eventService;
    private final CommentMapper commentMapper;

    @Override
    @Transactional
    public CommentDto saveComment(NewCommentDto newCommentDto, Integer userId, Integer eventId) {
        User author = userService.getUserById(userId);
        Event event = eventService.getEventById(eventId);
        if (commentRepository.findCommentByAuthorIdAndEvent(userId, eventId) != null) {
            throw new CommentException("Комментарий уже добавлен");
        }
        Comment comment = commentMapper.toEntity(newCommentDto);
        comment.setAuthor(author);
        comment.setCreated(LocalDateTime.now());
        comment.setEvent(eventId);
        Comment savedComment = commentRepository.save(comment);
        event.setComments(event.getComments() + 1);
        return commentMapper.toDto(savedComment);
    }

    @Override
    public List<CommentDto> getAllComments(Integer eventId, Integer from, Integer size) {
        return commentMapper.toDto(commentRepository.findAllByEvent(eventId, from, size));
    }

    @Override
    @Transactional
    public CommentDto updateComment(NewCommentDto newCommentDto, Integer commentId) {
        Comment comment = getCommentById(commentId);
        comment.setText(newCommentDto.getText());
        Comment updatedComment = commentRepository.save(comment);
        return commentMapper.toDto(updatedComment);
    }

    @Override
    @Transactional
    public void deleteComment(Integer eventId, Integer commentId) {
        Event event = eventService.getEventById(eventId);
        getCommentById(commentId);
        commentRepository.deleteById(commentId);
        event.setComments(event.getComments() - 1);
    }

    @Override
    public Comment getCommentById(Integer commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Комментарий с id=" + commentId + " не найден"));
    }

    @Override
    public CommentDto getCommentDtoById(Integer commentId) {
        return commentMapper.toDto(getCommentById(commentId));
    }
}
