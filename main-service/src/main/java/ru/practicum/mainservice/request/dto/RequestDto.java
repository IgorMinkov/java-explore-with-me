package ru.practicum.mainservice.request.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import ru.practicum.mainservice.utils.enums.Status;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static ru.practicum.mainservice.event.model.event.EventMapper.DATE_FORMAT;

@Data
@Builder
public class RequestDto {

    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    private LocalDateTime created;

    @NotNull
    private Long event;

    @NotNull
    private Long requester;

    private Status status;

}
