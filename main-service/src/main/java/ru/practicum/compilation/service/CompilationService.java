package ru.practicum.compilation.service;

import ru.practicum.compilation.dto.CompilationUpdateDto;
import ru.practicum.compilation.model.Compilation;

import java.util.List;
import java.util.Set;

public interface CompilationService {

    Compilation create(Compilation compilation, Set<Long> eventIds);

    Compilation update(Long compId, CompilationUpdateDto compilationUpdateDto);

    Compilation getById(Long compId);

    List<Compilation> getPinnedList(Boolean pinned, Integer from, Integer size);

    void delete(Long compId);

}
