package ru.practicum.mainservice.event.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import ru.practicum.mainservice.category.dto.CategoryDto;
import ru.practicum.mainservice.event.dto.LocationDto;
import ru.practicum.mainservice.user.dto.UserShortDto;
import ru.practicum.mainservice.utils.enums.State;

import java.time.LocalDateTime;

import static ru.practicum.mainservice.event.model.event.EventMapper.DATE_FORMAT;

@Data
@Builder
public class EventFullDto {

    private Long id;

    private String annotation;

    private CategoryDto category;

    private String title;

    private String description;

    private Boolean paid;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    private LocalDateTime eventDate;

    private UserShortDto initiator;

    private LocationDto location;

    private Long participantLimit;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    private LocalDateTime createdOn;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    private LocalDateTime publishedOn;

    private Boolean requestModeration;

    private State state;

    private Long views;

    private Long confirmedRequests;

}