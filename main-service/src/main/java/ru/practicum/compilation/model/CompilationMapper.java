package ru.practicum.compilation.model;

import lombok.experimental.UtilityClass;
import ru.practicum.event.model.event.EventMapper;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.CompilationNewDto;
import ru.practicum.event.dto.event.EventShortDto;

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
