package ru.practicum.comments.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import ru.practicum.event.dto.event.EventFullDto;
import ru.practicum.user.dto.UserDto;

import java.time.LocalDateTime;

import static ru.practicum.Utils.DATE_FORMAT;

@Data
@Builder
public class CommentDto {

    private Long id;

    private UserDto user;

    private EventFullDto event;

    private String message;

    @JsonFormat(pattern = DATE_FORMAT)
    private LocalDateTime created;

}
