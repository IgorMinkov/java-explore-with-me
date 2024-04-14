package ru.practicum.mainservice.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainservice.compilation.CompilationRepository;
import ru.practicum.mainservice.compilation.dto.CompilationUpdateDto;
import ru.practicum.mainservice.compilation.model.Compilation;
import ru.practicum.mainservice.event.repository.EventRepository;
import ru.practicum.mainservice.exception.DataNotFoundException;
import ru.practicum.mainservice.utils.EntityCheckService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;
    private final EntityCheckService checkService;

    @Override
    @Transactional
    public Compilation create(Compilation compilation, Set<Long> eventIds) {
        if (eventIds == null || eventIds.isEmpty()) {
            compilation.setEvents(Collections.emptySet());
        } else {
            compilation.setEvents(eventRepository.findByIdIn(eventIds));
        }
        return compilationRepository.save(compilation);
    }

    @Override
    @Transactional
    public Compilation update(Long compId, CompilationUpdateDto compilationUpdateDto) {
        Compilation updatedCompilation = getById(compId);
        Optional.ofNullable(compilationUpdateDto.getPinned()).ifPresent(updatedCompilation::setPinned);
        Optional.ofNullable(compilationUpdateDto.getTitle()).ifPresent(updatedCompilation::setTitle);
        if (compilationUpdateDto.getEvents() == null || compilationUpdateDto.getEvents().isEmpty()) {
            updatedCompilation.setEvents(Collections.emptySet());
        } else {
            updatedCompilation.setEvents(eventRepository.findByIdIn(compilationUpdateDto.getEvents()));
        }
        compilationRepository.save(updatedCompilation);
        return updatedCompilation;
    }

    @Override
    public Compilation getById(Long compId) {
        return compilationRepository.findById(compId)
                .orElseThrow(() -> new DataNotFoundException(
                        String.format("Не найдена подборка c id: %s", compId)));
    }

    @Override
    public List<Compilation> getPinnedList(Boolean pinned, Integer from, Integer size) {
        PageRequest pageRequest = PageRequest.of(from / size, size);
        if (pinned) {
            return compilationRepository.findByPinned(pinned, pageRequest);
        }
        return compilationRepository.findAll(pageRequest).toList();
    }

    @Override
    @Transactional
    public void delete(Long compId) {
        checkService.checkCompilation(compId);
        compilationRepository.deleteById(compId);
    }

}