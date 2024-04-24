package ru.practicum.comments.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.comments.CommentRepository;
import ru.practicum.comments.dto.CommentNewDto;
import ru.practicum.comments.model.Comment;
import ru.practicum.event.model.event.Event;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.DataNotFoundException;
import ru.practicum.exception.ValidationException;
import ru.practicum.user.model.User;
import ru.practicum.utils.EntityCheckService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static ru.practicum.Utils.DATE_FORMAT;
import static ru.practicum.Utils.PROGRAM_TIME;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final EntityCheckService checkService;

    @Override
    @Transactional
    public Comment create(Long userId, Long eventId, Comment comment) {
        User user = checkService.getUserOrNotFound(userId);
        Event event = checkService.getEventOrNotFound(eventId);
        comment.setUser(user);
        comment.setEvent(event);
        comment.setCreated(PROGRAM_TIME);
        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public Comment update(Long userId, Long commentId, CommentNewDto commentNewDto) {
        checkService.checkUser(userId);
        Comment comment = getOrNotFound(commentId);
        if (!userId.equals(comment.getUser().getId())) {
            throw new ConflictException(String.format(
                    "Пользователь с id: %s не автор комментария с id: %s",userId, commentId));
        }
        Optional.ofNullable(commentNewDto.getMessage()).ifPresent(comment::setMessage);
        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void deleteByAuthor(Long userId, Long commentId) {
        checkService.checkUser(userId);
        Comment comment = getOrNotFound(commentId);
        if (!userId.equals(comment.getUser().getId())) {
            throw new ConflictException(String.format(
                    "Пользователь с id: %s не автор комментария с id: %s",userId, commentId));
        }
        commentRepository.deleteById(commentId);
    }

    @Override
    @Transactional
    public void deleteByAdmin(Long commentId) {
        Comment comment = getOrNotFound(commentId);
        commentRepository.deleteById(comment.getId());
    }

    @Override
    public List<Comment> getUserComments(Long userId, String rangeStart, String rangeEnd, Integer from, Integer size) {
        checkService.checkUser(userId);
        LocalDateTime startTime = parseDate(rangeStart);
        LocalDateTime endTime = parseDate(rangeEnd);
        validateDate(startTime, endTime);
        PageRequest pageRequest = PageRequest.of(from / size, size);
        return commentRepository.getCommentsByUserId(userId, startTime, endTime, pageRequest);
    }

    @Override
    public List<Comment> getAllByAdmin(String rangeStart, String rangeEnd, Integer from, Integer size) {
        LocalDateTime startTime = parseDate(rangeStart);
        LocalDateTime endTime = parseDate(rangeEnd);
        validateDate(startTime, endTime);
        PageRequest pageRequest = PageRequest.of(from / size, size);
        return commentRepository.getComments(startTime, endTime, pageRequest);
    }

    @Override
    public List<Comment> getEventComments(Long eventId, String rangeStart, String rangeEnd,
                                          Integer from, Integer size) {
        checkService.checkEvent(eventId);
        LocalDateTime startTime = parseDate(rangeStart);
        LocalDateTime endTime = parseDate(rangeEnd);
        validateDate(startTime, endTime);
        PageRequest pageRequest = PageRequest.of(from / size, size);
        return commentRepository.getCommentsByEventId(eventId, startTime, endTime, pageRequest);
    }

    private Comment getOrNotFound(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new DataNotFoundException(
                        String.format("Не найден комментарий c id: %s", commentId)));
    }

    private LocalDateTime parseDate(String date) {
        if (date != null) {
            return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(DATE_FORMAT));
        } else {
            return null;
        }
    }

    private void validateDate(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime != null && endTime != null) {
            if (startTime.isAfter(endTime)) {
                throw new ValidationException("Start must be after End");
            }
            if (endTime.isAfter(PROGRAM_TIME) || startTime.isAfter(PROGRAM_TIME)) {
                throw new ValidationException("date must be the past");
            }
        }
    }

}
