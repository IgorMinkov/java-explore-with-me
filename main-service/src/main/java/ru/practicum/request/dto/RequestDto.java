package ru.practicum.request.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import ru.practicum.event.model.event.EventMapper;
import ru.practicum.utils.enums.Status;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
public class RequestDto {

    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = EventMapper.DATE_FORMAT)
    private LocalDateTime created;

    @NotNull
    private Long event;

    @NotNull
    private Long requester;

    private Status status;

}
