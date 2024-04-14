package ru.practicum.mainservice.event.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import ru.practicum.mainservice.event.dto.LocationDto;
import ru.practicum.mainservice.utils.enums.StateAction;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static ru.practicum.mainservice.event.model.event.EventMapper.DATE_FORMAT;

@Data
@Builder
public class EventUpdateDto {

    @Size(min = 20, max = 2000)
    private String annotation;

    private Long category;

    @Size(min = 3, max = 120)
    private String title;

    @Size(min = 20, max = 7000)
    private String description;

    @FutureOrPresent(message = "event date must be today or in the future")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    private LocalDateTime eventDate;

    private LocationDto location;

    private Boolean paid;

    private Long participantLimit;

    private Boolean requestModeration;

    private StateAction stateAction;

}
