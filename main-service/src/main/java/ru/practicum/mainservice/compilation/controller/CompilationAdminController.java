package ru.practicum.mainservice.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.mainservice.compilation.dto.CompilationDto;
import ru.practicum.mainservice.compilation.dto.CompilationNewDto;
import ru.practicum.mainservice.compilation.dto.CompilationUpdateDto;
import ru.practicum.mainservice.compilation.model.Compilation;
import ru.practicum.mainservice.compilation.model.CompilationMapper;
import ru.practicum.mainservice.compilation.service.CompilationService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/admin/compilations")
@RequiredArgsConstructor
public class CompilationAdminController {

    private final CompilationService compilationService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public CompilationDto addCompilation(@Valid @RequestBody CompilationNewDto compilationNewDto) {
        Compilation compilation = compilationService.create(
                CompilationMapper.toCompilation(compilationNewDto), compilationNewDto.getEvents());
        log.info("Добавление подборки событий: {}", compilation.getTitle());
        return CompilationMapper.toCompilationDto(compilation);
    }

    @PatchMapping("/{compId}")
    @ResponseStatus(value = HttpStatus.OK)
    public CompilationDto updateCompilation(@Valid @RequestBody CompilationUpdateDto compilationUpdateDto,
                                            @PathVariable Long compId) {
        Compilation updatedCompilation = compilationService.update(compId, compilationUpdateDto);
        log.info("Обновление подборки событий с id: {}", compId);
        return CompilationMapper.toCompilationDto(updatedCompilation);
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable("compId") Long compId) {
        log.info("Удаление подборки событий с id {} ", compId);
        compilationService.delete(compId);
    }

}
