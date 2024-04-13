package ru.practicum.mainservice.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.category.dto.CategoryDto;
import ru.practicum.mainservice.category.model.Category;
import ru.practicum.mainservice.category.model.CategoryMapper;
import ru.practicum.mainservice.category.service.CategoryService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryPublicController {

    private final CategoryService categoryService;

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<CategoryDto> getCategories(@PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                           @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        List<Category> result = categoryService.getList(from, size);
        log.info("Получение списка всех категорий с параметрами: from = {}, size = {}", from, size);
        return result.stream()
                .map(CategoryMapper::toCategoryDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{catId}")
    @ResponseStatus(value = HttpStatus.OK)
    public CategoryDto getCategory(@PathVariable("catId") Long categoryId) {

        log.info("Получение категории по её id {}", categoryId);
        return CategoryMapper.toCategoryDto(categoryService.getById(categoryId));
    }

}
