package ru.practicum.request.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.utils.enums.Status;

import java.time.LocalDateTime;

@Data
@Builder
public class RequestDto {

    private Long id;

    private LocalDateTime created;

    private Long event;

    private Long requester;

    private Status status;

}
