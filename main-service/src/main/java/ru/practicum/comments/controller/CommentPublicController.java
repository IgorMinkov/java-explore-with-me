package ru.practicum.comments.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.comments.dto.CommentShortDto;
import ru.practicum.comments.model.Comment;
import ru.practicum.comments.model.CommentMapper;
import ru.practicum.comments.service.CommentService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(path = "/comments")
@RequiredArgsConstructor
public class CommentPublicController {

    private final CommentService commentService;

    @GetMapping("{eventId}")
    @ResponseStatus(value = HttpStatus.OK)
    public List<CommentShortDto> getEventComments(@PathVariable Long eventId,
                                                  @RequestParam(required = false) String rangeStart,
                                                  @RequestParam(required = false) String rangeEnd,
                                                  @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                                  @Positive @RequestParam(defaultValue = "10") Integer size) {
        List<Comment> result = commentService.getEventComments(eventId, rangeStart, rangeEnd, from, size);
        log.info("Получение списка комментариев к событию с id: {} с {} по {}.", eventId, rangeStart, rangeEnd);
        return result.stream()
                .map(CommentMapper::toCommentShortDto)
                .collect(Collectors.toList());
    }

}
