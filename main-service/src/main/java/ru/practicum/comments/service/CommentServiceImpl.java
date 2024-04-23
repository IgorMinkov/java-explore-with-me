package ru.practicum.comments.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.comments.CommentRepository;
import ru.practicum.comments.dto.CommentNewDto;
import ru.practicum.comments.model.Comment;
import ru.practicum.utils.EntityCheckService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final EntityCheckService checkService;

    @Override
    @Transactional
    public Comment create(Long userId, Long eventId, Comment comment) {
        return null;
    }

    @Override
    @Transactional
    public Comment update(Long userId, Long commentId, CommentNewDto commentNewDto) {
        return null;
    }

    @Override
    @Transactional
    public void deleteByAuthor(Long userId, Long commentId) {

    }

    @Override
    @Transactional
    public void deleteByAdmin(Long commentId) {

    }

    @Override
    public List<Comment> getUserComments(Long userId, String rangeStart, String rangeEnd, Integer from, Integer size) {
        return null;
    }

    @Override
    public List<Comment> getAllByAdmin(String rangeStart, String rangeEnd, Integer from, Integer size) {
        return null;
    }

    @Override
    public List<Comment> getEventComments(Long eventId, String rangeStart, String rangeEnd,
                                          Integer from, Integer size) {
        return null;
    }

}
