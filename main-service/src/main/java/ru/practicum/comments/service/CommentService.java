package ru.practicum.comments.service;

import ru.practicum.comments.dto.CommentNewDto;
import ru.practicum.comments.model.Comment;

import java.util.List;

public interface CommentService {

    Comment create(Long userId, Long eventId, Comment comment);

    Comment update(Long userId, Long commentId, CommentNewDto commentNewDto);

    void deleteByAuthor(Long userId, Long commentId);

    void  deleteByAdmin(Long commentId);

    List<Comment> getUserComments(Long userId, String rangeStart, String rangeEnd, Integer from, Integer size);

    List<Comment> getAllByAdmin(String rangeStart, String rangeEnd, Integer from, Integer size);

    List<Comment> getEventComments(Long eventId, String rangeStart, String rangeEnd, Integer from, Integer size);

}
