package ru.practicum.mainservice.category.model;

import lombok.experimental.UtilityClass;
import ru.practicum.mainservice.category.dto.CategoryDto;

@UtilityClass
public class CategoryMapper {

    public CategoryDto toCategoryDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public Category toCategory(CategoryDto categoryDto) {
        return Category.builder()
                .id(categoryDto.getId())
                .name(categoryDto.getName())
                .build();
    }

}
