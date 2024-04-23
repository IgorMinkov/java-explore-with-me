package ru.practicum.comments.model;

import lombok.experimental.UtilityClass;
import ru.practicum.comments.dto.CommentDto;
import ru.practicum.comments.dto.CommentNewDto;
import ru.practicum.comments.dto.CommentShortDto;
import ru.practicum.event.model.event.EventMapper;
import ru.practicum.user.model.UserMapper;

@UtilityClass
public class CommentMapper {

    public static Comment toComment(CommentNewDto commentNewDto) {
        return Comment.builder()
                .message(commentNewDto.getMessage())
                .build();
    }

    public static CommentDto toCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .user(UserMapper.toUserDto(comment.getUser()))
                .event(EventMapper.toEventFullDto(comment.getEvent()))
                .message(comment.getMessage())
                .created(comment.getCreated())
                .build();
    }

    public static CommentShortDto toCommentShortDto(Comment comment) {
        return CommentShortDto.builder()
                .userName(comment.getUser().getName())
                .eventTitle(comment.getEvent().getTitle())
                .message(comment.getMessage())
                .created(comment.getCreated())
                .build();
    }

}
