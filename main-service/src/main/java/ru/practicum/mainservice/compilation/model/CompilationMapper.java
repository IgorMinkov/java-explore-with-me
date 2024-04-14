package ru.practicum.mainservice.compilation.model;

import lombok.experimental.UtilityClass;
import ru.practicum.mainservice.compilation.dto.CompilationDto;
import ru.practicum.mainservice.compilation.dto.CompilationNewDto;
import ru.practicum.mainservice.event.dto.event.EventShortDto;
import ru.practicum.mainservice.event.model.event.EventMapper;

import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class CompilationMapper {

    public static CompilationDto toCompilationDto(Compilation compilation) {

        Set<EventShortDto> eventShortDtoSet = compilation.getEvents().stream()
                .map(EventMapper::toEventShortDto).collect(Collectors.toSet());

        return CompilationDto.builder()
                .id(compilation.getId())
                .pinned(compilation.getPinned())
                .title(compilation.getTitle())
                .events(eventShortDtoSet)
                .build();
    }

    public static Compilation toCompilation(CompilationNewDto compilationNewDto) {
        return Compilation.builder()
                .title(compilationNewDto.getTitle())
                .pinned(compilationNewDto.getPinned())
                .build();
    }

}
