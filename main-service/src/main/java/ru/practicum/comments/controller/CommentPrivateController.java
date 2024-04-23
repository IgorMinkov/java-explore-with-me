package ru.practicum.comments.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.comments.dto.CommentDto;
import ru.practicum.comments.dto.CommentNewDto;
import ru.practicum.comments.dto.CommentShortDto;
import ru.practicum.comments.model.Comment;
import ru.practicum.comments.model.CommentMapper;
import ru.practicum.comments.service.CommentService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/comments")
@Validated
public class CommentPrivateController {

    private final CommentService commentService;

    @PostMapping("/{eventId}")
    @ResponseStatus(value = HttpStatus.CREATED)
    public CommentDto addComment(@Valid @RequestBody CommentNewDto commentNewDto,
                                 @PathVariable Long userId,
                                 @PathVariable Long eventId) {
        Comment comment = commentService.create(userId, eventId, CommentMapper.toComment(commentNewDto));
        log.info("Пользователь с id: {} комментирует событие с id: {}", userId, eventId);
        return CommentMapper.toCommentDto(comment);
    }

    @PatchMapping("/{commentId}")
    @ResponseStatus(value = HttpStatus.OK)
    public CommentDto updateComment(@Valid @RequestBody CommentNewDto commentNewDto,
                                    @PathVariable Long userId,
                                    @PathVariable Long commentId) {
        Comment updatedComment = commentService.update(userId, commentId, commentNewDto);
        log.info("Пользователь с id: {} обновил комментарий с id: {}", userId, commentId);
        return CommentMapper.toCommentDto(updatedComment);
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<CommentShortDto> getUserComments(@PathVariable Long userId,
                                                 @RequestParam(required = false) String rangeStart,
                                                 @RequestParam(required = false) String rangeEnd,
                                                 @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                                 @Positive @RequestParam(defaultValue = "10") Integer size) {
        List< Comment> result = commentService.getUserComments(userId, rangeStart, rangeEnd, from, size);
        log.info("Получение комментариев пользователя с id: {}, с {} по {}", userId, rangeStart, rangeEnd);
        return result.stream()
                .map(CommentMapper::toCommentShortDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable Long userId,
                              @PathVariable Long commentId) {
        log.info("Пользователь с id: {} удалил свой комментарий с id: {} ", userId, commentId);
        commentService.deleteByAuthor(userId, commentId);
    }

}
