package ru.practicum.request.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import ru.practicum.utils.enums.Status;

import java.time.LocalDateTime;

import static ru.practicum.Utils.DATE_FORMAT;

@Data
@Builder
public class RequestDto {

    private Long id;

    @JsonFormat(pattern = DATE_FORMAT)
    private LocalDateTime created;

    private Long event;

    private Long requester;

    private Status status;

}
