package ru.practicum.comments.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

import static ru.practicum.Utils.DATE_FORMAT;

@Data
@Builder
public class CommentShortDto {

    private String userName;

    private String eventTitle;

    private String message;

    @JsonFormat(pattern = DATE_FORMAT)
    private LocalDateTime created;

}
