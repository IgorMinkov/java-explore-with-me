package ru.practicum.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.model.Category;
import ru.practicum.category.model.CategoryMapper;
import ru.practicum.category.service.CategoryService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class CategoryAdminController {

    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public CategoryDto addCategory(@Valid @RequestBody CategoryDto categoryDto) {

        Category category = categoryService.create(CategoryMapper.toCategory(categoryDto));
        log.info("Добавление категории: {}", category.getName());
        return CategoryMapper.toCategoryDto(category);
    }

    @PatchMapping("/{catId}")
    @ResponseStatus(value = HttpStatus.OK)
    public CategoryDto updateCategory(@Valid @RequestBody CategoryDto categoryDto,
                                      @PathVariable("catId") Long categoryId) {
        Category updatedCategory = categoryService.update(CategoryMapper.toCategory(categoryDto), categoryId);
        log.info("Обновление категории: {}", categoryDto.getName());
        return CategoryMapper.toCategoryDto(updatedCategory);
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable("catId") Long categoryId) {
        categoryService.delete(categoryId);
        log.info("Удаление категории с id: {}", categoryId);
    }

}
