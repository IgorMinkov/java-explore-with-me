package ru.practicum.event.dto.event;

import lombok.Builder;
import lombok.Data;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.user.dto.UserShortDto;

import java.time.LocalDateTime;

@Data
@Builder
public class EventShortDto {

    private Long id;

    private String annotation;

    private CategoryDto category;

    private String title;

    private Boolean paid;

    private LocalDateTime eventDate;

    private UserShortDto initiator;

    private Long views;

    private Long confirmedRequests;

}
