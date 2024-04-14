package ru.practicum.mainservice.compilation.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.mainservice.event.dto.event.EventShortDto;

import java.util.Set;

@Data
@Builder
public class CompilationDto {

    private Long id;

    private Boolean pinned;

    private String title;

    private Set<EventShortDto> events;

}
