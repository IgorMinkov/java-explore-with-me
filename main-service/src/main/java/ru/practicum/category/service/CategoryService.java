package ru.practicum.category.service;

import ru.practicum.category.model.Category;

import java.util.List;

public interface CategoryService {

    Category create(Category category);

    Category update(Category category, Long categoryId);

    Category getById(Long categoryId);

    List<Category> getList(Integer from, Integer size);

    void delete(Long categoryId);

}
